package com.wedgwoodtom.contest.service;

import com.wedgwoodtom.test.data.Contest;
import com.wedgwoodtom.test.data.Player;
import com.wedgwoodtom.test.data.PlayerRankings;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by tompatterson on 11/6/16.
 */
public interface PlayerRankingsRepository extends MongoRepository<PlayerRankings, String>
{

    // Damn, totally cool that you can do this.
    public List<PlayerRankings> findByPlayerAndContest(Player player, Contest contest);

}