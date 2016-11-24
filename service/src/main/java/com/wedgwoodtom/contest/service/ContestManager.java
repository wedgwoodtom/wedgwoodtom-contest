package com.wedgwoodtom.contest.service;

import com.wedgwoodtom.test.data.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class ContestManager
{
    @Resource
    PlayerRepository playerRepository;

    @Resource
    ContestRepository contestRepository;

    @Resource
    EntryRepository entryRepository;

    @Resource
    PlayerRankingsRepository playerRankingsRepository;

    @Resource
    RatingRepository ratingRepository;


    public Contest save(Contest contest)
    {
        return contestRepository.save(contest);
    }

    public void delete(Contest contest)
    {
        contestRepository.delete(contest);
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

    public Rating save(Rating rating)
    {
        return ratingRepository.save(rating);
    }

    public void addPlayerToContest(Player player, Contest contest)
    {
        player.addContest(contest);
        contest.addPlayer(player);

        playerRepository.save(player);
        contestRepository.save(contest);
    }

    public void removePlayerFromContest(Player player, Contest contest)
    {
        player.getContests().remove(contest);
        contest.getPlayerList().remove(player);

        playerRepository.save(player);
        contestRepository.save(contest);
    }

    public PlayerRankings findPlayerRankings(Player player, Contest contest)
    {
        List<PlayerRankings> list = playerRankingsRepository.findByPlayerAndContest(player, contest);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Rating> findRatings(Player player, Contest contest)
    {
        return ratingRepository.findByPlayerAndContest(player, contest);
    }

    public List<Rating> findRatings(Contest contest)
    {
        return ratingRepository.findByContest(contest);
    }

    public Contest addContestEntry(Contest contest, Entry entry)
    {
        contest.getEntryList().remove(entry);
        contest.getEntryList().add(entry);

        return contestRepository.save(contest);
    }

    public synchronized Contest startPlayerRanking(Contest contest)
    {
        // TODO: This will be called on a timer, should check start date, etc

        List<Player> playersWithEntries = contest.getEntryList().stream()
                .map(entry -> entry.getPlayer())
                .collect(Collectors.toList());

        List<Player> disqualifiedPlayers = new ArrayList<>();
        contest.getPlayerList().stream()
                .forEach( player -> {
                    if (playersWithEntries.contains(player))
                    {
                        PlayerRankings playerRankings = new PlayerRankings(contest, player, contest.getEntryList());
                        playerRankingsRepository.save(playerRankings);

                        // TODO: Do a sanity check - there should be no ratings for contest/player
                        //save a default rating for each player
                        contest.getEntryList().forEach( entry -> {
                            // do not add entry for yourself
                            if (!player.equals(entry.getPlayer()))
                            {
                                Rating ratingForPlayer = new Rating(contest, player, entry);
                                ratingRepository.save(ratingForPlayer);
                            }
                        });
                    }
                    else
                    {
                        disqualifiedPlayers.add(player);
                    }
                } );

        contest.setPlayerList(playersWithEntries);
        contest.setDisqualifiedPlayers(disqualifiedPlayers);
        contest.setStatus(ContestStatus.PlayerRanking);

        // TODO: Send messages to contestants

        return contestRepository.save(contest);
    }


    public void deletePlayers()
    {
        playerRepository.deleteAll();
    }

    public void deleteContests()
    {
        contestRepository.deleteAll();
    }

    public void deleteRatings()
    {
        ratingRepository.deleteAll();
    }

    public Player findPlayerById(String playerId)
    {
        return playerRepository.findOne(playerId);
    }

    public Contest findContestById(String contestId)
    {
        return contestRepository.findOne(contestId);
    }

    public Contest findContestByTitle(String contestTitle)
    {
        return contestRepository.findByTitle(contestTitle);
    }

    public List<Contest> findAllContests()
    {
        return contestRepository.findAll(new Sort(Sort.Direction.ASC, "title"));
    }


}
