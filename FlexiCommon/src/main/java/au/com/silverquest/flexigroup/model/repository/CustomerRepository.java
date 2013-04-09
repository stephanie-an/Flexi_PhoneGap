package au.com.silverquest.flexigroup.model.repository;

import au.com.silverquest.flexigroup.model.entity.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * Simple repository interface for {@link Customer} instances. The interface is used to declare so called query methods,
 * methods to retrieve single entities or collections of them.
 *
 * @author coreyb
 */
@RepositoryDefinition(domainClass = Customer.class, idClass = Integer.class)
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer> {

    public Customer findByUuid(@Param("uuid") UUID uuid);

    @Query("select c from Customer c where c.name = :name and c.email = :email and c.phone = :phone")
    public List<Customer> findByNameAndEmailAndPhone(@Param("name") String name,
                                                     @Param("email") String email,
                                                     @Param("phone") String phone);
}

