package com.java3y.hades.service.impl;



import com.java3y.hades.core.service.config.HadesConfigProperties;
import com.java3y.hades.domain.HadesParam;
import com.java3y.hades.service.HadesConfigService;
import com.java3y.hades.vo.HadesConfigListVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author 3y
 * TODO Apollo配置实现类（未完成）
 */
@Service
@Slf4j
@ConditionalOnProperty(name = "apollo.bootstrap.enabled", havingValue = "true")
@Deprecated
public class HadesApolloConfigServiceImpl implements HadesConfigService {

    @Autowired
    protected HadesConfigProperties configProperties;
    @Value("apollo.portal.address")
    private String address;


    @Override
    public void addConfig(HadesParam hadesParam) {

    }

    @Override
    public List<HadesConfigListVo> getAllConfig(String keywords) {
        return null;
    }

    @Override
    public void deleteConfig(String name) {

    }
}
