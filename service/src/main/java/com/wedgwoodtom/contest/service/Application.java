package com.wedgwoodtom.contest.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * https://spring.io/guides/gs/accessing-data-mongodb/
 */

@SpringBootApplication
public class Application implements CommandLineRunner
{

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {

    }

}