package au.com.silverquest.flexigroup.model.repository;

import au.com.silverquest.flexigroup.model.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Simple repository interface for {@link Product} instances. The interface is used to declare so called query methods,
 * methods to retrieve single entities or collections of them.
 *
 * @author coreyb
 */
@RepositoryDefinition(domainClass = Product.class, idClass = Integer.class)
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer> {

    @Query("select p from Product p")
    public List<Product> getAll();

    @Query("select p from Product p where p.lastmodified >= :lastmodified")
    public List<Product> getAll(@Param("lastmodified") Date lastmodified);

    public Product findByUuid(@Param("uuid") UUID uuid);

    @Query("select p from Product p where p.name = :name")
    public List<Product> findByName(@Param("name") String name);
}

