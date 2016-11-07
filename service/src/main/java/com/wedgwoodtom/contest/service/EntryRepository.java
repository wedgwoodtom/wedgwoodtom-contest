package com.wedgwoodtom.contest.service;

import com.wedgwoodtom.test.data.Entry;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by tompatterson on 11/6/16.
 */
public interface EntryRepository extends MongoRepository<Entry, String>
{
    public Entry findByTitle(String title);

}
