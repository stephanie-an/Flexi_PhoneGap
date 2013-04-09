package au.com.silverquest.flexigroup.model.repository;

import au.com.silverquest.flexigroup.model.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stephanie
 * Date: 19/02/13
 * Time: 3:29 PM
 * To change this template use File | Settings | File Templates.
 */


@Repository
@RepositoryDefinition(domainClass = User.class, idClass = Integer.class)
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    @Query("select u from User u where u.username = :username")
    public User getUser(@Param("username") String username);


    @Query("select u from User u")
    public List<User> getAll();


}
