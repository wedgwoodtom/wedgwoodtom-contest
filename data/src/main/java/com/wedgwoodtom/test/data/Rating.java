package com.wedgwoodtom.test.data;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Rating extends BaseObject
{
    @DBRef
    private Contest contest;

    @DBRef
    private Player player;

    @DBRef
    private Entry entry;

    private Integer score;

    private String comments = "";

    private String sourceId;

    @PersistenceConstructor
    public Rating(Contest contest, Player player, Entry entry)
    {
        this.contest = contest;
        this.player = player;
        this.entry = entry;
        if (player != null)
        {
            sourceId = player.idAsString();
        }
    }

    public Contest getContest()
    {
        return contest;
    }

    public void setContest(Contest contest)
    {
        this.contest = contest;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public Entry getEntry()
    {
        return entry;
    }

    public void setEntry(Entry entry)
    {
        this.entry = entry;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public Integer getScore()
    {
        return score;
    }

    public void setScore(Integer score)
    {
        this.score = score;
    }

    public String getSourceId()
    {
        return sourceId;
    }

    public void setSourceId(String sourceId)
    {
        this.sourceId = sourceId;
    }
}
