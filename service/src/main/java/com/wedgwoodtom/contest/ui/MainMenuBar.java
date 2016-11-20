package com.wedgwoodtom.contest.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import com.wedgwoodtom.contest.ui.bestpractices.DataPage;
import com.wedgwoodtom.contest.ui.bestpractices.InputPage;
import com.wedgwoodtom.contest.ui.bestpractices.WelcomePage;

public class MainMenuBar extends MenuBar
{
    public MainMenuBar(Navigator navigator)
    {
        super();

        addItem("Contests", FontAwesome.LIST, (item -> navigator.navigateTo(ContestView.NAME)));
        addItem("Vote", FontAwesome.THUMBS_UP, (item -> navigator.navigateTo(WelcomePage.NAME)));
        addItem("Rank", FontAwesome.LIST_OL, (item -> navigator.navigateTo(InputPage.NAME)));
        addItem("Results", FontAwesome.LEGAL, (item -> navigator.navigateTo(DataPage.NAME)));
    }

}
