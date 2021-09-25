package datastore;

public class TreadLocalStore {
    //目的实现线程间的数据隔离
    public static final ThreadLocal<DataStore> dataStore = ThreadLocal.withInitial(DataStore::new);
}
