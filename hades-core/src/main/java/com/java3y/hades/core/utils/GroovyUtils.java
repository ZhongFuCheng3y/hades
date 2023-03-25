package com.java3y.hades.core.utils;

import com.google.common.base.Throwables;
import groovy.lang.GroovyClassLoader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author 3y
 */
@Slf4j
public class GroovyUtils {
    private static final GroovyClassLoader GROOVY_CLASS_LOADER = new GroovyClassLoader();

    public static Class parseClass(String instanceName, String groovyCode) {
        Class groovyClass = null;
        try {
            HadesCache.put2CodeCache(instanceName, DigestUtils.md5DigestAsHex(groovyCode.getBytes(StandardCharsets.UTF_8)));
            groovyClass = GROOVY_CLASS_LOADER.parseClass(groovyCode);
            log.info("Groovy解析:class=[{}]语法通过", instanceName);
        } catch (Exception e) {
            log.info("Groovy解析:class=[{}]语法错误，请检查！e:{}", instanceName, Throwables.getStackTraceAsString(e));
        }
        return groovyClass;
    }
}
