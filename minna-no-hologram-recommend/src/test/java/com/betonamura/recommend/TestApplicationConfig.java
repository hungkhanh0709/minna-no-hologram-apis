package com.betonamura.recommend;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.betonamura.recommend.config.RecommendationProperties;
import com.betonamura.recommend.data.DataProvider;
import com.betonamura.recommend.data.UserHistoryData;
import com.betonamura.recommend.data.UserInteractionData;
import com.betonamura.recommend.repository.SlmRepository;
import com.betonamura.recommend.repository.SlmRepositoryImpl;

@Configuration
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
public class TestApplicationConfig {
    @Bean
    @Primary
    public RecommendationProperties recommendationProperties() {
        RecommendationProperties props = new RecommendationProperties();
        RecommendationProperties.ModelConfig modelConfig = new RecommendationProperties.ModelConfig();
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
    @Primary
    public DataProvider dataProvider() {
        return new DataProvider();
    }

    @Bean
    @Primary
    public UserHistoryData userHistoryData() {
        return new UserHistoryData();
    }

    @Bean
    @Primary
    public UserInteractionData userInteractionData() {
        return new UserInteractionData();
    }

    @Bean
    @Primary
    public SlmRepository slmRepository(RecommendationProperties properties, DataProvider dataProvider,
            UserHistoryData userHistoryData) {
        return new SlmRepositoryImpl(properties, dataProvider, userHistoryData);
    }
}
