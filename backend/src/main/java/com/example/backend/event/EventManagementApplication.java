package com.example.backend.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Event Management Module.
 * @SpringBootApplication enables auto-configuration, component scanning, and configuration.
 */
@SpringBootApplication
public class EventManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventManagementApplication.class, args);
    }
}

