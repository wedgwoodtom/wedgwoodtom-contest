package com.wedgwoodtom.contest.ui;

import com.kbdunn.vaadin.addons.mediaelement.MediaElementPlayer;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ProgressBarRenderer;
import com.vaadin.ui.renderers.TextRenderer;
import com.wedgwoodtom.contest.service.ContestManager;
import com.wedgwoodtom.test.data.Contest;
import com.wedgwoodtom.test.data.Rating;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by thomaspatterson on 11/20/16.
 */
public class VoteView2 extends VerticalLayout implements View
{
    public static final String NAME = "vote";

    private ContestManager contestManager;
    private MediaElementPlayer videoPlayer;
    private List<RatingWrapper> ratings;
    private Grid videoGrid;
    private Slider slider;
    private TextArea comments;

    public VoteView2()
    {
        contestManager = ContestUI.getContestUI().getContestManager();

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSizeFull();

        Panel videoListPanel = new Panel();
        videoGrid = new Grid();
        videoGrid.setSizeFull();

        videoGrid.addColumn("name", String.class).setRenderer(new TextRenderer()).setExpandRatio(0);
        videoGrid.addColumn("ratingValue", Double.class).setRenderer(new ProgressBarRenderer()).setExpandRatio(2);
        videoGrid.setContainerDataSource(findRatings());

        videoGrid.addSelectionListener(listener -> {
            RatingWrapper selected = getSelectedRating();
            if (selected!=null)
            {
                slider.setValue(selected.getRatingValue()*100);
                comments.setValue(selected.getRating().getComments());
                // TODO: Set  "https://youtu.be/pQuYSVYk5AU"
                videoPlayer.setSource(new ExternalResource(selected.getRating().getEntry().getVideoUrl()));
            }
//            videoPlayer.play();
        });

        videoListPanel.setContent(videoGrid);
        videoListPanel.setSizeFull();
        videoListPanel.setHeight(500, Unit.PIXELS);

        videoPlayer = new MediaElementPlayer(MediaElementPlayer.Type.VIDEO);
        videoPlayer.setWidth(600, Unit.PIXELS);

        VerticalLayout playerVerticalLayout = new VerticalLayout();
        playerVerticalLayout.setWidth(600, Unit.PIXELS);
        playerVerticalLayout.addComponent(videoPlayer);

        slider = new Slider();
        slider.setCaption("My Rating");
        slider.setMin(0.0);
        slider.setMax(100.0);
        slider.setValue((double)50);
        slider.setWidth(100, Unit.PERCENTAGE);
        playerVerticalLayout.addComponent(slider);

        HorizontalLayout controlLayout = new HorizontalLayout();
        controlLayout.setSizeFull();

        comments = new TextArea();
        comments.setCaption("Comments");
        comments.setWidth(100, Unit.PERCENTAGE);
        comments.setRows(3);

        Button rateButton = new Button();
        rateButton.setCaption("Save");
        rateButton.addClickListener(listener -> {
            // TODO: Hack - move all this to a class method
            RatingWrapper selected = getSelectedRating();
            selected.getRating().setScore(slider.getValue().intValue());
            selected.setRatingValue(slider.getValue()/100);
            selected.getRating().setComments(comments.getValue());

            contestManager.save(selected.getRating());

            // trick to re-draw the grid FFS
            videoGrid.setRowStyleGenerator(null);

        });

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
        if (ratings.isEmpty() != true)
        {
            videoGrid.select(ratings.get(0));
        }
    }

    private RatingWrapper getSelectedRating()
    {
        return (RatingWrapper)videoGrid.getSelectedRow();
    }

    private Container.Indexed findRatings()
    {
        // TODO: This will need to be setup somewhere, hard-code for now

        Contest contest = contestManager.findContestByTitle("Virtual Freestyle 1");
        if (contest == null)
        {
            throw new RuntimeException("Could not find contest");
        }

        ratings = contestManager.findRatings(contest).stream()
                .map(rating -> new RatingWrapper(rating))
                .collect(Collectors.toList());

        return new BeanItemContainer(RatingWrapper.class, ratings);
    }

    public class RatingWrapper
    {
        private String name;
        private double ratingValue;
        private Rating rating;

        public RatingWrapper(Rating rating)
        {
            this.rating = rating;
            this.name = rating.getPlayer().getFirstName()+" "+rating.getPlayer().getLastName();
            this.ratingValue = rating.getScore()==null ? 0 : rating.getScore().doubleValue()/100;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public double getRatingValue()
        {
            return ratingValue;
        }

        public void setRatingValue(double ratingValue)
        {
            this.ratingValue = ratingValue;
        }

        public Rating getRating()
        {
            return rating;
        }
    }

}