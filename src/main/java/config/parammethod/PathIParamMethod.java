package config.parammethod;

import api.RequestData;
import io.restassured.specification.RequestSpecification;

public class PathIParamMethod implements IParamMethod {

    @Override
    public RequestSpecification paramMethodBuild(RequestSpecification specification, RequestData requestData) {
        String url = requestData.getUrl();
        String pathParam = requestData.getPathParam();
        if (url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        requestData.setUrl(url + pathParam);
        return specification;
    }
}
