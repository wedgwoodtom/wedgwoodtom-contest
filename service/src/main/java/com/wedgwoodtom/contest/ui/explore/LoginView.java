package com.wedgwoodtom.contest.ui.explore;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;


public class LoginView extends VerticalLayout implements View
{

    public LoginView()
    {
        setSizeFull();
        setSpacing(true);

        Label label = new Label("Enter your information below to log in.");
        TextField username = new TextField("Username");
        TextField password = new TextField("Password");

        addComponent(label);
        addComponent(username);
        addComponent(password);
        addComponent(loginButton());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event)
    {
        Notification.show("Welcome! Please log in.");
    }

    private Button loginButton()
    {
        Button button = new Button("Log In", new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent event)
            {
                getUI().getNavigator().navigateTo(ExploreVUI.MAINVIEW);
            }
        });
        return button;
    }

}