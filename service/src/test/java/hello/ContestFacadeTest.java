package hello;

import com.wedgwoodtom.test.data.Contest;
import com.wedgwoodtom.test.data.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = {SpringMongoConfiguration.class})
public class ContestFacadeTest
{
    @Autowired
    ContestFacade contestFacade;

    Player dave, oliver, carter;
    Contest freestyle1, freestyle2;

    @Before
    public void setUp()
    {
        contestFacade.deleteContests();
        contestFacade.deletePlayers();

        freestyle1 = contestFacade.save(new Contest("Virtual Freestyle 1"));
        freestyle2 = contestFacade.save(new Contest("Virtual Freestyle 2"));

        dave = contestFacade.save(new Player("Dave", "Matthews", "dave@music.com"));
        oliver = contestFacade.save(new Player("Oliver August", "Matthews", "oliver@sake.com"));
        carter = contestFacade.save(new Player("Carter", "Beauford", "carter@jimmi.com"));
    }

    @Test
    public void testAddContest()
    {
        // TODO: Not using direct list modified in order to avoid duplicates
        //  Could also use a set

//        dave.getContests().add(freestyle1);
//        dave.getContests().add(freestyle1);
//        dave.getContests().add(freestyle1);

        dave.addContest(freestyle1);
        dave.addContest(freestyle1);
        dave.addContest(freestyle1);
        dave.addContest(freestyle2);

        contestFacade.save(dave);

        Player fromDb = contestFacade.findPlayerById(dave.getId().toString());
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.getContests().size()).isEqualTo(2);
        assertThat(fromDb.getContests().get(0)).isEqualTo(freestyle1);
        assertThat(fromDb.getContests().get(1)).isEqualTo(freestyle2);
    }

}
