package com.devsu.account;

import com.intuit.karate.junit5.Karate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AccountRunner {

    @LocalServerPort
    int port;

    @Karate.Test
    Karate testAccounts() {
        System.setProperty("karate.env", "test");
        System.setProperty("baseUrl", "http://localhost:" + port);
        return Karate.run("classpath:features/account.feature").relativeTo(getClass());
    }

}
