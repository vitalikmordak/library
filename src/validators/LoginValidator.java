package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.ResourceBundle;

@FacesValidator("LoginValidator")
public class LoginValidator implements Validator {
    @Override
    public void validate(FacesContext facesContext, UIComponent uiComponent, Object o) throws ValidatorException {
        ResourceBundle bundle = ResourceBundle.getBundle("nls.messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String value = o.toString();
        try {
            switch (uiComponent.getId()) {
                case "username":
                    if (!Character.isLetter(value.charAt(0))) {
                        throw new IllegalArgumentException(bundle.getString("error_first_letter"));
                    }
                    if (value.length() < 5) {
                        throw new IllegalArgumentException(bundle.getString("error_login_length"));
                    }
                    if (value.equals("username") || o.toString().equals("login")) {
                        throw new IllegalArgumentException(bundle.getString("error_login_used").concat(o.toString() + "\""));
                    }

                    break;
                case "password":
                    if (value.length() < 5) {
                        throw new IllegalArgumentException(bundle.getString("error_password_length"));
                    }
                    break;
            }
        } catch (IllegalArgumentException e) {
            FacesMessage message = new FacesMessage(e.getMessage());
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }


    }
}
