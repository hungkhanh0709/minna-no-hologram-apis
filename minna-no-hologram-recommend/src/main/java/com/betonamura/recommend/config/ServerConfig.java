package com.betonamura.recommend.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Server configuration to ensure the application binds to all interfaces
 */
@Configuration
public class ServerConfig {
    
    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> tomcatCustomizer() {
        return factory -> {
            try {
                factory.setAddress(InetAddress.getByName("0.0.0.0"));
                factory.setPort(8080);
            } catch (UnknownHostException e) {
                throw new RuntimeException("Failed to set server address", e);
            }
        };
    }
}
