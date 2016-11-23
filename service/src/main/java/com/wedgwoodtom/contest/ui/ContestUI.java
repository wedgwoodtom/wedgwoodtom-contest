package com.wedgwoodtom.contest.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.wedgwoodtom.contest.service.ContestManager;
import com.wedgwoodtom.contest.ui.bestpractices.DataPage;
import com.wedgwoodtom.contest.ui.bestpractices.InputPage;
import com.wedgwoodtom.test.data.Contest;

import javax.annotation.Resource;

@SpringUI
//@Theme("reindeer")
//@Theme("runo")
//@Theme("liferay")
//@Theme("chameleon")
//@Theme("base")
@Theme("valo")
//@Widgetset("AppWidgetset")
//@Widgetset("org.vaadin.teemu.ratingstars.gwt.RatingStarsWidgetset")
//@Widgetset("org/vaadin/teemu/ratingstars/gwt/RatingStarsWidgetset.gwt.xmlorg.vaadin.teemu.ratingstars.RatingStarsWidgetset")
public class ContestUI extends UI
{
    @Resource
    private ContestManager contestManager;

    // Wow, this works, but not sure that I want to rely on it since I can just use the built-in
    //  thread local functionality of Vaadin

    private ContestView contestView;
    private VoteView2 voteView;

    @Resource
    private ContestEditor contestEditor;


    private Navigator navigator;

    public ContestUI()
    {
    }

    @Override
    protected void init(VaadinRequest request)
    {
//        setTheme("");

        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        Panel contentPanel = new Panel("Main Panel");
        contentPanel.setSizeFull();

        contestView = new ContestView();
        voteView = new VoteView2();

        navigator = new Navigator(this, contentPanel);
        // Adding an instance allows it to maintain state (which I prefer)
        navigator.addView(ContestView.NAME, contestView);
        navigator.addView(ContestEditor.NAME, contestEditor);
        // TODO: retire this one
        navigator.addView(VoteView.NAME, VoteView.class);
        navigator.addView(VoteView2.NAME, voteView);
        navigator.addView(ContestResultsView.NAME, ContestResultsView.class);
        navigator.addView(VideoViewerView.NAME, VideoViewerView.class);

        layout.addComponent(new MainMenuBar(navigator));
        layout.addComponent(contentPanel);
        navigator.navigateTo(ContestView.NAME);
    }

    public ContestManager getContestManager()
    {
        return contestManager;
    }

    public static ContestUI getContestUI()
    {
        return (ContestUI) UI.getCurrent();
    }

    public void editContest(Contest contest)
    {
        if (contest == null)
        {
            return;
        }
        contestEditor.editContest(contest);
        navigator.navigateTo(ContestEditor.NAME);
    }

    public void newContest()
    {
        contestEditor.newContest();
        navigator.navigateTo(ContestEditor.NAME);
    }

    public void showContests()
    {
        contestView.listContests(null);
        navigator.navigateTo(ContestView.NAME);
    }

    /*
    try {
    VaadinSession.getCurrent().getLockInstance().lock();
    VaadinSession.getCurrent().setAttribute(SESSION_SCOPED_VALUE_ID, "some value");
} finally {
    VaadinSession.getCurrent().getLockInstance().unlock();
}
     */

}
