package org.div.shopswift.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                //開啟跨域請求
                .allowedOrigins("*")//.allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedMethods("*")
                .allowedHeaders("*");
    }

}
