package com.wedgwoodtom.contest.ui;

import com.kbdunn.vaadin.addons.mediaelement.MediaElementPlayer;
import com.kbdunn.vaadin.addons.mediaelement.MediaElementPlayerOptions;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.shared.ui.slider.SliderOrientation;
import com.vaadin.ui.*;
import com.wedgwoodtom.contest.service.ContestManager;
import com.wedgwoodtom.test.data.Contest;
import com.wedgwoodtom.test.data.PlayerRankings;
import org.springframework.util.StringUtils;

/**
 * Created by thomaspatterson on 11/20/16.
 */
public class VoteView extends VerticalLayout implements View
{
    public static final String NAME = "vote";

    private ContestManager contestManager;

    private Grid grid;

    public VoteView()
    {
//        contestManager = ContestUI.getContestUI().getContestManager();


// Create a table and add a style to
// allow setting the row height in theme.
        Table table = new Table();
        table.addStyleName("components-inside");

/* Define the names and data types of columns.
 * The "default value" parameter is meaningless here. */
        table.addContainerProperty("Player", Label.class, null);
        table.addContainerProperty("Entry", MediaElementPlayer.class,  null);
        table.addContainerProperty("Rating",  VerticalLayout.class, null);
//        table.addContainerProperty("Rating",  Slider.class, null);
//        table.addContainerProperty("Comments", TextField.class,    null);

        addTableItem(table, "Tom", "https://youtu.be/pQuYSVYk5AU", 25, "No comment");
        addTableItem(table, "Bob", "https://youtu.be/pQuYSVYk5AU", 50, "Awesome");

//        for (int i=0; i<25; i++)
//        {
//            addTableItem(table, "Harry"+i, "https://youtu.be/pQuYSVYk5AU", 75, "Cool");
//        }

/* Add a few items in the table. */
//        for (int i=0; i<100; i++) {
//            // Create the fields for the current table row
//            Label sumField = new Label("Tom");
//            CheckBox transferredField = new CheckBox("is transferred");
//
//            // Multiline text field. This required modifying the
//            // height of the table row.
//            TextField commentsField = new TextField();
////            commentsField.setMaxLength(3);
//
//            // The Table item identifier for the row.
//            Integer itemId = new Integer(i);
//
//            // Create a button and handle its click. A Button does not
//            // know the item it is contained in, so we have to store the
//            // item ID as user-defined data.
//            Button detailsField = new Button("show details");
//            detailsField.setData(itemId);
//            detailsField.addClickListener(new Button.ClickListener() {
//                public void buttonClick(Button.ClickEvent event) {
//                    // Get the item identifier from the user-defined data.
//                    Integer iid = (Integer)event.getButton().getData();
//                    Notification.show("Link " +
//                            iid.intValue() + " clicked.");
//                }
//            });
//            detailsField.addStyleName("link");
//
//            // Create the table row.
//            table.addItem(new Object[] {sumField, transferredField,
//                            commentsField, detailsField},
//                    itemId);
//        }

// Show just three rows because they are so high.
//        table.setPageLength(5);

        table.setSizeFull();

        addComponent(table);
    }

    void addTableItem(Table table, String player, String entryUrl, int rating, String comments)
    {
        TextArea commentsField = new TextArea();
        commentsField.setValue(comments);
        commentsField.setRows(3);

        MediaElementPlayer videoPlayer = new MediaElementPlayer(MediaElementPlayer.Type.VIDEO);
//        addComponent(videoPlayer);
//        videoPlayer.setSizeFull();
//        videoPlayer.setHeight(200, Unit.PIXELS);
//        videoPlayer.setSizeFull();
//        videoPlayer.setSizeUndefined();
//        videoPlayer.setHeight(216, Unit.PIXELS);
        videoPlayer.setWidth(384, Unit.PIXELS);
//        videoPlayer.setWidth(100, Unit.PERCENTAGE);
        videoPlayer.setSource(new ExternalResource(entryUrl));

        Slider slider = new Slider();
//        sample.setImmediate(true);
        slider.setMin(0.0);
        slider.setMax(100.0);
        slider.setValue((double)rating);
        slider.setWidth(100, Unit.PERCENTAGE);
//        slider.setSizeUndefined();
        slider.setData(player);
//        slider.setOrientation(SliderOrientation.VERTICAL);
        slider.addValueChangeListener(event -> {
            Field.ValueChangeEvent changeEvent = (Field.ValueChangeEvent)event;
            Slider s = (Slider)changeEvent.getSource();
            Notification.show("Update "+s.getData());
        });

        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(slider);
        layout.addComponent(commentsField);


        String someId = player;
        table.addItem(new Object[] {new Label(player), videoPlayer, layout}, someId);
//        table.addItem(new Object[] {new Label(player), videoPlayer, slider, commentsField}, someId);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event)
    {
    }
}