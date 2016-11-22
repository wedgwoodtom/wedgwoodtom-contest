package com.wedgwoodtom.contest.ui.bestpractices;


import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class DataPage extends VerticalLayout implements View {

    private static final long serialVersionUID = 1L;

    public static final String NAME = "datapage";

    public DataPage() {
//        Table dataTable = new Table("Data Table", VaadinbestpracticesUI.dataBean);
        Table dataTable = new Table("Data Table");
        dataTable.setVisibleColumns(new Object[]{"name", "surname", "age"});
        dataTable.setHeight("200px");
        addComponent(dataTable);
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

}
