 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xsess.sessmanager;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;
import org.springframework.transaction.PlatformTransactionManager;

/**
 *
 * @author diego
 */
//**
@Configuration
@EnableRedisHttpSession
public class HttpSessionConfig {
    
    @Bean
    public JedisConnectionFactory connectionFactory(){
        return new JedisConnectionFactory();
    }
    
    
    @Bean
    public HttpSessionStrategy httpSessionStrategy(){
        return new HeaderHttpSessionStrategy();
    }
    
}
//*/
/**
@Configuration
@EnableJdbcHttpSession
public class HttpSessionConfig {
    
    @Bean
    public DataSource dataSource(){
        //TODO benchmark pointing to mysql.
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/sessManager");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        
        
        return dataSource;
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.DERBY)
//                .addScript("schema-derby.sql")
//                .build();
        
    }
    
    @Bean
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }
    
    @Bean
    public HttpSessionStrategy httpSessionStrategy(){
        return new HeaderHttpSessionStrategy();
    }
    
}
//*/

