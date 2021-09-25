package base;

import config.parammethod.*;
import lombok.SneakyThrows;

public enum ApiMethod {

    POST("post", BodyIParamMethod.class),
    POST_FORM("post", FormIParamMethod.class),
    GET("get", QueryIParamMethod.class),
    GET_PATH("get", PathIParamMethod.class),
    POST_PATH_BODY("post", PathAndBodyParamMethod.class),
    UPLOAD("post", UploadIParamMethod.class),
    //Api注解中的站位符
    PLACEHOLDER("post", BodyIParamMethod.class)
    ;

    private String apiMethod;
    private Class<? extends IParamMethod> paramMethod;

    ApiMethod(String method, Class<? extends IParamMethod> paramMethod) {
        this.apiMethod = method;
        this.paramMethod = paramMethod;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    @SneakyThrows
    public IParamMethod getParamMethod() {
        return paramMethod.newInstance();
    }
}
