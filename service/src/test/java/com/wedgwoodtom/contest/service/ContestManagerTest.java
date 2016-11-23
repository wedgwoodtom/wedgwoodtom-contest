package com.wedgwoodtom.contest.service;

import com.wedgwoodtom.test.data.*;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = {SpringMongoConfiguration.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
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
        contestManager.deleteRatings();

        freestyle1 = contestManager.save(new Contest("Virtual Freestyle 1"));
        freestyle2 = contestManager.save(new Contest("Virtual Freestyle 2"));

        dave = contestManager.save(new Player("Dave", "Matthews", "dave@music.com"));
        oliver = contestManager.save(new Player("Oliver August", "Matthews", "oliver@sake.com"));
        carter = contestManager.save(new Player("Carter", "Beauford", "carter@jimmi.com"));

        entry1 = contestManager.save(new Entry(dave, "Routine 1", "https://www.youtube.com/watch?v=rIN6sjaKSHs"));
        entry2 = contestManager.save(new Entry(oliver, "Routine 2", "https://www.youtube.com/watch?v=40Bll2hy17A"));
    }

    @Test
    public void test1SaveAndFindContest()
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
    public void test2FindAllContests()
    {
        List<Contest> contestList = contestManager.findAllContests();
        assertThat(contestList.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void test3AddPlayerToContest()
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
    public void test4RemovePlayerFromContest()
    {
        Contest contest = contestManager.findContestById(freestyle1.idAsString());
        Player player = contestManager.findPlayerById(carter.idAsString());

        contestManager.addPlayerToContest(carter, freestyle1);
        Contest updatedContest = contestManager.findContestById(freestyle1.idAsString());
        assertThat(contest.getPlayerList().size()).isEqualTo(updatedContest.getPlayerList().size() - 1);
        Player updatedPlayer = contestManager.findPlayerById(carter.idAsString());
        assertThat(player.getContests().size()).isEqualTo(updatedPlayer.getContests().size() - 1);

        contestManager.removePlayerFromContest(carter, freestyle1);
        updatedContest = contestManager.findContestById(freestyle1.idAsString());
        assertThat(contest.getPlayerList().size()).isEqualTo(updatedContest.getPlayerList().size());
        updatedPlayer = contestManager.findPlayerById(carter.idAsString());
        assertThat(player.getContests().size()).isEqualTo(updatedPlayer.getContests().size());
    }

    @Test
    public void test5AddEntryToContest()
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
    public void test7AddPlayerRankingsToContest()
    {
        Contest contest = contestManager.findContestById(freestyle1.idAsString());
        assertThat(contest.getPlayerRankingsList().size()).isEqualTo(0);

        PlayerRankings davesRankings = contestManager.save(new PlayerRankings(contest, dave, Arrays.asList(entry1, entry2)));
        contest.getPlayerRankingsList().add(davesRankings);
        contestManager.save(contest);

        Contest updatedContest = contestManager.findContestById(freestyle1.idAsString());
        assertThat(updatedContest.getPlayerRankingsList().size()).isEqualTo(1);
    }

    @Test
    public void test8FindPlayerRankings()
    {
        // TODO: Fix this
        PlayerRankings playerRankings = contestManager.findPlayerRankings(oliver, freestyle1);
        assertThat(playerRankings).isNull();

        //
        PlayerRankings playerRankings1 = contestManager.findPlayerRankings(oliver, freestyle1);
        assertThat(playerRankings1).isNull();

        Contest contest = contestManager.findContestById(freestyle1.idAsString());
        PlayerRankings rankings = contestManager.save(new PlayerRankings(contest, oliver, Arrays.asList(entry1, entry2)));
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


    // TODO: This is really the test data setup which should exercise most
    //  functionality
    @Test
    public void test999SetupTestContestData()
    {
        // contests
        contestManager.save(new Contest("Skateboard Freestyle 1"));
        contestManager.save(new Contest("Dabbing it up Contest #1"));
        contestManager.save(new Contest("PSPS Pugs Agility Contest #1"));

        // players
        Player john = contestManager.save(new Player("John", "Smith", "john@fly.com"));

        // players to contest
        contestManager.addPlayerToContest(dave, freestyle1);
        contestManager.addPlayerToContest(carter, freestyle1);
        contestManager.addPlayerToContest(oliver, freestyle1);
        contestManager.addPlayerToContest(john, freestyle1);

        contestManager.addContestEntry(freestyle1,
                contestManager.save(new Entry(dave, "My VF Entry", "https://www.youtube.com/watch?v=rIN6sjaKSHs")));
        contestManager.addContestEntry(freestyle1,
                contestManager.save(new Entry(carter, "My VF Entry", "https://www.youtube.com/watch?v=40Bll2hy17A")));

        // start the contest
        Contest startedContest = contestManager.startPlayerRanking(freestyle1);

        assertThat(startedContest.getDisqualifiedPlayers().size()).isEqualTo(2);
        assertThat(startedContest.getDisqualifiedPlayers()).contains(oliver, john);

        assertThat(startedContest.getPlayerList().size()).isEqualTo(2);
        assertThat(startedContest.getPlayerList()).contains(dave, carter);

        startedContest.getPlayerList()
                .forEach(player -> {
                    PlayerRankings playerRankings = contestManager.findPlayerRankings(player, startedContest);
                    assertThat(playerRankings).isNotNull();
                    assertThat(playerRankings.getPlayer()).isEqualTo(player);
                });

        startedContest.getDisqualifiedPlayers()
                .forEach(player -> {
                    PlayerRankings playerRankings = contestManager.findPlayerRankings(player, startedContest);
                    assertThat(playerRankings).isNull();
                });

        // he was disqualified
        List<Rating> oliversRatings = contestManager.findRatings(oliver, freestyle1);
        assertThat(oliversRatings.size()).isEqualTo(0);

        List<Rating> davesRatings = contestManager.findRatings(dave, freestyle1);
        assertThat(davesRatings.size()).isEqualTo(1);
        Rating davesRating = davesRatings.get(0);
        assertThat(davesRating.getContest()).isEqualTo(freestyle1);
        assertThat(davesRating.getPlayer()).isEqualTo(dave);
        assertThat(davesRating.getEntry().getPlayer()).isEqualTo(carter);
        assertThat(davesRating.getComments()).isEmpty();
        assertThat(davesRating.getScore()).isNull();
        assertThat(davesRating.getSourceId()).isEqualTo(dave.idAsString());

        List<Rating> cartersRatings = contestManager.findRatings(carter, freestyle1);
        assertThat(cartersRatings.size()).isEqualTo(1);
        Rating cartersRating = cartersRatings.get(0);
        assertThat(cartersRating.getContest()).isEqualTo(freestyle1);
        assertThat(cartersRating.getPlayer()).isEqualTo(carter);
        assertThat(cartersRating.getEntry().getPlayer()).isEqualTo(dave);

        cartersRating.setComments("Hella Good Bro!");
        cartersRating.setScore(90);
        contestManager.save(cartersRating);
        Rating cartersUpdatedRating = contestManager.findRatings(carter, freestyle1).get(0);
        assertThat(cartersUpdatedRating.getComments().equals("Hella Good Bro!"));
        assertThat(cartersUpdatedRating.getScore().equals(90));

        List<Rating> allContestRatings = contestManager.findRatings(freestyle1);
        assertThat(allContestRatings.size()).isEqualTo(2);
    }


    // TODO: Should I just bag all the repository stuff and just use MongoTemplate?


}
