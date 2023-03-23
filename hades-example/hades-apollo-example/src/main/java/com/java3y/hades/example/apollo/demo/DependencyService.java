package com.java3y.hades.example.apollo.demo;

import com.java3y.hades.core.HadesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 3y
 * 测试的脚本依赖该对象
 */
@Service
public class DependencyService {

    @Autowired
    private HadesClient hadesClient;

    public HadesClient getHadesClient() {
        return hadesClient;
    }
}
