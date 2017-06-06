package com.kutapps.keyten.shared.helpers;

import com.google.gson.Gson;

public class Json {

    static Gson gson = new Gson();

    public static String toJson(Object model) {
        return gson.toJson(model);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
