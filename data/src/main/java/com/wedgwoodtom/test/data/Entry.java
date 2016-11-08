package com.wedgwoodtom.test.data;


import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Entry extends BaseObject
{
    private String title;

    @DBRef
    private Player player;

    private String videoUrl;

    public Entry(Player player, String title, String videoUrl)
    {
        this.title = title;
        this.videoUrl = videoUrl;
        this.player = player;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public String getVideoUrl()
    {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl)
    {
        this.videoUrl = videoUrl;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
