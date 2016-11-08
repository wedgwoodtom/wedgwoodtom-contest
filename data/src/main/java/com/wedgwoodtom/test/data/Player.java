package com.wedgwoodtom.test.data;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document
public class Player extends BaseObject
{
    // TODO: Need to add
    // TODO: Player videos for each contest they enter

    // TODO: User votes - user must register in order to vote
    // TODO: need auto password reset
    // TODO: Users can vote for any contest they want to, but only once (order them)

    // TODO: Perhaps have PlayerVotes - need to model this all out much further

    private String firstName;

    @Indexed
    private String lastName;

    @Indexed(unique = true)
    private String email;

    @DBRef(lazy = true)
    private List<Contest> contests = new ArrayList<>();

    // TODO: Can use Set if concerned about duplicates
//    private Set<Contest> contests = new HashSet<>();

    @PersistenceConstructor
    public Player(String firstName, String lastName, String email)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public List<Contest> getContests()
    {
        return contests;
    }

    public void setContests(List<Contest> contests)
    {
        this.contests = contests;
    }

    public void addContest(Contest contest)
    {
        // TODO: Use this method in order to avoid duplicates
        if (!contests.contains(contest))
        {
            contests.add(contest);
        }
    }

}
