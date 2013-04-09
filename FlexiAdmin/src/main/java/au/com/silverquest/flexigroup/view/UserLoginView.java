package au.com.silverquest.flexigroup.view;

import au.com.silverquest.flexigroup.model.entity.Role;
import au.com.silverquest.flexigroup.services.AuthenticationServiceImpl;
import au.com.silverquest.flexigroup.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Stephanie
 * Date: 9/03/13
 * Time: 11:40 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("loginManager")
@Scope("view")
public class UserLoginView implements Serializable {

    private String username;
    private String password;

    @Autowired
    private UserDetailsService authenticationService;

    @Autowired
    private UserService userService;


    public UserLoginView() {
        super();
    }
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

    public String login() throws ServletException, IOException{
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = ((HttpServletRequest)context.getRequest());

        ServletResponse response = ((ServletResponse)context.getResponse());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_check");
        try {
            dispatcher.forward(request, response);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Username", "Please check your username."));
            return null;
        }
        FacesContext.getCurrentInstance().responseComplete();
        return null;
    }

    public String logout() throws ServletException, IOException{
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = ((HttpServletRequest)context.getRequest());

        ServletResponse resposnse = ((ServletResponse)context.getResponse());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_logout");
        dispatcher.forward(request, resposnse);
        FacesContext.getCurrentInstance().responseComplete();
        return null;
    }

    /**
     * Returns true if users with the role 'ROLE_USER' is restricted for the access
     * @return restricted
     */
    public boolean restrictUser() {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = ((HttpServletRequest)context.getRequest());
        java.security.Principal principal = request.getUserPrincipal();
        String authority = "";
        boolean isAdmin = false;
        if( principal != null ) {
            List<Role> roles = userService.getUser(principal.getName()).getRoles();
            for (int i=0; i<roles.size(); i++) {
                if (roles.get(i).getAuthority().equals("ROLE_ADMIN")) {
                    isAdmin = true;
                }
            }
        }
        if (isAdmin) {
            return false;
        }
        return true;
    }

    public void loginFailMsg(ActionEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Sample error message", "Username does not exists"));
    }


}
