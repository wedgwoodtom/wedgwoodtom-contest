package com.wedgwoodtom.contest.ui.bestpractices;


import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.wedgwoodtom.contest.ui.explore.LoginView;

//@SuppressWarnings("serial")
//@Theme("vaadinbestpractices")
//@SpringUI
@Theme("valo")
public class VaadinbestpracticesUI extends UI
{

    public Navigator navigator;

//    public static BeanContainer<String, DataBean> dataBean;

//    @WebServlet(value = "/*", asyncSupported = true)
//    @VaadinServletConfiguration(productionMode = false, ui = VaadinbestpracticesUI.class, widgetset = "com.example.vaadinbestpractices.widgetset.VaadinbestpracticesWidgetset")
//    public static class Servlet extends VaadinServlet {
//    }

//    @Override
//    protected void init(VaadinRequest request)
//    {
//        final VerticalLayout layout = new VerticalLayout();
//        layout.setMargin(true);
//        layout.setSpacing(true);
//        setContent(layout);
//        Navigator.ComponentContainerViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(layout);
//        navigator = new Navigator(UI.getCurrent(), viewDisplay);
//        navigator.addView("", new LoginView());
//        navigator.addView(MAINVIEW, new MainView());
//        navigator.addView(HELPVIEW, new HelpView());
//    }

    @Override
    protected void init(VaadinRequest request)
    {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);
        Panel contentPanel = new Panel("Main Panel");
//        contentPanel.setSizeUndefined();
        contentPanel.setSizeFull();
//        dataBean = new BeanContainer<String, DataBean>(DataBean.class);
//        dataBean.setBeanIdProperty("name");


        /*
         Navigator.ComponentContainerViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(layout);
        navigator = new Navigator(UI.getCurrent(), viewDisplay);
        navigator.addView("", new LoginView());
        navigator.addView(MAINVIEW, new MainView());
        navigator.addView(HELPVIEW, new HelpView());
         */

//        Navigator.ComponentContainerViewDisplay viewDisplay = new Navigator.ComponentContainerViewDisplay(layout);
//        navigator = new Navigator(UI.getCurrent(), viewDisplay);

        navigator = new Navigator(this, contentPanel);
        navigator.addView(InputPage.NAME, InputPage.class);
        navigator.addView(WelcomePage.NAME, WelcomePage.class);
        navigator.addView(DataPage.NAME, DataPage.class);

        MenuBar.Command welcome = new Command()
        {

            @Override
            public void menuSelected(MenuItem selectedItem)
            {
                navigator.navigateTo(WelcomePage.NAME);
            }
        };

        MenuBar.Command input = new Command()
        {

            @Override
            public void menuSelected(MenuItem selectedItem)
            {
                navigator.navigateTo(InputPage.NAME);
            }
        };

        MenuBar.Command data = new Command()
        {

            @Override
            public void menuSelected(MenuItem selectedItem)
            {
                navigator.navigateTo(DataPage.NAME);
            }
        };

        MenuBar mainMenu = new MenuBar();
        mainMenu.addItem("Welcome", FontAwesome.ARROW_CIRCLE_LEFT, welcome);
        mainMenu.addItem("Input", FontAwesome.WEIXIN, input);
        mainMenu.addItem("Data", FontAwesome.LIST, data);

        layout.addComponent(mainMenu);
        layout.addComponent(contentPanel);
        navigator.navigateTo(WelcomePage.NAME);
    }

}


/*

@SpringUI
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

 */