package au.com.silverquest.flexigroup.services;

import au.com.bytecode.opencsv.CSVWriter;
import au.com.silverquest.flexigroup.model.entity.User;
import au.com.silverquest.flexigroup.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stephanie
 * Date: 5/03/13
 * Time: 3:25 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("userService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    public User getUser(String username) {
        return userRepository.getUser(username);
    }
    public List<User> listUser() {
        return userRepository.getAll();
    }
    public void save(User user) {
        userRepository.save(user);
    }
    public void delete(User user) {
        userRepository.delete(user);
    }
    public boolean userExists(String username) {
        User user = userRepository.getUser(username);
        if (user != null) {
               return true;
        }
        return false;
    }

    public void writeToCSV(List<User> users){
        String csv = "C:\\Users\\Stephanie\\Desktop\\output.csv";
        CSVWriter writer = null;
        try {
            File file = new File("C:\\Users\\Stephanie\\Desktop\\output.csv");
            file.createNewFile();
            writer =  new CSVWriter(new FileWriter(file));
            List<String[]> data = new ArrayList<String[]>();
            for (int i=0; i<users.size(); i++) {
                data.add(new String[] {users.get(i).getFirstname(), users.get(i).getLastname(), users.get(i).getUsername(), users.get(i).getEmail()});
            }
            /*data.add(new String[] {"India", "New Delhi"});
            data.add(new String[] {"United States", "Washington D.C"});
            data.add(new String[] {"Germany", "Berlin"});*/
            writer.writeAll(data);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (Exception e2) {

            }
        }
    }
}
