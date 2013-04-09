package au.com.silverquest.flexigroup.model.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: coreyb
 * Date: 18/02/13
 * Time: 8:45 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Equipment {
    @Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue
    @Id
    private Integer id;

    @javax.persistence.Column(name = "UUID", unique = true, nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Basic
    private UUID uuid;

    @Column(name = "name", unique = true, nullable = true, insertable = true, updatable = true, length = 50, precision = 0)
    @Basic
    private String name;

    @Column(name = "lastmodified", columnDefinition = "TIMESTAMP", insertable = false, updatable = false, length = 19, precision = 0)
    @GeneratedValue
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastmodified;

    public Equipment() {
    }

    public Equipment(String name) {
        this.name = name;
        this.uuid = UUID.randomUUID();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(Date lastmodified) {
        this.lastmodified = lastmodified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Equipment equipment = (Equipment) o;

        if (id != null ? !id.equals(equipment.id) : equipment.id != null) return false;
        if (uuid != null ? !uuid.equals(equipment.uuid) : equipment.uuid != null) return false;
        if (name != null ? !name.equals(equipment.name) : equipment.name != null) return false;
        if (lastmodified != null ? !lastmodified.equals(equipment.lastmodified) : equipment.lastmodified != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastmodified != null ? lastmodified.hashCode() : 0);
        return result;
    }
}
