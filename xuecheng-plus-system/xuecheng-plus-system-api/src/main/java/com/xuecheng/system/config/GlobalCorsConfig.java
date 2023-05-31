package com.xuecheng.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author MrMa
 * @version 1.0
 * @description 配置跨域过滤，解决跨域问题
 */
@Configuration
public class GlobalCorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        // 允许所有来源进行访问
        corsConfiguration.addAllowedOrigin("*");
        // 允许跨域发送cookie
        corsConfiguration.setAllowCredentials(true);
        // 放行全部原始头信息
        corsConfiguration.addAllowedHeader("*");
        // 允许所有请求方法跨域调用
        corsConfiguration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource basedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        // 第一个参数代表，这个路径进入过滤
        basedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(basedCorsConfigurationSource);
    }
}
