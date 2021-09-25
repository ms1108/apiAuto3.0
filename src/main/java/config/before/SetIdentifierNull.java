package config.before;

import api.RequestData;

import static datastore.TreadLocalStore.dataStore;

public class SetIdentifierNull implements IBefore {
    @Override
    public void beforeHandle(RequestData requestData) {
        if (dataStore.get().token != null)
            dataStore.get().token = null;
    }
}
