package api;

import annotation.annotations.After;
import annotation.annotations.Api;
import annotation.annotations.Before;
import annotation.annotations.Header;
import base.ApiMethod;
import base.BaseCase;
import base.BusinessDefaultInfo;
import com.alibaba.fastjson.JSONObject;
import config.after.IAfter;
import config.asserts.AssertMethod;
import config.asserts.FailAssetDefault;
import config.before.IBefore;
import config.header.IHeaders;
import config.invokerequest.DefaultInvokeRequest;
import config.invokerequest.InvokeRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import org.testng.Assert;
import utils.StringUtil;
import utils.set.PropertiesUtil;

import java.util.Map;


@Data
@NoArgsConstructor
@Accessors(chain = true)
public class RequestData {

    private String host;

    private String url;
    //请求的方式
    private ApiMethod methodAndRequestType;
    //jsonSchema的位置
    private String jsonSchemaPath;
    //当步骤描述为空时，使用地址枚举中的描述
    private String des;
    //cookie
    private Map<String, String> cookies;
    //请求头接口
    private Class<? extends IHeaders> iHeaders;
    //请求头
    private Map<String, Object> header;
    //前置处理器要处理请求参数时通过该参数处理
    private String param;
    //未经过参数前置处理器处理的请求参数，
    //注解在修改参数时需要传入key路径，所以要保存一份未经过前置处理过的参数
    private String notPreHandleParamData;
    //拼接在请求路径后的数据，
    //用于restful类型请求，或者简单粗暴的拼接参数，
    //注意请求方式要走PathIParamMethod或者继承了PathIParamMethod的类
    private String pathParam;
    //BaseCase对象
    private BaseCase baseCase;
    //api注解
    private Api api;
    //是否开启断言
    private boolean isOpenAssert = true;
    //请求前等待时间，单位秒
    private Integer sleep;
    //断言方式
    private AssertMethod assertMethod;
    //幂等请求线程个数
    private int multiThreadNum;
    //用于区分普通请求和幂等请求
    private InvokeRequest invokeRequest = new DefaultInvokeRequest();
    //前置处理器
    private Class<? extends IBefore> beforeHandle;
    //后置处理器
    private Class<? extends IAfter> afterHandle;

    public RequestData(BaseCase param) {
        requestData(param);
    }

    @SneakyThrows
    public void requestData(BaseCase baseCase) {
        this.baseCase = baseCase;
        this.api = baseCase.getClass().getAnnotation(Api.class);
        String hostKey = api.hostKey();
        if (StringUtil.isEmpty(hostKey))
            hostKey = BusinessDefaultInfo.getModuleEnum(baseCase.getClass().getPackage().getName()).getHostKey();
        this.host = PropertiesUtil.get(hostKey);
        this.url = api.value();
        this.methodAndRequestType = api.apiMethod();
        if (api.apiMethod().name().equals(ApiMethod.PLACEHOLDER.name()))
            this.methodAndRequestType = BusinessDefaultInfo.getModuleEnum(baseCase.getClass().getPackage().getName()).getApiMethod();
        this.jsonSchemaPath = api.jsonSchemaPath();
        this.des = api.des();
        this.assertMethod = baseCase.getAssertMethod();
        this.pathParam = baseCase.getPathParam();
        this.iHeaders = baseCase.getClass().isAnnotationPresent(Header.class) ? baseCase.getClass().getAnnotation(Header.class).value() : null;
        if (this.iHeaders == null)
            iHeaders = BusinessDefaultInfo.getModuleEnum(baseCase.getClass().getPackage().getName()).getIHeaders();
        this.beforeHandle = baseCase.getClass().isAnnotationPresent(Before.class) ? baseCase.getClass().getAnnotation(Before.class).value() : null;
        if (this.beforeHandle == null)
            beforeHandle = BusinessDefaultInfo.getModuleEnum(baseCase.getClass().getPackage().getName()).getIBefore();
        this.afterHandle = baseCase.getClass().isAnnotationPresent(After.class) ? baseCase.getClass().getAnnotation(After.class).value() : null;
        if (this.afterHandle == null)
            afterHandle = BusinessDefaultInfo.getModuleEnum(baseCase.getClass().getPackage().getName()).getIAfter();
        //param转json串时，以下的字段不需要
        baseCase.assertMethod = null;
        baseCase.pathParam = null;
        this.notPreHandleParamData = JSONObject.toJSONString(baseCase);
        if (this.notPreHandleParamData.contains("{\"$ref\":\"@\"}")) {
            Assert.fail("组件类中不能出现以get开头的方法，或者在该方法加上注解：@JSONField(serialize = false)");
        }
        //置null后避免空指针问题，重新赋值
        baseCase.assertMethod = this.assertMethod;
        baseCase.pathParam = this.pathParam;
    }

    public RequestData fail() {
        assertMethod = new FailAssetDefault();
        return this;
    }

    public RequestData offDefaultAssert() {
        isOpenAssert = false;
        return this;
    }

    public String getParam() {
        return this.param == null ? this.notPreHandleParamData : this.param;
    }

    @SneakyThrows
    public Map<String, Object> getHeader() {
        //其他地方可以通过getHeader获取请求头，再通过set进来，实现对请求头的修改
        return this.header == null ? this.iHeaders.newInstance().getHeaders(this) : this.header;
    }
}
