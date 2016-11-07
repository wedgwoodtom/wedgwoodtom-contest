package hello;

import com.wedgwoodtom.test.data.Contest;
import com.wedgwoodtom.test.data.Entry;
import com.wedgwoodtom.test.data.Player;
import com.wedgwoodtom.test.data.PlayerRankings;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = {SpringMongoConfiguration.class})
public class ContestManagerTest
{
    @Autowired
    ContestManager contestManager;

    Player dave, oliver, carter;
    Contest freestyle1, freestyle2;
    Entry entry1, entry2;

    @Before
    public void setUp()
    {
        contestManager.deleteContests();
        contestManager.deletePlayers();

        freestyle1 = contestManager.save(new Contest("Virtual Freestyle 1"));
        freestyle2 = contestManager.save(new Contest("Virtual Freestyle 2"));

        dave = contestManager.save(new Player("Dave", "Matthews", "dave@music.com"));
        oliver = contestManager.save(new Player("Oliver August", "Matthews", "oliver@sake.com"));
        carter = contestManager.save(new Player("Carter", "Beauford", "carter@jimmi.com"));

        entry1 = contestManager.save(new Entry("Routine 1", URI.create("url1"), dave));
        entry2 = contestManager.save(new Entry("Routine 2", URI.create("url2"), oliver));
    }

    @Test
    public void testSaveAndFindContest()
    {
        String title = UUID.randomUUID().toString();
        Contest contest = contestManager.save(new Contest(title));
        contest.setStartDate(new Date());
        contest.setEndDate(new Date());
        contest.setActive(true);

        contestManager.save(contest);

        Contest retrievedContest = contestManager.findContestById(contest.getId().toString());
        assertThat(contest).isNotNull();
        assertThat(contest).isEqualTo(retrievedContest);
        assertThat(contest).isNotSameAs(retrievedContest);

        assertThat(contest.getTitle()).isEqualTo(retrievedContest.getTitle());
        assertThat(contest.getStartDate()).isEqualTo(retrievedContest.getStartDate());
        assertThat(contest.getEndDate()).isEqualTo(retrievedContest.getEndDate());
        assertThat(contest.getActive()).isEqualTo(retrievedContest.getActive());

        // update
        retrievedContest.setActive(false);
        contestManager.save(retrievedContest);
        Contest updatedContest = contestManager.findContestById(contest.getId().toString());
        assertThat(updatedContest).isNotNull();
        assertThat(retrievedContest).isNotSameAs(updatedContest);
        assertThat(updatedContest.getActive()).isEqualTo(false);
    }

