package com.wedgwoodtom.contest.ui;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.wedgwoodtom.contest.service.ContestManager;
import com.wedgwoodtom.test.data.Contest;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by thomaspatterson on 11/13/16.
 */
@SpringComponent
@UIScope
public class ContestEditor  extends VerticalLayout
{
    private final ContestManager contestManager;

    /**
     * The currently edited customer
     */
    private Contest contest;

    /* Fields to edit properties in Customer entity */
    TextField title = new TextField("Title");

    /* Action buttons */
    Button save = new Button("Save", FontAwesome.SAVE);
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", FontAwesome.TRASH_O);
    CssLayout actions = new CssLayout(save, cancel, delete);

    @Autowired
    public ContestEditor(ContestManager contestManager)
    {
        this.contestManager = contestManager;

        addComponents(title, actions);

        // Configure and style components
        setSpacing(true);
        actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

        save.setStyleName(ValoTheme.BUTTON_PRIMARY);
        save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> contestManager.save(contest));
        delete.addClickListener(e -> contestManager.delete(contest));

        cancel.addClickListener(e -> editContest(contest));
        setVisible(false);
    }

    public interface ChangeHandler
    {
        void onChange();
    }

    public final void editContest(Contest c)
    {
        final boolean persisted = c.getId() != null;
        if (persisted)
        {
            // Find fresh entity for editing
            contest = contestManager.findContestById(c.getId().toString());
        }
        else
        {
            contest = c;
        }
        cancel.setVisible(persisted);

        // Bind contest properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        BeanFieldGroup.bindFieldsUnbuffered(contest, this);

        setVisible(true);

        // A hack to ensure the whole form is visible
        save.focus();
        // Select all text in firstName field automatically
        title.selectAll();
    }

    public void setChangeHandler(ContestEditor.ChangeHandler h)
    {
        // ChangeHandler is notified when either save or delete
        // is clicked
        save.addClickListener(e -> h.onChange());
        delete.addClickListener(e -> h.onChange());
    }

}
