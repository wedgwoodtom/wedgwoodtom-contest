package com.wedgwoodtom.contest.ui;

import com.kbdunn.vaadin.addons.mediaelement.MediaElementPlayer;
import com.kbdunn.vaadin.addons.mediaelement.MediaElementPlayerOptions;
import com.vaadin.event.ContextClickEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.FontIcon;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ClickableRenderer;
import com.vaadin.ui.renderers.NumberRenderer;
import com.vaadin.ui.renderers.ProgressBarRenderer;
import com.vaadin.ui.renderers.TextRenderer;
import com.wedgwoodtom.contest.service.ContestManager;
import com.wedgwoodtom.contest.ui.explore.ExampleUtil;

import java.util.Random;
import java.util.Set;

/**
 * Created by thomaspatterson on 11/20/16.
 */
public class VoteView2 extends VerticalLayout implements View
{
    public static final String NAME = "vote";

    private ContestManager contestManager;

    private MediaElementPlayer videoPlayer;

    public VoteView2()
    {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();

        Panel videoListPanel = new Panel();
        Grid videoGrid = new Grid();
        videoGrid.setSizeFull();


        videoGrid.addColumn("name", String.class).setRenderer(new TextRenderer()).setExpandRatio(0);
        videoGrid.addColumn("progress", Double.class).setRenderer(new ProgressBarRenderer()).setExpandRatio(2);

        videoGrid.addSelectionListener(listener -> {
            Set<Object> selected = listener.getSelected();
            // TODO: Set
            videoPlayer.setSource(new ExternalResource("https://youtu.be/pQuYSVYk5AU"));
        });

        for (int i = 0; i < 10; ++i)
        {
            videoGrid.addRow(
            /* name */"Bob Barker "+i,
            /* progress */Math.sin(i / 3.0) / 2.0 + 0.5);
        }

        videoListPanel.setContent(videoGrid);
        videoListPanel.setSizeFull();
        videoListPanel.setHeight(500, Unit.PIXELS);

        videoPlayer = new MediaElementPlayer(MediaElementPlayer.Type.VIDEO);
        videoPlayer.setWidth(600, Unit.PIXELS);
        videoPlayer.setSource(new ExternalResource("https://youtu.be/pQuYSVYk5AU"));

        VerticalLayout playerVerticalLayout = new VerticalLayout();
        playerVerticalLayout.setWidth(600, Unit.PIXELS);
        playerVerticalLayout.addComponent(videoPlayer);

        Slider slider = new Slider();
        slider.setCaption("My Rating");
        slider.setMin(0.0);
        slider.setMax(100.0);
        slider.setValue((double)50);
        slider.setWidth(100, Unit.PERCENTAGE);
        playerVerticalLayout.addComponent(slider);


        HorizontalLayout controlLayout = new HorizontalLayout();
        controlLayout.setSizeFull();

        TextArea comments = new TextArea();
        comments.setCaption("Comments");
        comments.setWidth(100, Unit.PERCENTAGE);
        comments.setRows(3);

        Button rateButton = new Button();
        rateButton.setCaption("Save");

        controlLayout.addComponent(comments);
        controlLayout.addComponent(rateButton);
        controlLayout.setComponentAlignment(rateButton, Alignment.MIDDLE_CENTER);
        playerVerticalLayout.addComponent(controlLayout);

        horizontalLayout.addComponent(playerVerticalLayout);
        horizontalLayout.addComponent(videoListPanel);
        // do not expand the player column, but do the list
        horizontalLayout.setExpandRatio(playerVerticalLayout, 0);
        horizontalLayout.setExpandRatio(videoListPanel, 1);

        addComponent(horizontalLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event)
    {
        // set to first
//        Notification.show("Enter on Video");
        // TODO: Set
        videoPlayer.setSource(new ExternalResource("https://youtu.be/pQuYSVYk5AU"));
    }
}