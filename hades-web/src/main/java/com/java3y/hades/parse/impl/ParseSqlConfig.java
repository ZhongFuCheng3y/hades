package com.java3y.hades.parse.impl;


import com.alibaba.fastjson2.JSON;
import com.java3y.hades.parse.ParseConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 3y
 * 将条件解析成sql配置
 */
@Deprecated
public class ParseSqlConfig implements ParseConfig {

    @Override
    public String parse(String config) {
        return generateSqlCondition(JSON.parseObject(config, Map.class));
    }

    private static String generateSqlCondition(Map<String, Object> config) {
        String conjunction = (String) config.get("conjunction");
        List<Map<String, Object>> children = (List<Map<String, Object>>) config.get("children");
        List<String> childConditions = new ArrayList<>();

        for (Map<String, Object> child : children) {
            Map<String, Object> left = (Map<String, Object>) child.get("left");
            if (left == null) {
                // 如果left为null，则认为是一个组合类型的节点
                String childCondition = generateSqlCondition(child);
                childConditions.add(childCondition);
            } else {
                String type = (String) left.get("type");

                if (type != null && type.equals("field")) {
                    // 处理字段类型的节点
                    String field = (String) left.get("field");
                    String op = (String) child.get("op");
                    Object right = child.get("right");

                    String childCondition = generateSqlFieldCondition(field, op, right);
                    childConditions.add(childCondition);
                } else {
                    // 处理组合类型的节点
                    String childCondition = generateSqlCondition(child);
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

    private static String generateSqlFieldCondition(String field, String op, Object right) {
        if (op.equals("is_not_empty")) {
            return field + " is not null";
        } else if (op.equals("equal")) {
            if (right instanceof String) {
                return field + " = '" + right + "'";
            } else {
                return field + " = " + right;
            }
        } else if (op.equals("not_equal")) {
            if (right instanceof String) {
                return field + " <> '" + right + "'";
            } else {
                return field + " <> " + right;
            }
        } else if (op.equals("is_empty")) {
            return field + " is null";
        } else if (op.equals("like")) {
            return field + " like '" + right + "'";
        } else if (op.equals("not_like")) {
            return field + " not like '" + right + "'";
        } else if (op.equals("starts_with")) {
            return field + " like '" + right + "%'";
        } else if (op.equals("ends_with")) {
            return field + " like '%" + right + "'";
        } else if (op.equals("less")) {
            return field + " < " + right;
        } else if (op.equals("less_or_equal")) {
            return field + " <= " + right;
        } else if (op.equals("greater")) {
            return field + " > " + right;
        } else if (op.equals("greater_or_equal")) {
            return field + " >= " + right;
        } else if (op.equals("between")) {
            List<Integer> range = (List<Integer>) right;
            return field + " between " + range.get(0) + " and " + range.get(1);
        } else if (op.equals("not_between")) {
            List<Integer> range = (List<Integer>) right;
            return field + " not between " + range.get(0) + " and " + range.get(1);
        } else {
            throw new IllegalArgumentException("Unsupported operator: " + op);
        }
    }

}
