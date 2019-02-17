package com.glqdlt.ex.springsecurity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author glqdlt
 * 2019-02-17
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "myapp.datasource")
public class SimpleDataSource {

    private String driverClassName;
    private String url;
    private String username;
    private String password;

    @Bean(name= "myDataSource")
    public DataSource dataSource(){
        return DataSourceBuilder.create()
                .driverClassName(driverClassName)
                .url(url)
                .username(username)
                .password(password)
                .build();
    }

}
