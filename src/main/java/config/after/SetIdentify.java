package config.after;

import api.RequestData;
import business.force.login.component.Identifier;
import io.restassured.response.Response;

import java.net.URLEncoder;

import static datastore.DataStore.getResponseValue;
import static datastore.TreadLocalStore.dataStore;

public class SetIdentify implements IAfter {
    @Override
    public void afterHandle(RequestData requestData, Response response) {
        if (response.getBody().path(dataStore.get().defaultAssertPath) == dataStore.get().defaultAssertValue)
            dataStore.get().token = URLEncoder.encode(getResponseValue(Identifier.class, "data.identifier"));
    }
}
