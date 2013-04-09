package au.com.silverquest.flexigroup.model.repository;

import au.com.silverquest.flexigroup.model.entity.Tax;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Simple repository interface for {@link Tax} instances. The interface is used to declare so called query methods,
 * methods to retrieve single entities or collections of them.
 *
 * @author coreyb
 */
@RepositoryDefinition(domainClass = Tax.class, idClass = Integer.class)
public interface TaxRepository extends PagingAndSortingRepository<Tax, Integer> {

    @Query("select t from Tax t")
    public List<Tax> getAll();

    @Query("select t from Tax t where t.lastmodified >= :lastmodified")
    public List<Tax> getAll(@Param("lastmodified") Date lastmodified);

    public Tax findByUuid(@Param("uuid") UUID uuid);

    public List<Tax> findByDescription(@Param("description") String description);
}

