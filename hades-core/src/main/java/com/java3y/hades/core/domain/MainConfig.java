package com.java3y.hades.core.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 3y
 * 主配置
 * eg：{"instanceNames":["com.java3y.hades.core.constant.HadesConstant"],"updateTime":"2023年3月20日10:26:0131"}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainConfig {

    /**
     * 全限定类名(包名+类名)
     * eg:com.java3y.hades.core.constant.HadesConstant
     */
    private List<String> instanceNames;

    /**
     * 更新时间
     */
    private Long updateTime;

}
