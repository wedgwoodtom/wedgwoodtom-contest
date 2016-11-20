package com.wedgwoodtom.contest.ui.explore;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.wedgwoodtom.contest.service.ContestManager;
import com.wedgwoodtom.test.data.Contest;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

//@SpringUI
@Theme("valo")
public class ExploreVUI extends UI
{

    public Navigator navigator;

    public static final String MAINVIEW = "main";
    public static final String HELPVIEW = "help";

    @Override
    protected void init(VaadinRequest request)
    {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);
        Navigator.ComponentContainerViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(layout);
        navigator = new Navigator(UI.getCurrent(), viewDisplay);
        navigator.addView("", new LoginView());
        navigator.addView(MAINVIEW, new MainView());
        navigator.addView(HELPVIEW, new HelpView());
    }


}
