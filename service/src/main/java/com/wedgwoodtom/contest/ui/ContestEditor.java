package com.wedgwoodtom.contest.ui;


import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.wedgwoodtom.contest.service.ContestManager;
import com.wedgwoodtom.contest.ui.explore.ExampleUtil;
import com.wedgwoodtom.test.data.Contest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

@SpringComponent
@UIScope
public class ContestEditor extends VerticalLayout implements View
{
    public static final String NAME = "contestEditor";

    private final ContestManager contestManager;

    private Contest contest;

    /* Fields to edit properties in Customer entity */
    private TextField title = new TextField("Title");
    private DateField startDate = new DateField("Start Date");
    private DateField endDate = new DateField("End Date");

    /* Action buttons */
    private Button save = new Button("Save", FontAwesome.SAVE);
    private Button cancel = new Button("Cancel", FontAwesome.CLOSE);
    private Button delete = new Button("Delete", FontAwesome.TRASH_O);

    private CssLayout actions = new CssLayout(save, cancel, delete);

    @Autowired
    public ContestEditor(ContestManager contestManager)
    {
//        contestManager = ContestUI.getContestUI().getContestManager();
        this.contestManager = contestManager;

        title.addValidator(new StringLengthValidator("Fuck you", 5, 125, false));
//        title.setImmediate(true);
//        title.setValidationVisible(true);
        title.setRequired(true);
        title.setRequiredError("I am required");

        addComponent(title);
        addComponent(startDate);
        addComponent(endDate);

//        final BeanFieldGroup<Contest> binder =
//                new BeanFieldGroup<Contest>(Contest.class);
//        binder.setItemDataSource(contest);
//        addComponent(binder.buildAndBind("Title", "title"));


//        FieldGroup fieldGroup = new FieldGroup();
//        fieldGroup.commit();


        addComponent(actions);

        // Configure and style components
        setSpacing(true);
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        save.addClickListener(e -> {
            contestManager.save(contest);
            ContestUI.getContestUI().showContests();
        });
        delete.addClickListener(e -> {
            contestManager.delete(contest);
            ContestUI.getContestUI().showContests();
        });
        cancel.addClickListener(e -> {
            editContest(contest);
            ContestUI.getContestUI().showContests();
        });
    }

    public void editContest(Contest c)
    {
        final boolean persisted = c.getId() != null;
        if (persisted)
        {
            contest = contestManager.findContestById(c.getId().toString());
        }
        else
        {
            contest = c;
        }

        // Bind contest properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        BeanFieldGroup.bindFieldsUnbuffered(contest, this);

//        removeAllComponents();
//        final BeanFieldGroup<Contest> binder =
//                new BeanFieldGroup<Contest>(Contest.class);
//        binder.setBuffered(false);
//        binder.setItemDataSource(contest);
//
//        addComponent(binder.buildAndBind("Title", "title"));
//
//        addComponent(actions);

        // A hack to ensure the whole form is visible
        save.focus();
        // Select all text in firstName field automatically
        title.selectAll();

    }

    public void newContest()
    {
        editContest(new Contest("Untitled"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent)
    {

    }
}
