package com.wedgwoodtom.contest.ui;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import com.wedgwoodtom.contest.service.ContestManager;
import com.wedgwoodtom.test.data.Contest;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@SpringView
public class ContestView extends VerticalLayout implements View
{
    public static final String NAME = "contest";

    private ContestManager contestManager;

//    @Resource
//    private ContestEditor editor;

    private Grid grid;
    private TextField filter;
    private  Button addNewBtn;

    public ContestView()
    {
        contestManager = ContestUI.getContestUI().getContestManager();
        grid = new Grid();
        filter = new TextField();
        addNewBtn = new Button("New", FontAwesome.PLUS);
        Button editButton = new Button("Edit", FontAwesome.EDIT);

        setSizeFull();
        setSpacing(true);

        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn, editButton);
        VerticalLayout mainLayout = new VerticalLayout(actions, grid);
        addComponent(mainLayout);

        grid.setWidth(100, Unit.PERCENTAGE);
        grid.setColumns("title", "status");

        filter.setInputPrompt("Filter by tags");

        // Replace listing with filtered content when user changes filter
        filter.addTextChangeListener(e -> listContests(e.getText()));


       addNewBtn.addClickListener(e -> ContestUI.getContestUI().newContest());

//        Contest contest = (Contest)grid.getSelectedRow();
        editButton.addClickListener(e -> ContestUI.getContestUI().editContest((Contest)grid.getSelectedRow()));

        grid.addSelectionListener(e ->
        {
            if (!e.getSelected().isEmpty())
            {
                ContestUI.getContestUI().editContest((Contest)grid.getSelectedRow());
            }
        });

//        grid.addItemClickListener( click -> {
//            if (click.isDoubleClick()) {
//                Notification.show("Double-click", Notification.Type.ERROR_MESSAGE);
//            }
//        });

        // Initialize listing
        listContests(null);
    }

    void listContests(String text)
    {
        if (StringUtils.isEmpty(text))
        {
            grid.setContainerDataSource(
                    new BeanItemContainer(Contest.class, contestManager.findAllContests()));
        }
        else
        {
            // TODO: Add filter here
            grid.setContainerDataSource(
                    new BeanItemContainer(Contest.class, contestManager.findAllContests()));
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event)
    {
//        Notification.show("Showing view: Main!");
        UI myUI = getUI();
        myUI.getNavigator();

    }



}
