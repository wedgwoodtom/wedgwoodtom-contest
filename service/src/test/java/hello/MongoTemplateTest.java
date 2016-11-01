package hello;

import com.wedgwoodtom.test.data.Player;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
/**
 * https://www.mkyong.com/mongodb/spring-data-mongodb-query-document/
 */
public class MongoTemplateTest
{
    @Autowired
    MongoTemplate mongoTemplate;

    @Before
    public void setUp()
    {
        mongoTemplate.dropCollection(Player.class);

        mongoTemplate.insert(new Player("Dave", "Matthews", "dave@itunes.com"));
        mongoTemplate.insert(new Player("Tim", "Collins", "tim@itunes.com"));
        mongoTemplate.insert(new Player("Joan", "Collins", "joan@itunes.com"));
    }

    @Test
    public void testBasicQuery()
    {
        // This is an example of using mongo template rather than their more magical apis
        BasicQuery basicQuery = new BasicQuery("{ firstName : 'Dave', lastName : 'Matthews' }");
        Player dave = mongoTemplate.findOne(basicQuery, Player.class);
        assertThat(dave).isNotNull();
        assertThat(dave.getLastName()).isEqualTo("Matthews");
    }

    @Test
    public void testNoResultFindOneQuery()
    {
        BasicQuery noResultQuery = new BasicQuery("{ firstName : 'NOTDave'}");
        Player noPlayer = mongoTemplate.findOne(noResultQuery, Player.class);
        assertThat(noPlayer).isNull();
    }

    @Test
    public void testMultiFieldQuery()
    {
        Query multiQuery = new Query();
        multiQuery.addCriteria(Criteria.where("firstName").is("Joan").and("lastName").is("Collins"));
        Player joan = mongoTemplate.findOne(multiQuery, Player.class);
        assertThat(joan).isNotNull();
        assertThat(joan.getLastName()).isEqualTo("Collins");
    }

    @Test
    public void testInQuery()
    {
        Query inQuery = new Query();
        inQuery.addCriteria(Criteria.where("firstName").in(Arrays.asList("Tim", "Joan")));
        List<Player> players = mongoTemplate.find(inQuery, Player.class);
        assertThat(players.size()).isEqualTo(2);
    }

    @Test
    public void testREQuery()
    {
        Query REQuery = new Query();
        REQuery.addCriteria(Criteria.where("lastName").regex("C.*s"));
        List<Player> players = mongoTemplate.find(REQuery, Player.class);
        assertThat(players.size()).isEqualTo(2);
    }

    @Test
    public void testSortResults()
    {
        Query inQuery = new Query();
        inQuery.addCriteria(Criteria.where("firstName").in(Arrays.asList("Tim", "Joan", "Dave")));
        inQuery.with(new Sort(Sort.Direction.ASC, "firstName"));
        List<Player> players = mongoTemplate.find(inQuery, Player.class);
        assertThat(players.size()).isEqualTo(3);
        assertThat(players.get(0).getFirstName()).isEqualTo("Dave");
        assertThat(players.get(1).getFirstName()).isEqualTo("Joan");
        assertThat(players.get(2).getFirstName()).isEqualTo("Tim");
    }

    // TODO: Finish
    @Test
    public void testComplexCriteria()
    {
        /**
         * Query query4 = new Query();

         // it hits error
         // query4.addCriteria(Criteria.where("age").lt(40).and("age").gt(10));

         query4.addCriteria(
         Criteria.where("age").exists(true).andOperator(
         Criteria.where("age").gt(10),
         Criteria.where("age").lt(40)
         )
         );
         */
    }


}
