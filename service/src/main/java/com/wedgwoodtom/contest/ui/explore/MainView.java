package com.wedgwoodtom.contest.ui.explore;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import java.text.ParseException;

/**
 * Created by thomaspatterson on 11/17/16.
 */
public class MainView extends VerticalLayout implements View
{

    public MainView()
    {
        setSizeFull();
        setSpacing(true);
        addComponent(new Menu());
        addComponent(headingLabel());
        addComponent(new TableExample());

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event)
    {
        Notification.show("Showing view: Main!");
    }

    private Label headingLabel()
    {
        return new Label("Main");
    }


}