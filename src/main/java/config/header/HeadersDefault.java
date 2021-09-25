package config.header;

import api.RequestData;
import datastore.DataStore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static datastore.TreadLocalStore.dataStore;

public class HeadersDefault implements IHeaders {
    @Override
    public Map<String, Object> getHeaders(RequestData requestData) {
        Map<String, Object> map = new HashMap<>();
        map.put("content-type", "application/json");
        map.put("referer", requestData.getHost());
        if (dataStore.get().token!=null)
        map.put("cookie", dataStore.get().token);
        return map;
    }
}
