package au.com.silverquest.flexigroup.model.repository;

import au.com.silverquest.flexigroup.model.entity.Content;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Simple repository interface for {@link Content} instances. The interface is used to declare so called query methods,
 * methods to retrieve single entities or collections of them.
 *
 * @author coreyb
 */
@RepositoryDefinition(domainClass = Content.class, idClass = Integer.class)
public interface ContentRepository extends PagingAndSortingRepository<Content, Integer> {

    @Query("select c from Content c")
    public List<Content> getAll();

    @Query("select c from Content c where c.lastmodified >= :lastmodified")
    public List<Content> getAll(@Param("lastmodified") Date lastmodified);

    public Content findByUuid(@Param("uuid") UUID uuid);

    @Query("select c from Content c where c.activate < :date and (c.expire is null or c.expire > :date) and c.type = :type and c.lastmodified >= :lastmodified")
    public List<Content> findCurrentByType(@Param("date") Date date, @Param("type") Integer type, @Param("lastmodified") Date lastmodified);

    @Query("select c from Content c where c.activate < :date and (c.expire is null or c.expire > :date) and c.lastmodified >= :lastmodified")
    public List<Content> findCurrentContent(@Param("date") Date date, @Param("lastmodified") Date lastmodified);

    @Query("select c from Content c where c.type = :type")
    public List<Content> getContentsByType(@Param("type") Integer type);
}

