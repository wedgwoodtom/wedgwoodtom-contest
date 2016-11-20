package com.wedgwoodtom.contest.ui.bestpractices;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class WelcomePage extends VerticalLayout implements View {

    private static final long serialVersionUID = 1L;

    public static final String NAME = "welcomepage";

    public WelcomePage() {
        setMargin(true);
        setSpacing(true);
        Label welcome = new Label("Welcome");
        welcome.addStyleName("h1");
        addComponent(welcome);
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

}

