package com.wedgwoodtom.contest.service;

import com.wedgwoodtom.test.data.Contest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ContestRepository extends MongoRepository<Contest, String>
{
    public Contest findByTitle(String title);

}

