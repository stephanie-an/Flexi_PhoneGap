package au.com.silverquest.flexigroup.model.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: coreyb
 * Date: 18/02/13
 * Time: 8:45 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class StatusCodes {
    @javax.persistence.Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    private Integer id;

    @javax.persistence.Column(name = "UUID", unique = true, nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Basic
    private UUID uuid;

    @javax.persistence.Column(name = "value", unique = true, nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @Basic
    private String value;

    @OneToMany(mappedBy = "statuscode")
    @JsonIgnore
    private Collection<Quote> quotesById;

    public StatusCodes() {
    }

    public StatusCodes(Integer id, String value) {
        this.id = id;
        this.uuid = UUID.randomUUID();
        this.value = value;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StatusCodes that = (StatusCodes) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    public Collection<Quote> getQuotesById() {
        return quotesById;
    }

    public void setQuotesById(Collection<Quote> quotesById) {
        this.quotesById = quotesById;
    }
}
