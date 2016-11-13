package com.wedgwoodtom.contest.ui;

import com.mongodb.MongoClient;
//import com.wedgwoodtom.contest.service.ContestManager;
//import com.wedgwoodtom.contest.service.PlayerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.wedgwoodtom.contest.service")
//@PropertySource(value = "classpath:mongo-config.properties")
public class AppConfig
{
    // TODO: Add a properties file, etc
    // TODO: Where is the mongoDB named?

//    public @Bean
//    MongoTemplate mongoTemplate() throws Exception
//    {
//        MongoTemplate mongoTemplate =
//                new MongoTemplate(new MongoClient(),"test");
//        return mongoTemplate;
//    }


//    @Bean
//    public PlayerRepository playerRepository()
//    {
//    }

//    @Bean
//    public ContestManager contestManager()
//    {
//        return new ContestManager();
//    }


}
