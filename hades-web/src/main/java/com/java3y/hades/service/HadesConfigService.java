package com.java3y.hades.service;


/**
 * 读取配置服务
 *
 * @author 3y
 */
public interface HadesConfigService {

    /**
     * 读取配置 (nacos/apollo)
     * @param key
     * @param defaultValue
     * @return
     */
    String getProperty(String key, String defaultValue);

    /**
     * 设置配置(nacos/apollo)
     * @param key
     * @param value
     */
    void setProperty(String key, String value);

}
