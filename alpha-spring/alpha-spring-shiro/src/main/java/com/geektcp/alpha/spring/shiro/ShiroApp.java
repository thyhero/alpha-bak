package com.geektcp.alpha.spring.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author MrBird
 */
@EnableAsync
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("cc.mrbird.febs.*.mapper")
public class ShiroApp {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ShiroApp.class).run(args);
    }

}
