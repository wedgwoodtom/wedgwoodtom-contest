package com.wedgwoodtom.contest.ui;

import com.wedgwoodtom.contest.service.ContestManager;
import com.wedgwoodtom.test.data.Contest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.Resource;
import java.util.List;

@SpringUI
@Theme("valo")
public class ContestUI extends UI
{
    @Autowired
    private ContestManager contestManager;

    private final CustomerRepository repo;
    private final CustomerEditor editor;

    final Grid grid;

    final TextField filter;

    private final Button addNewBtn;

    @Autowired
    public ContestUI(CustomerRepository repo, CustomerEditor editor)
    {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid();
        this.filter = new TextField();
        this.addNewBtn = new Button("New customer", FontAwesome.PLUS);
    }

    @Override
    protected void init(VaadinRequest request)
    {
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
        filter.addTextChangeListener(e -> listCustomers(e.getText()));

        // Connect selected Customer to editor or hide if none is selected
        grid.addSelectionListener(e ->
        {
            if (e.getSelected().isEmpty())
            {
                editor.setVisible(false);
            } else
            {
                editor.editCustomer((Customer) grid.getSelectedRow());
            }
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.editCustomer(new Customer("", "")));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() ->
        {
            editor.setVisible(false);
            listCustomers(filter.getValue());
        });

        // Initialize listing
        listCustomers(null);
    }

    void listCustomers(String text)
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
