package com.xiaoyanming.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

// 在入口类上标注注解即可
//@Configuration
//@EnableRedisHttpSession
public class SessionConfig extends AbstractHttpSessionApplicationInitializer {
//    @Bean
//    public JedisConnectionFactory connectionFactory() {
//        return new JedisConnectionFactory();
//    }
}