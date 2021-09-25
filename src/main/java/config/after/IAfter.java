package config.after;

import api.RequestData;
import io.restassured.response.Response;

public interface IAfter {
    void afterHandle(RequestData requestData, Response response);
}
