package com.wedgwoodtom.contest.ui.explore;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.AbstractErrorMessage;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class HelpView extends VerticalLayout implements View
{

    public HelpView() {
        setSizeFull();
        setSpacing(true);
        addComponent(new Menu());
        addComponent(headingLabel());
        addComponent(someText());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Notification.show("Showing view: Help!");
    }

    private Label headingLabel() {
        return new Label("Help");
    }

    private Label someText() {
        Label label = new Label(ExampleUtil.lorem);
//        label.setContentMode(AbstractErrorMessage.ContentMode.HTML);
        return label;
    }

}