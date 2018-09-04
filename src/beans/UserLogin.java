package beans;

import dao.UserDAO;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Named
@SessionScoped
public class UserLogin implements Serializable {
    private String username;
    private String password;

    private boolean access;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index?faces-redirect=true";
    }

    //validate login

    public String validateLogin() {
        boolean valid = UserDAO.login(username, password);
        if (valid) {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            session.setAttribute("username", username);
            access = UserDAO.editModeAccess(username); // check if user has role = admin
            return "pages/books?faces-redirect=true";
        } else {
            return "index";
        }
    }

    public boolean isAccess() {
        return access;
    }
}