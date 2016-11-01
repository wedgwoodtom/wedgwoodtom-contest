package hello;


import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class AppConfig
{
    public @Bean
    MongoTemplate mongoTemplate() throws Exception
    {
        MongoTemplate mongoTemplate =
                new MongoTemplate(new MongoClient(),"test");
//                new MongoTemplate(new MongoClient("127.0.0.1"),"yourdb");
        return mongoTemplate;
    }

}

