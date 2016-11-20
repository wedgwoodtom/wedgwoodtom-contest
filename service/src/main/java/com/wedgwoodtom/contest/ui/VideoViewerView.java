package com.wedgwoodtom.contest.ui;

import com.kbdunn.vaadin.addons.mediaelement.MediaElementPlayer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Video;
import com.wedgwoodtom.contest.service.ContestManager;

/**
 * Created by thomaspatterson on 11/20/16.
 */
public class VideoViewerView extends VerticalLayout implements View
{
    public static final String NAME = "videoViewer";

    private ContestManager contestManager;

    public VideoViewerView()
    {
        contestManager = ContestUI.getContestUI().getContestManager();


        Video sample = new Video();
//        final Resource mp4Resource = new ExternalResource(
//                "https://s3.amazonaws.com/videos.vaadin.com/vaadin_fin_web002.mp4");
//        final Resource ogvResource = new ExternalResource(
//                "https://s3.amazonaws.com/videos.vaadin.com/vaadin_fin_web002.ogv");
//        sample.setSources(mp4Resource, ogvResource);

//        Resource youTube = new ExternalResource("http://youtu.be/pQuYSVYk5AU");
//        sample.setSources(youTube);
//
//        sample.setSizeFull();
//        sample.setHtmlContentAllowed(true);
//        sample.setAltText("Can't play media");
//
//        addComponent(sample);


//        Embedded e = new Embedded(null, new ExternalResource(
//                "https://youtu.be/pQuYSVYk5AU"));
//        e.setAlternateText("Vaadin Eclipse Quickstart video");
//        e.setMimeType("application/x-shockwave-flash");
//        e.setParameter("allowFullScreen", "true");
//        e.setWidth("320px");
//        e.setHeight("265px");
//        addComponent(e);

//        Embedded embed = new Embedded("my video", new ExternalResource("https://youtu.be/pQuYSVYk5AU"));
//        embed.setMimeType("application/x-shockwave-flash");
//        addComponent(embed);


        // YouTube player
        MediaElementPlayer videoPlayer = new MediaElementPlayer(MediaElementPlayer.Type.VIDEO);
        addComponent(videoPlayer);
        videoPlayer.setSizeFull();
        videoPlayer.setSource(new ExternalResource("https://youtu.be/pQuYSVYk5AU"));
//        videoPlayer.setSource(new ExternalResource("https://youtu.be/kh29_SERH0Y"));

    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event)
    {

    }
}