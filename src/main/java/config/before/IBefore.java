package config.before;

import api.RequestData;
//前置处理器
public interface IBefore {
    void beforeHandle(RequestData requestData);
}
