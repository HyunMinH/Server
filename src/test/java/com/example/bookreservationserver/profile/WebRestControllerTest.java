package com.example.bookreservationserver.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class WebRestControllerTest {
    @Autowired
    private TestRestTemplate testRestTemplate;

    public void checkProfile() {
        String profile = this.testRestTemplate.getForObject("/profile", String.class);


        assertEquals("local", profile);
    }
}