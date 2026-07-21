package com.miniredis.store;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Store {

    private final Map<String, String> map = new HashMap<>();

    public void set(String key, String value) {
        map.put(key, value);
    }

    public Optional<String> get(String key) {
        return Optional.ofNullable(map.get(key));
    }

    public boolean delete(String key) {
        return map.remove(key) != null;
    }

    public boolean exists(String key) {
        return map.containsKey(key);
    }
}
