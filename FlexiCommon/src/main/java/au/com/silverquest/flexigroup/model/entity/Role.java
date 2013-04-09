package au.com.silverquest.flexigroup.model.entity;

/**
 * Created with IntelliJ IDEA.
 * User: Stephanie
 * Date: 9/03/13
 * Time: 11:01 PM
 * To change this template use File | Settings | File Templates.
 */

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Role {

    @Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Basic
    @Column(unique = true)
    private String authority;

    @Basic
    @Column(name = "group_name", unique = true)
    private String groupName;

    /*@ManyToMany(mappedBy = "roles", cascade={CascadeType.ALL})
    private List<User> users;*/
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List<User> users;


    public Role() {

    }

    public Role(String authority, String groupName) {
        this.authority = authority;
        this.groupName = groupName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
