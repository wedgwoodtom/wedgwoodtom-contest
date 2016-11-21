package com.wedgwoodtom.contest.ui;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.wedgwoodtom.contest.service.ContestManager;
import com.wedgwoodtom.test.data.Contest;
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
        table.addContainerProperty("Sum",            Label.class,     null);
        table.addContainerProperty("Is Transferred", CheckBox.class,  null);
        table.addContainerProperty("Comments",       TextField.class, null);
        table.addContainerProperty("Details",        Button.class,    null);

/* Add a few items in the table. */
        for (int i=0; i<100; i++) {
            // Create the fields for the current table row
            Label sumField = new Label(String.format(
                    "Sum is <b>$%04.2f</b><br/><i>(VAT incl.)</i>",
                    new Object[] {new Double(Math.random()*1000)}),
                    ContentMode.HTML);
            CheckBox transferredField = new CheckBox("is transferred");

            // Multiline text field. This required modifying the
            // height of the table row.
            TextField commentsField = new TextField();
//            commentsField.setMaxLength(3);

            // The Table item identifier for the row.
            Integer itemId = new Integer(i);

            // Create a button and handle its click. A Button does not
            // know the item it is contained in, so we have to store the
            // item ID as user-defined data.
            Button detailsField = new Button("show details");
            detailsField.setData(itemId);
            detailsField.addClickListener(new Button.ClickListener() {
                public void buttonClick(Button.ClickEvent event) {
                    // Get the item identifier from the user-defined data.
                    Integer iid = (Integer)event.getButton().getData();
                    Notification.show("Link " +
                            iid.intValue() + " clicked.");
                }
            });
            detailsField.addStyleName("link");

            // Create the table row.
            table.addItem(new Object[] {sumField, transferredField,
                            commentsField, detailsField},
                    itemId);
        }

// Show just three rows because they are so high.
        table.setPageLength(7);

        table.setSizeFull();

        addComponent(table);



//
//        grid = new Grid();
//
//        setSizeFull();
//        setSpacing(true);
//
//        addComponent(grid);
//
//        grid.setWidth(100, Unit.PERCENTAGE);
//        grid.setColumns("title", "status");

        // Initialize listing
//        listContests(null);
    }

    void listContests(String text)
    {
        if (StringUtils.isEmpty(text))
        {
            grid.setContainerDataSource(
                    new BeanItemContainer(Contest.class, contestManager.findAllContests()));
        } else
        {
            // TODO: Add filter here
            grid.setContainerDataSource(
                    new BeanItemContainer(Contest.class, contestManager.findAllContests()));
        }
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event)
    {
    }
}