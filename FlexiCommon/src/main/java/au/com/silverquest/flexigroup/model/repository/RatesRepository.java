package au.com.silverquest.flexigroup.model.repository;

import au.com.silverquest.flexigroup.model.entity.Rates;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Simple repository interface for {@link Rates} instances. The interface is used to declare so called query methods,
 * methods to retrieve single entities or collections of them.
 *
 * @author coreyb
 */
@Repository
@RepositoryDefinition(domainClass = Rates.class, idClass = Integer.class)
public interface RatesRepository extends PagingAndSortingRepository<Rates, Integer> {

    @Query("select r from Rates r")
    public List<Rates> getAll();

    @Query("select r from Rates r where r.lastmodified >= :lastmodified")
    public List<Rates> getAll(@Param("lastmodified") Date lastmodified);

    @Query("select r from Rates r where r.product.name = :product and r.term = :term and :price >= r.minamount" +
            " and :price < r.maxamount")
    public List<Rates> findByProductAndTerm(@Param("price") Integer price, @Param("term") Integer term,
                                            @Param("product") String product);


    @Query("select r from Rates r where r.minamount = 0 and r.maxamount = 0 and r.term = :term")
    public List<Rates> findResidualRate(@Param("term") Integer term);
}

