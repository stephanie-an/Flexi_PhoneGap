package au.com.silverquest.flexigroup.model.repository;

import au.com.silverquest.flexigroup.model.entity.Equipment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Simple repository interface for {@link Equipment} instances. The interface is used to declare so called query methods,
 * methods to retrieve single entities or collections of them.
 *
 * @author coreyb
 */
@RepositoryDefinition(domainClass = Equipment.class, idClass = Integer.class)
public interface EquipmentRepository extends PagingAndSortingRepository<Equipment, Integer> {

    @Query("select e from Equipment e")
    public List<Equipment> getAll();

    @Query("select e from Equipment e where e.lastmodified >= :lastmodified")
    public List<Equipment> getAll(@Param("lastmodified") Date lastmodified);

    public Equipment findByUuid(@Param("uuid") UUID uuid);

    @Query("select e from Equipment e where e.name = :name")
    public List<Equipment> findByName(@Param("name") String name);
}

