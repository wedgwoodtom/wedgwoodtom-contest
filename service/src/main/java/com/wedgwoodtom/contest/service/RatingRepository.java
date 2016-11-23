package com.wedgwoodtom.contest.service;

import com.wedgwoodtom.test.data.*;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatingRepository extends MongoRepository<Rating, String>
{
    public List<Rating> findByPlayerAndContest(Player player, Contest contest);
    public List<Rating> findByContest(Contest contest);

//    public List<Rating> findByPlayer(Player player);
//    public List<Rating> findByPlayerAndContestAndEntry(Player player, Contest contest, Entry entry);
//    public List<Rating> findByContestAndEntry(Contest contest, Entry entry);
}
