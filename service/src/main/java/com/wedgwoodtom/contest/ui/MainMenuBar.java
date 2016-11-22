package com.wedgwoodtom.contest.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;

public class MainMenuBar extends MenuBar
{
    public MainMenuBar(Navigator navigator)
    {
        super();

        addItem("Contests", FontAwesome.LIST, (item -> navigator.navigateTo(ContestView.NAME)));
        addItem("Vote", FontAwesome.THUMBS_UP, (item -> navigator.navigateTo(VoteView.NAME)));
        addItem("Rank", FontAwesome.LIST_OL, (item -> navigator.navigateTo(VoteView2.NAME)));
        addItem("Results", FontAwesome.LEGAL, (item -> navigator.navigateTo(ContestResultsView.NAME)));
        addItem("Video", FontAwesome.LEGAL, (item -> navigator.navigateTo(VideoViewerView.NAME)));
    }

}
