package config.header;

import api.RequestData;

import java.util.Map;

public class FormHeadersDefault extends HeadersDefault {
    @Override
    public Map<String, Object> getHeaders(RequestData requestData) {
        Map<String, Object> map = super.getHeaders(requestData);
        map.put("content-type", "application/x-www-form-urlencoded");
        return map;
    }
}
