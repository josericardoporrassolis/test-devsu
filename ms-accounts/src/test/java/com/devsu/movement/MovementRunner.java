package com.devsu.movement;

import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class MovementRunner {

    @LocalServerPort
    int port;

    @MockBean
    private KafkaTemplate<String, String> kafkaTemplate;

    @Karate.Test
    Karate testAccounts() {
        System.setProperty("karate.env", "test");
        System.setProperty("baseUrl", "http://localhost:" + port);
        return Karate.run("classpath:features/movement.feature").relativeTo(getClass());
    }

}
