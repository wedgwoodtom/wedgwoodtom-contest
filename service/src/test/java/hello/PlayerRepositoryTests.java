package hello;

import com.wedgwoodtom.test.data.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlayerRepositoryTests
{
    @Autowired
    PlayerRepository repository;

    Player dave, oliver, carter;

    @Before
    public void setUp()
    {
        repository.deleteAll();

        dave = repository.save(new Player("Dave", "Matthews", "dave@music.com"));
        oliver = repository.save(new Player("Oliver August", "Matthews", "oliver@sake.com"));
        carter = repository.save(new Player("Carter", "Beauford", "carter@jimmi.com"));
    }

    @Test
    public void testFindByLastName()
    {
        List<Player> result = repository.findByLastName("Beauford");

        assertThat(result).hasSize(1).extracting("firstName").contains("Carter");
    }

    @Test
    public void testFindByExample()
    {

        Player probe = new Player(null, "Matthews", null);

        List<Player> result = repository.findAll(Example.of(probe));

        assertThat(result).hasSize(2).extracting("firstName").contains("Dave", "Oliver August");
    }

}
