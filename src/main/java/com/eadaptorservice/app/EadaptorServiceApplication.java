package com.eadaptorservice.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.eadaptorservice"})
public class EadaptorServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EadaptorServiceApplication.class, args);
    }

}
