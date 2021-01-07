package com.starvel.framework.config;

import com.starvel.gateway.filter.TokenFilter;
import com.starvel.hole.filter.STokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by skyyemperor on 2020-12-27 10:54
 * Description :
 */
@Configuration
public class FilterConfig {

    @Autowired
    private TokenFilter tokenFilter;

    @Autowired
    private STokenFilter sTokenFilter;


    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public FilterRegistrationBean tokenFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(tokenFilter);
        registration.addUrlPatterns("/*");
        registration.setName("tokenFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        return registration;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public FilterRegistrationBean sTokenFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(sTokenFilter);
        registration.addUrlPatterns("/api/hole/*");
        registration.setName("STokenFilter");
        registration.setOrder(1);
        Map<String, String> initParameters = new HashMap<String, String>();
        initParameters.put("excludes", "/api/hole/stoken/*");
        registration.setInitParameters(initParameters);
        return registration;
    }
}
