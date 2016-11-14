package com.wedgwoodtom.contest;

//import com.wedgwoodtom.contest.service.ContestManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
public class Application implements CommandLineRunner
{
    private static final Logger log = LoggerFactory.getLogger(Application.class);

//    @Resource
//    CustomerRepository repository;

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception
    {
    }

}
