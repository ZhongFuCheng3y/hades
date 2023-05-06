package com.java3y.hades.parse.impl;


import com.alibaba.fastjson2.JSON;
import com.java3y.hades.parse.ParseConfig;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 将条件解析成java配置
 *
 * @author 3y
 */
@Component
public class ParseJavaConfig implements ParseConfig {

    private static final String IS_NOT_EMPTY = "is_not_empty";
    private static final String EQUAL = "equal";
    private static final String NOT_EQUAL = "not_equal";
    private static final String IS_EMPTY = "is_empty";
    private static final String LIKE = "like";
    private static final String NOT_LIKE = "not_like";
    private static final String STARTS_WITH = "starts_with";
    private static final String ENDS_WITH = "ends_with";
    private static final String LESS = "less";
    private static final String LESS_OR_EQUAL = "less_or_equal";
    private static final String GREATER = "greater";
    private static final String GREATER_OR_EQUAL = "greater_or_equal";
    private static final String BETWEEN = "between";
    private static final String NOT_BETWEEN = "not_between";
    private static final String AND = "and";
    private static final String OR = "or";

    @Override
    public String parse(String config) {
        return generateJavaCondition(JSON.parseObject(config, Map.class));
    }

    private static String generateJavaFieldCondition(String field, String op, Object right) {

        if (op.equals(IS_NOT_EMPTY)) {
            return field + " !=null";
        } else if (op.equals(EQUAL)) {
            if (right instanceof String) {
                return field + ".equals(" + "\"" + right + "\"" + ")";
            } else {
                return field + " == " + right + "L";
            }
        } else if (op.equals(NOT_EQUAL)) {
            if (right instanceof String) {
                return "!" + field + ".equals(" + "\"" + right + "\"" + ")";
            } else {
                return field + " != " + right + "L";
            }
        } else if (op.equals(IS_EMPTY)) {
            return field + " == null ";
        } else if (op.equals(LIKE)) {
            return field + ".contains(" + "\"" + right + "\"" + ")";
        } else if (op.equals(NOT_LIKE)) {
            return "!" + field + ".contains(" + "\"" + right + "\"" + ")";
        } else if (op.equals(STARTS_WITH)) {
            return field + ".startsWith(" + "\"" + right + "\"" + ")";
        } else if (op.equals(ENDS_WITH)) {
            return field + ".endsWith(" + "\"" + right + "\"" + ")";
        } else if (op.equals(LESS)) {
            return field + ".longValue()" + " < " + right + "L";
        } else if (op.equals(LESS_OR_EQUAL)) {
            return field + ".longValue()" + " <= " + right + "L";
        } else if (op.equals(GREATER)) {
            return field + ".longValue()" + " > " + right + "L";
        } else if (op.equals(GREATER_OR_EQUAL)) {
            return field + ".longValue()" + " >= " + right + "L";
        } else if (op.equals(BETWEEN)) {
            List<Integer> range = (List<Integer>) right;
            return "(" + field + ".longValue()" + " >= " + range.get(0) + "L" + " && " + field + ".longValue()" + " <= " + range.get(1) + "L" + ")";
        } else if (op.equals(NOT_BETWEEN)) {
            List<Integer> range = (List<Integer>) right;
            return "(" + field + ".longValue()" + " < " + range.get(0) + "L" + " || " + field + ".longValue()" + ">" + range.get(1) + "L" + ")";
        } else {
            throw new IllegalArgumentException("Unsupported operator: " + op);
        }
    }

    private static String generateJavaCondition(Map<String, Object> config) {
        String conjunction = (String) config.get("conjunction");
        if (AND.equals(conjunction)) {
            conjunction = "&&";
        } else if (OR.equals(conjunction)) {
            conjunction = "||";
        }
        List<Map<String, Object>> children = (List<Map<String, Object>>) config.get("children");
        List<String> childConditions = new ArrayList<>();

        for (Map<String, Object> child : children) {
            Map<String, Object> left = (Map<String, Object>) child.get("left");
            if (left == null) {
                // 如果left为null，则认为是一个组合类型的节点
                String childCondition = generateJavaCondition(child);
                childConditions.add(childCondition);
            } else {
                String type = (String) left.get("type");

                if (type != null && type.equals("field")) {
                    // 处理字段类型的节点
                    String field = (String) left.get("field");
                    String op = (String) child.get("op");
                    Object right = child.get("right");

                    String childCondition = generateJavaFieldCondition(field, op, right);
                    childConditions.add(childCondition);
                } else {
                    // 处理组合类型的节点
                    String childCondition = generateJavaCondition(child);
                    childConditions.add(childCondition);
                }
            }
        }

        String condition = String.join(" " + conjunction + " ", childConditions);
        if (children.size() > 1) {
            condition = "(" + condition + ")";
        }
        return condition;
    }
}