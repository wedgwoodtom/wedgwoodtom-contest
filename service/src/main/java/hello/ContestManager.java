package hello;

import com.wedgwoodtom.test.data.Contest;
import com.wedgwoodtom.test.data.Entry;
import com.wedgwoodtom.test.data.Player;
import com.wedgwoodtom.test.data.PlayerRankings;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ContestManager
{
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ContestRepository contestRepository;

    @Autowired
    EntryRepository entryRepository;

    @Autowired
    PlayerRankingsRepository playerRankingsRepository;


    public Contest save(Contest contest)
    {
        return contestRepository.save(contest);
    }

    public Player save(Player player)
    {
        return playerRepository.save(player);
    }

    public Entry save(Entry entry)
    {
        return entryRepository.save(entry);
    }

    public PlayerRankings save(PlayerRankings playerRankings)
    {
        return playerRankingsRepository.save(playerRankings);
    }

    public void addPlayerToContest(String playerId, String contestId)
    {
        Player player = playerRepository.findOne(playerId);
        Contest contest = contestRepository.findOne(contestId);

        player.addContest(contest);
        contest.addPlayer(player);

        playerRepository.save(player);
        contestRepository.save(contest);
    }

    public void removePlayerFromContest(String playerId, String contestId)
    {
        Player player = playerRepository.findOne(playerId);
        Contest contest = contestRepository.findOne(contestId);

        player.getContests().remove(contest);
        contest.getPlayerList().remove(player);

        playerRepository.save(player);
        contestRepository.save(contest);
    }


    public void deletePlayers()
    {
        playerRepository.deleteAll();
    }

    public void deleteContests()
    {
        contestRepository.deleteAll();
    }

    public Player findPlayerById(String playerId)
    {
        return playerRepository.findOne(playerId);
    }

    public Contest findContestById(String contestId)
    {
        return contestRepository.findOne(contestId);
    }

    public List<Contest> findAllContests()
    {
        return contestRepository.findAll(new Sort(Sort.Direction.ASC, "title"));
    }


}
