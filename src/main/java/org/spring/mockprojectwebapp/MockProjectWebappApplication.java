package org.spring.mockprojectwebapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class MockProjectWebappApplication {
    public static void main(String[] args) {
        SpringApplication.run(MockProjectWebappApplication.class, args);
    }
}