    @Test
    public void testFindAllContests()
    {
        List<Contest> contestList = contestManager.findAllContests();
        assertThat(contestList.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void testAddPlayerToContest()
    {
        assertThat(contestManager.findContestById(freestyle1.getId().toString()).getPlayerList().size()).isEqualTo(0);
        assertThat(contestManager.findContestById(freestyle2.getId().toString()).getPlayerList().size()).isEqualTo(0);

        contestManager.addPlayerToContest(dave, freestyle1);
        contestManager.addPlayerToContest(dave, freestyle1);
        contestManager.addPlayerToContest(dave, freestyle1);
        contestManager.addPlayerToContest(dave, freestyle2);

        assertThat(contestManager.findContestById(freestyle1.idAsString()).getPlayerList().size()).isEqualTo(1);
        assertThat(contestManager.findContestById(freestyle2.idAsString()).getPlayerList().size()).isEqualTo(1);

        Player fromDb = contestManager.findPlayerById(dave.idAsString());
        assertThat(fromDb).isNotNull();
        List<Contest> contestList = fromDb.getContests();
        assertThat(contestList.size()).isEqualTo(2);
        assertThat(fromDb.getContests().get(0)).isEqualTo(freestyle1);
        assertThat(fromDb.getContests().get(1)).isEqualTo(freestyle2);

        contestManager.addPlayerToContest(oliver, freestyle1);
        assertThat(contestManager.findContestById(freestyle1.getId().toString()).getPlayerList().size()).isEqualTo(2);
    }

    @Test
    public void testRemovePlayerFromContest()
    {
        Contest contest = contestManager.findContestById(freestyle1.idAsString());
        Player player = contestManager.findPlayerById(carter.idAsString());

        contestManager.addPlayerToContest(carter, freestyle1);
        Contest updatedContest = contestManager.findContestById(freestyle1.idAsString());
        assertThat(contest.getPlayerList().size()).isEqualTo(updatedContest.getPlayerList().size()-1);
        Player updatedPlayer = contestManager.findPlayerById(carter.idAsString());
        assertThat(player.getContests().size()).isEqualTo(updatedPlayer.getContests().size()-1);

        contestManager.removePlayerFromContest(carter, freestyle1);
        updatedContest = contestManager.findContestById(freestyle1.idAsString());
        assertThat(contest.getPlayerList().size()).isEqualTo(updatedContest.getPlayerList().size());
        updatedPlayer = contestManager.findPlayerById(carter.idAsString());
        assertThat(player.getContests().size()).isEqualTo(updatedPlayer.getContests().size());
    }

    @Test
    public void testAddEntryToContest()
    {
        Contest contest = contestManager.findContestById(freestyle1.idAsString());
        assertThat(contest.getEntryList().isEmpty()).isTrue();

        contest.getEntryList().add(entry1);
        contest.getEntryList().add(entry2);

        contestManager.save(contest);

        Contest updatedContest = contestManager.findContestById(freestyle1.idAsString());
        assertThat(updatedContest.getEntryList().size()).isEqualTo(2);
    }


    @Test
    public void testAddPlayerRankingsToContest()
    {
//        Contest contest = contestManager.findContestById(freestyle1.idAsString());
//        assertThat(contest.getPlayerEntryRankings().isEmpty()).isTrue();
//
//        contest.getPlayerEntryRankings().put(dave.idAsString(), Arrays.asList(entry1, entry2));
//        contestManager.save(contest);
//
//        Contest updatedContest = contestManager.findContestById(freestyle1.idAsString());
//        assertThat(updatedContest.getPlayerEntryRankings().size()).isEqualTo(1);

        Contest contest = contestManager.findContestById(freestyle1.idAsString());
        assertThat(contest.getPlayerRankingsList().size()).isEqualTo(0);

        PlayerRankings davesRankings = contestManager.save(new PlayerRankings(contest, dave,  Arrays.asList(entry1, entry2)));
        contest.getPlayerRankingsList().add(davesRankings);
        contestManager.save(contest);

        Contest updatedContest = contestManager.findContestById(freestyle1.idAsString());
        assertThat(updatedContest.getPlayerRankingsList().size()).isEqualTo(1);
    }

    @Test
    public void testFindPlayerRankings()
    {
        // TODO: Fix this
        PlayerRankings playerRankings = contestManager.findPlayerRankings(oliver, freestyle1);
        assertThat(playerRankings).isNull();

        //
        PlayerRankings playerRankings1 = contestManager.findPlayerRankings(oliver, freestyle1);
        assertThat(playerRankings1).isNull();

        Contest contest = contestManager.findContestById(freestyle1.idAsString());
        PlayerRankings rankings = contestManager.save(new PlayerRankings(contest, oliver,  Arrays.asList(entry1, entry2)));
        contest.getPlayerRankingsList().add(rankings);
        contestManager.save(contest);

        //
        playerRankings1 = contestManager.findPlayerRankings(oliver, freestyle1);
        assertThat(playerRankings1).isNotNull();

        playerRankings1 = contestManager.findPlayerRankings(oliver, freestyle2);
        assertThat(playerRankings1).isNull();

        Contest updatedContest = contestManager.findContestById(freestyle1.idAsString());
        playerRankings = contestManager.findPlayerRankings(oliver, freestyle1);
        assertThat(playerRankings).isNotNull();
    }


    // TODO: Should I just bag all the repository stuff and just use MongoTemplate?



}
