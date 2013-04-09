package au.com.silverquest.flexigroup.model.repository;

import au.com.silverquest.flexigroup.model.entity.Dealer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/**
 * Simple repository interface for {@link Dealer} instances. The interface is used to declare so called query methods,
 * methods to retrieve single entities or collections of them.
 *
 * @author coreyb
 */
@RepositoryDefinition(domainClass = Dealer.class, idClass = Integer.class)
public interface DealerRepository extends PagingAndSortingRepository<Dealer, Integer> {

    @Query("select d from Dealer d")
    public List<Dealer> getAll();

    public Dealer findByUuid(@Param("uuid") UUID uuid);

    @Query("select d from Dealer d where d.name = :name")
    public List<Dealer> findByName(@Param("name") String name);

    @Query("select d from Dealer d where d.name = :name")
    public Dealer findDealer(@Param("name") String name);
}

