package com.wedgwoodtom.test.data;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.*;

@Document
public class Contest extends BaseObject
{
    @Indexed
    private String title;

    private ContestStatus status = ContestStatus.Planned;
    private Boolean active;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private Date endDate;

    @DBRef
    private List<Player> playerList = new ArrayList<>();
    @DBRef
    private List<Player> disqualifiedPlayers = new ArrayList<>();

    @DBRef
    private List<Entry> entryList = new ArrayList<>();

    @DBRef
    private List<PlayerRankings> playerRankingsList = new ArrayList<>();


    // TODO; Not sure why this is needed - works with or without it
//    @PersistenceConstructor
    public Contest(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public List<Player> getPlayerList()
    {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList)
    {
        this.playerList = playerList;
    }

    public void addPlayer(Player player)
    {
        if (!playerList.contains(player))
        {
            playerList.add(player);
        }
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public List<Entry> getEntryList()
    {
        return entryList;
    }

    public void setEntryList(List<Entry> entryList)
    {
        this.entryList = entryList;
    }

    public Boolean getActive()
    {
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }

    public List<PlayerRankings> getPlayerRankingsList()
    {
        return playerRankingsList;
    }

    public void setPlayerRankingsList(List<PlayerRankings> playerRankingsList)
    {
        this.playerRankingsList = playerRankingsList;
    }

    public List<Player> getDisqualifiedPlayers()
    {
        return disqualifiedPlayers;
    }

    public void setDisqualifiedPlayers(List<Player> disqualifiedPlayers)
    {
        this.disqualifiedPlayers = disqualifiedPlayers;
    }

    public ContestStatus getStatus()
    {
        return status;
    }

    public void setStatus(ContestStatus status)
    {
        this.status = status;
    }
}
