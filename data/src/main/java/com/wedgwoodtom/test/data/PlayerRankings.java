package com.wedgwoodtom.test.data;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class PlayerRankings extends BaseObject
{
    @DBRef
    private Player player;

    @DBRef(lazy = true)
    private Contest contest;

    @DBRef
    private List<Entry> rankedEntryList = new ArrayList<>();


    public PlayerRankings(Contest contest, Player player, List<Entry> rankedEntryList)
    {
        this.contest = contest;
        this.player = player;
        this.rankedEntryList = rankedEntryList;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public Contest getContest()
    {
        return contest;
    }

    public void setContest(Contest contest)
    {
        this.contest = contest;
    }

    public List<Entry> getRankedEntryList()
    {
        return rankedEntryList;
    }

    public void setRankedEntryList(List<Entry> rankedEntryList)
    {
        this.rankedEntryList = rankedEntryList;
    }
}
