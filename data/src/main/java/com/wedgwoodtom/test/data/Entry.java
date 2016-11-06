package com.wedgwoodtom.test.data;


import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.net.URI;

@Document
public class Entry extends BaseDomainObject
{
    private String title;

    @DBRef
    private Player player;

    private URI videoUrl;

    public Entry(String title, URI videoUrl, Player player)
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

    public URI getVideoUrl()
    {
        return videoUrl;
    }

    public void setVideoUrl(URI videoUrl)
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
