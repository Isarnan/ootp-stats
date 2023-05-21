package com.felarca.ootp.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class BeanUtil {
    @Autowired
    private ApplicationContext applicationContext;

    public <T> T getBean(Class<T> beanClass) {
        return applicationContext.getBean(beanClass);
    }

}