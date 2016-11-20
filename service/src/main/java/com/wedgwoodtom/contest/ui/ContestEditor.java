package com.wedgwoodtom.contest.ui;


import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import com.wedgwoodtom.contest.service.ContestManager;
import com.wedgwoodtom.test.data.Contest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@SpringComponent
@UIScope
public class ContestEditor extends FormLayout implements View
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
        setSizeFull();
//        setComponentAlignment(this, Alignment.MIDDLE_CENTER);

        setMargin(true);
//        contestManager = ContestUI.getContestUI().getContestManager();
        this.contestManager = contestManager;

//        title.addValidator(new StringLengthValidator("Fuck you", 5, 125, false));
//        title.setImmediate(true);
//        title.setValidationVisible(true);
        title.setRequired(true);
        title.setNullRepresentation("");
        title.setRequiredError("Title is required.");
//        title.setWidth(100, Unit.PERCENTAGE);
        title.setMaxLength(256);
        title.setSizeFull();

        startDate.setRequired(true);
        startDate.setRequiredError("Start date is required.");
        startDate.addValidator(new RelativeDateValidator("Start date must be before End date", endDate, false));

        endDate.setRequired(true);
        endDate.setRequiredError("End date is required.");
        endDate.addValidator(new RelativeDateValidator("End date must be after Start date", startDate, true));

        addComponent(title);
        addComponent(startDate);
        addComponent(endDate);

        // TODO: Validation can also be done with BeanFieldGroup/binder, so look into that as well

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
            if (validateForm())
            {
                contestManager.save(contest);
                ContestUI.getContestUI().showContests();
            }
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


//    public void buttonClick(ClickEvent event) {
//        try {
//            form.commit();
//        } catch (EmptyValueException e) {
//            // A required value was missing
//        }
//    }


    private boolean validateForm()
    {
        List<String> errors = new ArrayList<String>();
        for (Component component : this)
        {
            if (component instanceof AbstractField)
            {
                AbstractField field = (AbstractField)component;
                try
                {
                    field.validate();
                }
                catch(Validator.InvalidValueException error)
                {
                    errors.add(error.getMessage());
                }
            }
        }
        if (!errors.isEmpty())
        {
            Notification notification = new Notification("Validation Errors", String.join("\n", errors), Notification.Type.ERROR_MESSAGE);
            notification.setDelayMsec(5*1000);
            notification.show(Page.getCurrent());

//            Notification.show("Validation Errors", String.join("\n", errors), Notification.Type.ERROR_MESSAGE);
        }
        return errors.isEmpty();
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
