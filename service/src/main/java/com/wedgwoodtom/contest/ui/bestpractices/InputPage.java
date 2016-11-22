package com.wedgwoodtom.contest.ui.bestpractices;


import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class InputPage extends VerticalLayout implements View {

    public static final String NAME = "inputpage";

    public InputPage() {
        Panel inputPanel = new Panel("Input data");
//        inputPanel.setSizeUndefined();
        addComponent(inputPanel);

        PropertysetItem fProperties = new PropertysetItem();
        fProperties.addItemProperty("nameValidator", new ObjectProperty<String>(""));
        fProperties.addItemProperty("surnameValidator", new ObjectProperty<String>(""));
        fProperties.addItemProperty("integerValidator", new ObjectProperty<Integer>(0));

        FormLayout inputForm = new FormLayout();

        TextField name = new TextField("Name");
        name.setNullSettingAllowed(true);
        name.setNullRepresentation("");
        name.addValidator(new StringLengthValidator("Name must have 3-15 characters lenght", 3, 15, true));
        name.setValidationVisible(true);
        inputForm.addComponent(name);

        TextField surname = new TextField("Surname");
        surname.setNullSettingAllowed(true);
        surname.setNullRepresentation("");
        surname.addValidator(new StringLengthValidator("Surname must have 3-15 characters lenght", 3, 15, true));
        surname.setValidationVisible(true);
        inputForm.addComponent(surname);

        TextField age = new TextField("Age");
        age.setNullRepresentation("0");
        age.addValidator(new IntegerRangeValidator("Age must be between 1 and 110", 1, 110));
        inputForm.addComponent(age);

        HorizontalLayout btLayout = new HorizontalLayout();
        Button btSave = new Button("Save");
        btLayout.addComponent(btSave);
        Button btClear = new Button("Clear");
        btLayout.addComponent(btClear);
        inputForm.addComponent(btLayout);

        btSave.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                if(!name.isEmpty() && !surname.isEmpty() && !age.isEmpty()){
                    Boolean save = true;
                    try{
                        name.validate();

                    }catch(Validator.InvalidValueException e){
                        save = false;
                    }

                    try{
                        surname.validate();

                    }catch(Validator.InvalidValueException e){
                        save = false;
                    }

                    try{
                        age.validate();

                    }catch(Validator.InvalidValueException e){
                        save = false;
                    }

                    if(save){
                        Notification.show("Data saved!");
                        name.setValue("");
                        surname.setValue("");
                        age.setValue("0");
                        btSave.setComponentError(null);
                    }
                }else{
                    Notification.show("All fields must be filled");
                }
            }
        });

        btClear.addClickListener(new ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                name.clear();
                surname.clear();
                age.clear();
            }
        });

        FieldGroup fGroup = new FieldGroup(fProperties);
        fGroup.bind(name, "nameValidator");
        fGroup.bind(surname, "surnameValidator");
        fGroup.bind(age, "integerValidator");

        inputForm.setMargin(true);
        inputForm.setSpacing(true);
        inputPanel.setContent(inputForm);
    }

    @Override
    public void enter(ViewChangeEvent event) {

    }

}
