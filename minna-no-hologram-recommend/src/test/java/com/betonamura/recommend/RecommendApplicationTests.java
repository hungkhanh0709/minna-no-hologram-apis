package com.betonamura.recommend;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = { RecommendApplication.class, TestApplicationConfig.class })
@ActiveProfiles("test")
class RecommendApplicationTests {

    @Test
    void contextLoads() {
        // Test that Spring context loads successfully
        assertTrue(true);
    }

}
