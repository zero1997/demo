package com.evan.miaosha.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author yangyifan05 <yangyifan05@kuaishou.com>
 * Created on 2020-08-03
 */

/**
 * 这里的@Configuration等价于@Bean注解，将该类注入容器，并标识它是一个configuration
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    UserArgumentResolver userArgumentResolver;

    /**
     * 是springMVC里面的方法
     * 为RequestMapping的方法注入参数，这样只要进行RequestMapping的方法都会通过分布式session取到值注入进去
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }
}
