package com.java3y.hades.core.utils;


import com.google.common.base.Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 3y
 */
public class HadesCache {
    private static final Map<String, String> CODE_CACHE = new HashMap(128);

    public static void put2CodeCache(String key, String value) {
        CODE_CACHE.put(key, value);
    }

    public static String get2CodeCache(String key) {
        return CODE_CACHE.get(key);
    }

    /**
     * 判断传入的key是否跟缓存的相等
     *
     * @return
     */
    public static boolean diff(String key, String currentGroovyCode) {
        String originGroovyCode = get2CodeCache(key);
        if (!Strings.isNullOrEmpty(originGroovyCode) && originGroovyCode.equals(currentGroovyCode)) {
            return false;
        }
        return true;
    }

}
