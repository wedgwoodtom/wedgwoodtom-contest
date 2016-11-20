package com.wedgwoodtom.contest.ui;

import com.vaadin.data.Validator;
import com.vaadin.ui.DateField;

import java.util.Date;

public class RelativeDateValidator implements Validator
{
    private String error;
    private DateField dateField;
    private boolean greaterThan;

    public RelativeDateValidator(String error, DateField dateField, boolean greaterThan)
    {
        this.dateField = dateField;
        this.greaterThan = greaterThan;
        this.error = error;
    }

    @Override
    public void validate(Object value) throws InvalidValueException
    {
        Date valueDate = (Date)value;
        Date uiDate = dateField.getValue();

        if (uiDate != null && valueDate != null)
        {
            if (greaterThan)
            {
                if (!valueDate.after(uiDate))
                {
                    throw new InvalidValueException(error);
                }
            }
            else
            {
                if (!valueDate.before(uiDate))
                {
                    throw new InvalidValueException(error);
                }
            }
        }

    }
}
