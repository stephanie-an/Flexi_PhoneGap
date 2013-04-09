package au.com.silverquest.flexigroup.model.repository;

import au.com.silverquest.flexigroup.model.entity.Configuration;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.stereotype.Repository;

/**
 * Simple repository interface for {@link Configuration} instances. The interface is used to declare so called query methods,
 * methods to retrieve single entities or collections of them.
 *
 * @author coreyb
 */
@Repository
@RepositoryDefinition(domainClass = Configuration.class, idClass = String.class)
public interface ConfigurationRepository extends PagingAndSortingRepository<Configuration, String> {

}

