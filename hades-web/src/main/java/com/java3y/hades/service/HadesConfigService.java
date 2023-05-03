package com.java3y.hades.service;


import com.java3y.hades.domain.HadesParam;
import com.java3y.hades.vo.HadesConfigListVo;

import java.util.List;

/**
 * @author 3y
 * 配置服务
 */
public interface HadesConfigService {

    /**
     * 添加配置
     *
     * @param hadesParam
     */
    void addConfig(HadesParam hadesParam);

    /**
     * 获取所有的配置项(可输入关键词筛选)
     *
     * @param keywords 非必填
     * @return
     */
    List<HadesConfigListVo> getAllConfig(String keywords);

    /**
     * 删除配置项
     *
     * @param name 脚本名
     */
    void deleteConfig(String name);


}
