package com.wedgwoodtom.contest.ui.previous;

import com.vaadin.ui.*;
import com.wedgwoodtom.contest.service.ContestManager;
import com.wedgwoodtom.test.data.Contest;
import org.springframework.util.StringUtils;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;

import javax.annotation.Resource;

//@SpringUI
@Theme("valo")
public class ContestUIOld extends UI
{

    // TODO: Continue working on this example to add editing of dates, etc
    //  https://vaadin.com/docs/-/part/framework/tutorial.html


    @Resource
    private ContestManager contestManager;

    @Resource
    private ContestEditorOld editor;

    final Grid grid;

    final TextField filter;

    private final Button addNewBtn;


    public ContestUIOld()
    {
//        this.editor = editor;
        this.grid = new Grid();
        this.filter = new TextField();
        this.addNewBtn = new Button("New Contest", FontAwesome.PLUS);
    }

    @Override
    protected void init(VaadinRequest request)
    {

//        Navigator navigator = new Navigator(this, this);
        // TODO: create your own view provider
//        navigator.addProvider(viewProvider);
        // TODO: or add
//        navigator.addView(DashboardView.VIEW_NAME, DashboardView.class);
//        navigator.addView(OrderView.VIEW_NAME, OrderView.class);
//        navigator.addView(AboutView.VIEW_NAME, AboutView.class);
//        navigator.navigateTo("hello");
        // https://vaadin.com/wiki/-/wiki/main/View+navigation+with+Vaadin+Designer

        // login page
        // https://examples.javacodegeeks.com/enterprise-java/vaadin/vaadin-login-example/
        // https://github.com/degiere/vaadin-navigation-example/blob/master/src/main/java/net/degiere/ui/Menu.java
        // https://github.com/degiere/vaadin-navigation-example
        // https://books.google.com/books?id=0y_rmSUDf6cC&pg=PT223&lpg=PT223&dq=vaadin+Navigator+example&source=bl&ots=ISwqavKjRF&sig=wPSfRhyld2o-qZqtCSCW5LyQqHY&hl=en&sa=X&ved=0ahUKEwidqK3ljarQAhUFxmMKHUdnDE04ChDoAQhPMAk#v=onepage&q=vaadin%20Navigator%20example&f=false



        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        VerticalLayout mainLayout = new VerticalLayout(actions, grid, editor);
        setContent(mainLayout);

        // Configure layouts and components
        actions.setSpacing(true);
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);

        grid.setHeight(300, Unit.PIXELS);
//        grid.setColumns("id", "firstName", "lastName");
        grid.setColumns("title", "status");

        filter.setInputPrompt("Filter by last name");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.addTextChangeListener(e -> listContests(e.getText()));

        // Connect selected Customer to editor or hide if none is selected
        grid.addSelectionListener(e ->
        {
            if (e.getSelected().isEmpty())
            {
                Notification.show("Welcome to the Animal Farm", Notification.Type.ERROR_MESSAGE);

                editor.setVisible(false);
            } else
            {
                editor.editContest((Contest) grid.getSelectedRow());
            }
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.editContest(new Contest("")));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listContests(filter.getValue());
        });

        // Initialize listing
        listContests(null);
    }

    void listContests(String text)
    {
        if (StringUtils.isEmpty(text))
        {
            grid.setContainerDataSource(
                    new BeanItemContainer(Contest.class, contestManager.findAllContests()));
//            grid.setContainerDataSource(
//                    new BeanItemContainer(Customer.class, repo.findAll()));
        }
        else
        {
            // TODO: Add filter here
            grid.setContainerDataSource(
                    new BeanItemContainer(Contest.class, contestManager.findAllContests()));
//            grid.setContainerDataSource(new BeanItemContainer(Customer.class,
//                    repo.findByLastNameStartsWithIgnoreCase(text)));
        }
    }

    protected void setContestManager(ContestManager contestManager)
    {
        this.contestManager = contestManager;
    }
}
