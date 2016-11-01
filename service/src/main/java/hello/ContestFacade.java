package hello;

import com.wedgwoodtom.test.data.Contest;
import com.wedgwoodtom.test.data.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ContestFacade
{
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    ContestRepository contestRepository;

    public Contest save(Contest contest)
    {
        return contestRepository.save(contest);
    }

    public Player save(Player player)
    {
        return playerRepository.save(player);
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

}
