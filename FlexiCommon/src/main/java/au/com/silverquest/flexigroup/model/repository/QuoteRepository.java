package au.com.silverquest.flexigroup.model.repository;

import au.com.silverquest.flexigroup.model.entity.Quote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Simple repository interface for {@link Quote} instances. The interface is used to declare so called query methods,
 * methods to retrieve single entities or collections of them.
 *
 * @author coreyb
 */
@RepositoryDefinition(domainClass = Quote.class, idClass = Integer.class)
public interface QuoteRepository extends PagingAndSortingRepository<Quote, Integer> {

    @Query("select q from Quote q")
    public List<Quote> getAll();

    public Quote findByUuid(@Param("uuid") UUID uuid);

    @Query("select q from Quote q where q.product.name like :productName and q.submitdate >= :fromSubmitDate and q.submitdate <= :toSubmitDate")
    public List<Quote> findByProductNameAndSubmitDate(@Param("productName") String productName, @Param("fromSubmitDate") Date fromSubmitDate, @Param("toSubmitDate") Date toSubmitDate);

    @Query("select q from Quote q where q.product.name like :productName")
    public List<Quote> findByProductName(@Param("productName") String productName);

    @Query("select q from Quote q where q.submitdate >= :fromSubmitdate and q.submitdate <= :toSubmitDate")
    public List<Quote> findbyDateRange(@Param("fromSubmitdate") Date fromSubmitdate, @Param("toSubmitDate") Date toSubmitDate);

    @Query("select q from Quote q where q.submitdate >= :fromSubmitDate")
    public List<Quote> findByFromDate(@Param("fromSubmitDate") Date fromSubmitDate);

    @Query("select q from Quote q where q.submitdate <= :toSubmitDate")
    public List<Quote> findByToDate(@Param("toSubmitDate") Date toSubmitDate);

    @Query("select q from Quote q where q.product.name like :productName and q.submitdate >= :fromSubmitDate")
    public List<Quote> findByProductNameAndFromDate(@Param("productName") String productName, @Param("fromSubmitDate") Date fromSubmitDate);

    @Query("select q from Quote q where q.product.name like :productName and q.submitdate <= :toSubmitDate")
    public List<Quote> findByProductNameAndToDate(@Param("productName") String productName, @Param("toSubmitDate") Date toSubmitDate);
}

