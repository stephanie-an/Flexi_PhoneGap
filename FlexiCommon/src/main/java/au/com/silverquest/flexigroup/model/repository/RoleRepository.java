package au.com.silverquest.flexigroup.model.repository;

import au.com.silverquest.flexigroup.model.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stephanie
 * Date: 11/03/13
 * Time: 7:09 PM
 * To change this template use File | Settings | File Templates.
 */
@Repository
//@RestResource(exported = false)
public interface RoleRepository extends PagingAndSortingRepository<Role, Integer> {

    @Query("select r from Role r")
    public List<Role> getAll();

    @Query("select r from Role r where groupName = :groupName")
    public Role findRoleByName(@Param("groupName") String groupName);

    @Query("select r.authority from Role r where groupName = :groupName")
    public List<String> findAuthorieisByGroupName(@Param("groupName") String groupName);

}
