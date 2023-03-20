package com.java3y.hades.core.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 3y
 * 监听到的变更配置
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListenerConfig {

    /**
     * 实例名
     */
    private List<String> instanceNames;

    /**
     * 更新时间
     */
    private String updateTime;

}
