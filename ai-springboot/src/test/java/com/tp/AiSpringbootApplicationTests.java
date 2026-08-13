package com.tp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:mysql://localhost:3306/mental_health_assistant?serverTimezone=UTC",
        "spring.datasource.username=root",
        "spring.datasource.password=root",
        "spring.ai.openai.api-key=test-api-key",
        "jwt.secret=test-jwt-secret-must-be-at-least-32-characters"
})
class AiSpringbootApplicationTests {

    @Test
    void contextLoads() {
    }

}
