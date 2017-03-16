package com.basaki.example.docker.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * {@code BookApplication} represents the entry point for book controller
 * spring boot application.
 * <p/>
 *
 * @author Indra Basak
 * @since 2/23/17
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.basaki.example.docker.config",
        "com.basaki.example.docker.controller",
        "com.basaki.example.docker.error",
        "com.basaki.example.docker.model",
        "com.basaki.example.docker.service",
        "com.basaki.example.docker.util"})
public class BookApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }
}
