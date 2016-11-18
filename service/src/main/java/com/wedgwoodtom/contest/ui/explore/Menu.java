package com.wedgwoodtom.contest.ui.explore;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;


public class Menu extends CustomComponent
{
    public Menu()
    {
        HorizontalLayout layout = new HorizontalLayout();
        layout.addComponent(mainButton());
        layout.addComponent(helpButton());
        layout.addComponent(logoutButton());
        layout.setSizeUndefined();
        layout.setSpacing(true);
        setSizeUndefined();
        setCompositionRoot(layout);
    }

    private Button mainButton()
    {
        Button button = new Button("Main", new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent event)
            {
                getUI().getNavigator().navigateTo(ExploreVUI.MAINVIEW);
            }
        });
        return button;
    }

    private Button helpButton()
    {
        Button button = new Button("Help", new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent event)
            {
                getUI().getNavigator().navigateTo(ExploreVUI.HELPVIEW);
            }
        });
        return button;
    }

    private Button logoutButton()
    {
        Button button = new Button("Logout", new Button.ClickListener()
        {
            @Override
            public void buttonClick(Button.ClickEvent event)
            {
                getUI().getSession().close();
                getUI().getPage().setLocation(getLogoutPath());
            }
        });
        return button;
    }

    private String getLogoutPath()
    {
        return getUI().getPage().getLocation().getPath();
    }

}