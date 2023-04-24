package com.java3y.hades.vo;


import com.java3y.hades.enums.RuleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 3y
 * hades配置返回类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HadesConfigListVo {

    /**
     * 全限定类名(包名+类名)
     * eg:com.java3y.hades.core.constant.HadesConstant
     */
    private String name;

    /**
     * 规则类型
     *
     * @see RuleType
     */
    private String ruleType;

    /**
     * 规则 逻辑图
     */
    private String ruleLogicGraph;

    /**
     * 规则 脚本代码
     */
    private String ruleLogicCode;
}
