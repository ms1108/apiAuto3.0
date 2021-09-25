package datastore;

import api.ApiTest;
import api.RequestData;
import base.BaseCase;
import depedchain.DependChain;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static datastore.TreadLocalStore.dataStore;

public class DataStore {
    //存储响应信息
    public Map<String, Response> res = new HashMap<>();
    //存储请求信息
    public Map<String, JsonPath> req = new HashMap<>();
    //存储请求信息
    public Map<String, RequestData> reqData = new HashMap<>();

    public String token;
    /**
     * 默认断言路径
     */
    public String defaultAssertPath = "ret";
    /**
     * 默认断言值
     */
    public Object defaultAssertValue = 0;
    /**
     * 是否开放的注解测试
     */
    public boolean isOpenAnnotation = true;
    /**
     * 下载路径
     */
    public String downloadDir = "src/main/resources/download";

    //自定义的调用链依赖使用，即用即清理
    public Map<String, Object> dependChainDIY = new HashMap<>();

    //获取响应中的值，类型为什么类型就返回什么
    public static <T> T getResponseValue(Class<? extends BaseCase> baseCaseClass, String path) {
        Response response = dataStore.get().res.get(baseCaseClass.getName());
        if (response == null) {
            return null;
        }
        T t = (T) response.path(path);
        if (t == null) {
            return null;
        }
        return t;
    }

    public static <T> T getResponseValue(Class<? extends BaseCase> baseCaseClass, String path, Class<T> clazz) {
        Object value = getResponseValue(baseCaseClass, path);
        if (value == null) {
            return null;
        }
        //返回的类型由外边控制
        if (clazz.getSimpleName().equals(String.class.getSimpleName())) {
            return (T) (value + "");
        }
        return (T) Integer.valueOf(value + "");
    }

    //获取所有响应求头
    public static Headers getResponseHeaders(Class<? extends BaseCase> baseCaseClass) {
        Response response = dataStore.get().res.get(baseCaseClass.getName());
        if (response == null) {
            return null;
        }
        Headers t = response.headers();
        if (t == null) {
            return null;
        }
        return t;
    }

    //获取指定响应求头
    public static Header getResponseHeader(Class<? extends BaseCase> baseCaseClass, String headerName) {
        Header t = getResponseHeaders(baseCaseClass).get(headerName);
        if (t == null) {
            return null;
        }
        return t;
    }

    //获取cookies
    public static <T> T getResponseCookies(Class<? extends BaseCase> baseCaseClass) {
        Response response = dataStore.get().res.get(baseCaseClass.getName());
        if (response == null) {
            return null;
        }
        T t = (T) response.cookies();
        if (t == null) {
            return null;
        }
        return t;
    }

    //调用接口后再取值
    public static <T> T invokeApiGetValue(BaseCase baseCase, String path) {
        Response response = new ApiTest().apiTest(baseCase);
        T t = (T) response.path(path);
        if (t == null) {
            return null;
        }
        return t;
    }

    //获取请求过的数据
    public static <T> T getRequestValue(Class<? extends BaseCase> baseCaseClass, String path) {
        JsonPath jsonPath = dataStore.get().req.get(baseCaseClass.getName());
        if (jsonPath == null) {
            return null;
        }
        T t = (T) jsonPath.get(path);
        if (t == null) {
            return null;
        }
        return t;
    }

    public static <T> T getRequestValue(Class<? extends BaseCase> baseCaseClass, String path, Class<T> clazz) {
        Object value = getRequestValue(baseCaseClass, path);
        if (value == null) {
            return null;
        }
        //返回的类型由外边控制
        if (clazz.getSimpleName().equals(String.class.getSimpleName())) {
            return (T) (value + "");
        }
        return (T) Integer.valueOf(value + "");
    }

    //存入调用链的map中
    public static void putDependChainDIY(Class<? extends DependChain> dependChainClass, DependChain dependChain) {
        putDependChainDIY(dependChainClass, dependChain, false);
    }

    public static void putDependChainDIY(Class<? extends DependChain> dependChainClass, DependChain dependChain, boolean cover) {
        if (cover) {
            dataStore.get().dependChainDIY.put(dependChainClass.getName(), dependChain);
        } else {
            dataStore.get().dependChainDIY.putIfAbsent(dependChainClass.getName(), dependChain);
        }
    }

}
