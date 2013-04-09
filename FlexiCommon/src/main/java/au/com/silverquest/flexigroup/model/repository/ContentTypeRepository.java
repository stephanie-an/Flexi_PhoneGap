package au.com.silverquest.flexigroup.model.repository;

import au.com.silverquest.flexigroup.model.entity.ContentType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Simple repository interface for {@link ContentType} instances. The interface is used to declare so called query methods,
 * methods to retrieve single entities or collections of them.
 *
 * @author Stephanie
 */
@RepositoryDefinition(domainClass = ContentType.class, idClass = Integer.class)
public interface ContentTypeRepository extends PagingAndSortingRepository<ContentType, Integer> {

    @Query("select c from ContentType c")
    public List<ContentType> getAll();

    @Query("select c from ContentType c where c.type = :typeName")
    public ContentType getContentType(@Param("typeName") String typeName);
}

