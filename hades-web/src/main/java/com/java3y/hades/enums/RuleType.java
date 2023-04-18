package com.java3y.hades.enums;

import com.java3y.hades.enums.base.PowerfulEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;


/**
 * @author 3y
 * 规则类型
 */
@Getter
@ToString
@AllArgsConstructor
public enum RuleType implements PowerfulEnum {

    /**
     * 10.Java原生代码
     */
    JAVA_CODE(10, "Java原生代码"),
    /**
     * 20.页面逻辑规则
     */
    WEB_LOGIC(20, "审核成功");

    private final Integer code;
    private final String description;

}
