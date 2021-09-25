package api;

import base.BaseCase;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.testng.Assert;
import utils.RandomUtil;
import utils.ReportUtil;

import java.util.concurrent.TimeUnit;

import static datastore.TreadLocalStore.dataStore;
import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static utils.FileUtil.writeFile;

public class ApiTest {
    //被BaseCase类继承了的类中的属性一定要写私有的如下,因为在实体类转json串时公有属性也会被写入json中。并且不能加上对应各get方法
    //private Integer test = 1;

    public Response apiTest(BaseCase baseCase) {
        return apiTest(new RequestData(baseCase));
    }

    @SneakyThrows
    public Response apiTest(RequestData requestData) {
        //执行前置处理器
        if (requestData.getBeforeHandle() != null) {
            requestData.getBeforeHandle().newInstance().beforeHandle(requestData);
        }

        if (requestData.getSleep() != null && requestData.getSleep() != 0) {
            try {
                ReportUtil.log("Sleep             : " + requestData.getSleep());
                TimeUnit.SECONDS.sleep(requestData.getSleep());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ReportUtil.log("Des               : " + requestData.getDes());
        ReportUtil.log("Host              : " + requestData.getHost());
        ReportUtil.log("Uri               : " + requestData.getUrl());
        ReportUtil.log("Method            : " + requestData.getMethodAndRequestType().getApiMethod());
        ReportUtil.log("ParamMethod       : " + requestData.getMethodAndRequestType().getParamMethod().getClass().getSimpleName());
        ReportUtil.log("Header            : " + requestData.getHeader());
        ReportUtil.log("Param             : " + requestData.getParam());

        //host
        RestAssured.baseURI = requestData.getHost();
        //使用https证书
        RestAssured.useRelaxedHTTPSValidation();
        //设置默认的解析
        RestAssured.defaultParser = Parser.JSON;
        //组装请求
        RequestSpecification specification = given();
        //放入请求头
        specification.headers(requestData.getHeader());
        //请求方式组装
        specification = requestData.getMethodAndRequestType().getParamMethod().paramMethodBuild(specification, requestData);

        //发送请求
        Response response = requestData.getInvokeRequest().invokeRequest(specification, requestData);

        //存储请求
        dataStore.get().req.put(requestData.getBaseCase().getClass().getName(), from(requestData.getNotPreHandleParamData()));
        //存储响应
        dataStore.get().res.put(requestData.getBaseCase().getClass().getName(), response);
        //存储请求信息
        dataStore.get().reqData.put(requestData.getBaseCase().getClass().getName(), requestData);

        //下载文件
        String contentTypeHeader = response.getHeader("Content-Type");
        String res = response.getBody().asString();
        if (contentTypeHeader != null && (contentTypeHeader.contains("download")
                || contentTypeHeader.contains("octet-stream"))) {
            String fileType = "";
            String headerDisposition = response.getHeader("Content-disposition");
            if (headerDisposition != null) {
                fileType = headerDisposition.substring(headerDisposition.lastIndexOf("."), headerDisposition.length() - 1);
            }
            String contentPath = dataStore.get().downloadDir + RandomUtil.getString() + fileType;
            Assert.assertTrue(writeFile(response.getBody().asInputStream(), contentPath), "下载文件失败");
            res = "{\"filePath\":\"" + contentPath + "\"}";
        } else if (!contentTypeHeader.contains("application/json")) {
            response = new ResponseBuilder().clone(response).setContentType(ContentType.JSON).build();
        }
        ReportUtil.log("res               : " + res);
        //换行
        ReportUtil.log("");

        //执行后置处理器
        if (requestData.getAfterHandle() != null) {
            requestData.getAfterHandle().newInstance().afterHandle(requestData, response);
        }

        //断言
        if (requestData.isOpenAssert() && requestData.getAssertMethod() != null) {
            requestData.getAssertMethod().assets(requestData, response);
        }

        return response;
    }
}
