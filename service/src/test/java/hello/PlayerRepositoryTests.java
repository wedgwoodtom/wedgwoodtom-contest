package hello;

import com.wedgwoodtom.test.data.Contest;
import com.wedgwoodtom.test.data.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerRepositoryTests
{
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ContestRepository contestRepository;

    Player dave, oliver, carter;
    Contest freestyle1, freestyle2;

    @Before
    public void setUp()
    {
        playerRepository.deleteAll();

        dave = playerRepository.save(new Player("Dave", "Matthews", "dave@music.com"));
        oliver = playerRepository.save(new Player("Oliver August", "Matthews", "oliver@sake.com"));
        carter = playerRepository.save(new Player("Carter", "Beauford", "carter@jimmi.com"));
    }

    @Test
    public void testFindByLastName()
    {
        List<Player> result = playerRepository.findByLastName("Beauford");

        assertThat(result).hasSize(1).extracting("firstName").contains("Carter");
    }

    @Test
    public void testFindByExample()
    {
        Player probe = new Player(null, "Matthews", null);

        List<Player> result = playerRepository.findAll(Example.of(probe));

        assertThat(result).hasSize(2).extracting("firstName").contains("Dave", "Oliver August");
    }

    /**
     * /**
     * Query query = new Query();
     query.limit(10);
     query.with(new Sort(Sort.Direction.DESC, "lastModifiedDate"));

     mongoOperation.find(query, Domain.class);
     */

}
