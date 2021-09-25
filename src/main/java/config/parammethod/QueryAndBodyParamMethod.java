package config.parammethod;

import api.RequestData;
import io.restassured.specification.RequestSpecification;

public class QueryAndBodyParamMethod implements IParamMethod{
    public RequestSpecification paramMethodBuild(RequestSpecification specification, RequestData requestData) {
        specification.queryParam(requestData.getParam());
        specification.body(requestData.getParam());
        return specification;
    }
}
