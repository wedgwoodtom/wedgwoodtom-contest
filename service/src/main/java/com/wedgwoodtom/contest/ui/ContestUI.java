package com.wedgwoodtom.contest.ui;

import com.vaadin.annotations.Theme;
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
@Theme("valo")
public class ContestUI extends UI
{
    @Resource
    private ContestManager contestManager;

    // Wow, this works, but not sure that I want to rely on it since I can just use the built-in
    //  thread local functionality of Vaadin

    private ContestView contestView;
    @Resource
    private ContestEditor contestEditor;


    private Navigator navigator;

    public ContestUI()
    {
    }

    @Override
    protected void init(VaadinRequest request)
    {
        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        Panel contentPanel = new Panel("Main Panel");
        contentPanel.setSizeFull();

        contestView = new ContestView();

        navigator = new Navigator(this, contentPanel);
        // Adding an instance allows it to maintain state (which I prefer)
        navigator.addView(ContestView.NAME, contestView);
        navigator.addView(ContestEditor.NAME, contestEditor);
        navigator.addView(InputPage.NAME, InputPage.class);
        navigator.addView(DataPage.NAME, DataPage.class);

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
