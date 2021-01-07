package com.starvel.admin;

import com.starvel.gateway.filter.TokenFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.starvel.**")
@MapperScan("com.starvel.*.mapper")
public class HoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(HoleApplication.class, args);
    }


}
