package config.after;

import api.RequestData;
import io.restassured.response.Response;

public class AfterDefault implements IAfter{
    @Override
    public void afterHandle(RequestData requestData, Response response) {

    }
}
