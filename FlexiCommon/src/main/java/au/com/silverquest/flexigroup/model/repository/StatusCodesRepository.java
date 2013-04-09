package au.com.silverquest.flexigroup.model.repository;

import au.com.silverquest.flexigroup.model.entity.StatusCodes;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Simple repository interface for {@link StatusCodes} instances. The interface is used to declare so called query methods,
 * methods to retrieve single entities or collections of them.
 *
 * @author coreyb
 */
@RepositoryDefinition(domainClass = StatusCodes.class, idClass = Integer.class)
public interface StatusCodesRepository extends PagingAndSortingRepository<StatusCodes, Integer> {

    @Query("select s from StatusCodes s")
    public List<StatusCodes> getAll();

    public List<StatusCodes> findByValue(@Param("value") String value);
}

