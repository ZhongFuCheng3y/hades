package com.java3y.hades.core.service;


/**
 * @author 3y
 * 条件接口(给到hades-web实现，RuleType == 20 才需要用上）
 */
public interface ExecuteCondition {
    /**
     * 指定条件过滤
     *
     * @param string1 字符串参数1
     * @param string2 字符串参数2
     * @param string3 字符串参数3
     * @param long1   Long参数1
     * @param long2   Long参数2
     * @param long3   Long参数3
     * @return
     */
    Boolean execute(String string1, String string2, String string3, Long long1, Long long2, Long long3);
}
