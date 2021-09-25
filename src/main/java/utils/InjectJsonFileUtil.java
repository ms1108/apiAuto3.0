package utils;

import com.google.gson.Gson;
import lombok.SneakyThrows;

import static utils.ReadJsonUtil.readJsonFile;

public class InjectJsonFileUtil {
    @SneakyThrows
    public static <T> T injectJsonFile(Class<T> baseCase, String path) {
        String json = readJsonFile(path);
        Gson gson = new Gson();
        return gson.fromJson(json, baseCase);
    }
}
