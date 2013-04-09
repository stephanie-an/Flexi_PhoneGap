package au.com.silverquest.flexigroup.model.repository;

import au.com.silverquest.flexigroup.model.entity.SalesStaff;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Simple repository interface for {@link SalesStaff} instances. The interface is used to declare so called query methods,
 * methods to retrieve single entities or collections of them.
 *
 * @author coreyb
 */
@Repository
@RepositoryDefinition(domainClass = SalesStaff.class, idClass = Integer.class)
public interface SalesStaffRepository extends PagingAndSortingRepository<SalesStaff, Integer> {

    public SalesStaff findByUuid(@Param("uuid") UUID uuid);

    @Query("select s from SalesStaff s")
    public List<SalesStaff> getAll();

    @Query("select s from SalesStaff s where s.email = :email")
    public List<SalesStaff> findByEmail(@Param("email") String email);
}

