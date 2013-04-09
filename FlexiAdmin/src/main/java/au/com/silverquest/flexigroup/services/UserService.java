package au.com.silverquest.flexigroup.services;

import au.com.silverquest.flexigroup.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stephanie
 * Date: 5/03/13
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {

    public User getUser(String username);
    public List<User> listUser();
    public void save(User user);
    public void delete(User user);
    public boolean userExists(String username);
    public void writeToCSV(List<User> users);
}
