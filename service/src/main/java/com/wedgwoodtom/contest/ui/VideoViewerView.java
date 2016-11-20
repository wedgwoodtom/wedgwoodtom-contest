package com.wedgwoodtom.contest.ui;

import com.kbdunn.vaadin.addons.mediaelement.MediaElementPlayer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Resource;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Slider;
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

        // YouTube player
        MediaElementPlayer videoPlayer = new MediaElementPlayer(MediaElementPlayer.Type.VIDEO);
        addComponent(videoPlayer);
        videoPlayer.setSizeFull();
        videoPlayer.setSource(new ExternalResource("https://youtu.be/pQuYSVYk5AU"));
//        videoPlayer.setSource(new ExternalResource("https://youtu.be/kh29_SERH0Y"));


//        Slider sample = new Slider();
//        sample.setImmediate(true);
//        sample.setMin(0.0);
//        sample.setMax(100.0);
//        sample.setValue(50.0);
//        sample.setSizeFull();
//        sample.setCaption("Score");
//        addComponent(sample);

    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event)
    {

    }
}