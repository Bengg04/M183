package ch.bbzbl;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.math.BigInteger;

@FacesValidator("myBigIntegerValidator")
public class MyBigIntegerValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        try {
            BigInteger myValue = new BigInteger(value.toString());

            if (myValue.toString().length() != 13) {
                throw new ValidatorException(new FacesMessage("Must be 13 digits"));
            }
        } catch (NumberFormatException e) {
            throw new ValidatorException(new FacesMessage("Not a valid BigInteger"));
        }
    }
}

