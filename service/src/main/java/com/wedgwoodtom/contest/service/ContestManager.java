package com.wedgwoodtom.contest.service;

import com.wedgwoodtom.test.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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
