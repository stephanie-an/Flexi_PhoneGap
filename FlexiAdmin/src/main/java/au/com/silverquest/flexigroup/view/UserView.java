package au.com.silverquest.flexigroup.view;

import au.com.silverquest.flexigroup.model.entity.Role;
import au.com.silverquest.flexigroup.model.entity.User;
import au.com.silverquest.flexigroup.model.repository.RoleRepository;
import au.com.silverquest.flexigroup.services.UserService;
import au.com.silverquest.flexigroup.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stephanie
 * Date: 5/03/13
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("userManager")
@Scope("view")
public class UserView implements Serializable {

    final Logger log = LoggerFactory.getLogger(UserView.class);

    private User user;
    private List<User> users;
    private List<User> filteredUsers;
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private Role role;
    private List<Role> roles;
    private String selectedRole;
    private List<String> selectedRoles;
    private List<String> rolesToDelete;
    private List<Role> availableRoles;


    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    /*@Autowired
    private Util util;*/

    @PostConstruct
    public void init() {
        user = new User();
        users = new ArrayList<User>();
        loadUsers();
        loadRoles();
        availableRoles = new ArrayList<Role>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        updateAvailableRoles();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public String getSelectedRole() {
        return selectedRole;
    }

    public void setSelectedRole(String selectedRole) {
        this.selectedRole = selectedRole;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }


    public String createUser(ActionEvent actionEvent) {
        boolean userExists = userService.userExists(getUsername());

        if (userExists) {
            log.debug(getUsername() + " already exists");
        } else {
            if (getPassword().equals(getConfirmPassword())) {

                role = roleRepository.findRoleByName(getSelectedRole());
                roles.add(role);
                //user = new User();
                //user.setRoles(getRoles());
                user.setFirstname(getFirstname());
                user.setLastname(getLastname());
                user.setUsername(getUsername());
                user.setPassword(getPassword());
                user.setEmail(getEmail());
                userService.save(user);



                // Displays success message
                //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User " + firstname + " " + lastname + " has been added successfully."));

                // Clears the form after creating user
                role = null;
                firstname = null;
                lastname = null;
                username = null;
                password = null;
                confirmPassword = null;
                email = null;
                user = new User();
                loadUsers();
            } else {
                log.debug("Password mismatch");
            }
        }
        return null;
    }

    public void updateUser() {
        userService.save(user);
    }

    public List<User> getUsers() {
        return userService.listUser();
    }

    public List<User> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<User> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

    public void loadUsers() {
        users = userService.listUser();
    }

    public void loadRoles() {
        roles = roleRepository.getAll();
    }

    public void deleteUser() {
        userService.delete(user);
    }

    public void addRoles() {
        for (int i=0; i<selectedRoles.size(); i++) {
            boolean exists = false;
            if (user.getRoles() != null) {
                for (int j=0; j<user.getRoles().size(); j++) {
                    if (selectedRoles.get(i).equals(user.getRoles().get(j).getGroupName())) {
                        exists = true;
                    }
                }
            }
            if (!exists) {
                user.addRole(roleRepository.findRoleByName(selectedRoles.get(i)));
            }
        }
        if (user.getId() != null) {
            updateUser();
        }
        updateAvailableRoles();
        selectedRoles = null;
    }

    public void deleteRoles() {
        for (int i=0; i<rolesToDelete.size(); i++) {
            user.deleteRole(roleRepository.findRoleByName(rolesToDelete.get(i)));
        }
        updateUser();
        updateAvailableRoles();
        rolesToDelete = null;
    }

    public void refreshUser() {
        user = new User();
    }



    public String login() throws ServletException, IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = ((HttpServletRequest)context.getRequest());

        ServletResponse response = ((ServletResponse)context.getResponse());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/j_spring_security_check");
        dispatcher.forward(request, response);
        FacesContext.getCurrentInstance().responseComplete();


        return null;
    }

    public void writeToCSV() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));

        List<User> usersToWrite = null;
        if (getFilteredUsers() != null) {
            usersToWrite = getFilteredUsers();
        } else {
            usersToWrite = getUsers();
        }
        List<String[]> data = new ArrayList<String[]>();
        data.add(new String[] {"ID", "First Name", "Last Name", "Username", "Email"});
        for (int i=0; i<usersToWrite.size(); i++) {
            data.add(new String[] {usersToWrite.get(i).getId().toString(), usersToWrite.get(i).getFirstname(), usersToWrite.get(i).getLastname(), usersToWrite.get(i).getUsername(), usersToWrite.get(i).getEmail()});
        }
        Util.writeToCSV("C:\\Users\\Stephanie\\Desktop\\users_\" + dateFormat.format(date) + \".csv", data);
    }


    public List<String> getSelectedRoles() {
        return selectedRoles;
    }
    public void setSelectedRoles(List<String> selectedRoles) {
        this.selectedRoles = selectedRoles;
    }

    public List<String> getRolesToDelete() {
        return rolesToDelete;
    }
    public void setRolesToDelete(List<String> rolesToDelete) {
        this.rolesToDelete = rolesToDelete;
    }

    public List<Role> getAvailableRoles() {
        updateAvailableRoles();
        if (availableRoles.size() == 0) {
            return null;
        }
        return availableRoles;
    }
    public void setAvailableRoles(List<Role> availableRoles) {
        this.availableRoles = availableRoles;
    }

    private void updateAvailableRoles() {
        List<Role> allRoles = roleRepository.getAll();
        List<Role> userRoles = user.getRoles();
        availableRoles = new ArrayList<Role>();
        if (userRoles != null) {
            for (int i=0; i<allRoles.size(); i++) {
                boolean exists = false;
                for (int j=0; j<userRoles.size(); j++) {
                    if (allRoles.get(i).getId() == userRoles.get(j).getId()) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    availableRoles.add(allRoles.get(i));
                }
            }
        } else {
            availableRoles = allRoles;
        }
    }

   /* private return md5() {
        String plainPassword = getPassword();
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("md5");
        } catch (Exception e) {

        }
        m.reset();
        m.update(plainPassword.getBytes());
        byte[] digest = m.digest();
        BigInteger bigInt = new BigInteger(1,digest);
        String hashedPassword = bigInt.toString(16);
        while(hashedPassword.length() < 32 ){
            hashedPassword = "0"+hashedPassword;
        }
        return hashedPassword;
    }*/
}