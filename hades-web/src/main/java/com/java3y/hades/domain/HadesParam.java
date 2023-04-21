package com.java3y.hades.domain;

import com.java3y.hades.enums.RuleType;

/**
 * hades web入参
 *
 * @author 3y
 */
public class HadesParam {

    /**
     * 规则脚本名
     * 同等于 类名
     */
    private String className;

    /**
     * 规则域
     * 同等于 nacos 的group和 apollo namespace的前缀
     */
    private String domain;

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
