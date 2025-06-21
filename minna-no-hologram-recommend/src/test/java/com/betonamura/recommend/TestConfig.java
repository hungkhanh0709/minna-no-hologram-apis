package com.betonamura.recommend;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.betonamura.recommend.config.RecommendationProperties;
import com.betonamura.recommend.data.DataProvider;
import com.betonamura.recommend.data.UserHistoryData;
import com.betonamura.recommend.data.UserInteractionData;
import com.betonamura.recommend.service.SlmService;
import com.betonamura.recommend.service.impl.SlmServiceImpl;

/**
 * Test configuration to disable database-related auto-configuration.
 */
@Configuration
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class TestConfig {
    @Bean
    public RecommendationProperties recommendationProperties() {
        RecommendationProperties props = new RecommendationProperties();
        RecommendationProperties.ModelConfig modelConfig = new RecommendationProperties.ModelConfig();
        modelConfig.setName("test-model");
        modelConfig.setName("test-model");
        modelConfig.setMaxLength(128);
        modelConfig.setBatchSize(16);
        props.setModel(modelConfig);
        props.setDefaultLimit(10);

        // Set up cache config
        RecommendationProperties.CacheConfig cacheConfig = new RecommendationProperties.CacheConfig();
        cacheConfig.setTtlMinutes(5);
        cacheConfig.setMaxSize(100);
        props.setCache(cacheConfig);
        return props;
    }

    @Bean
    public DataProvider dataProvider() {
        return new DataProvider();
    }

    @Bean
    public UserHistoryData userHistoryData() {
        return new UserHistoryData();
    }

    @Bean
    public UserInteractionData userInteractionData() {
        return new UserInteractionData();
    }

    @Bean
    public SlmService slmService(RecommendationProperties properties, DataProvider dataProvider,
            UserHistoryData userHistoryData) {
        return new SlmServiceImpl(properties, dataProvider, userHistoryData);
    }
}
