package com.java3y.hades.core.service.core;


/**
 * @author 3y
 * 配置服务
 */
public interface HadesConfig {

    /**
     * 启动配置变更监听器
     */
    void addListener();

    /**
     * 通过文件名获取得到文件内容
     *
     * @param fileName
     * @return
     */
    String getConfigValueByName(String fileName);
}
