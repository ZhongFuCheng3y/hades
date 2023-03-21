package com.java3y.hades.core.service;


/**
 * @author 3y
 * 配置服务
 */
public interface HadesConfigService {

    /**
     * 刷新配置
     */
    void refresh();

    /**
     * 初始化
     */
    void init();


    /**
     * 通过配置名获取得到配置内容
     *
     * @param name
     * @return
     */
    String getConfigValueByName(String name);
}
