package com.devsu.karate;

import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CustomerRunner {

    @LocalServerPort
    int port;

    @Karate.Test
    Karate testCustomers() {
        System.setProperty("karate.env", "test");
        System.setProperty("baseUrl", "http://localhost:" + port);
        return Karate.run("classpath:feature/customer.feature").relativeTo(getClass());
    }

}
