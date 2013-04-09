package au.com.silverquest.flexigroup.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
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
public class Tax {
    @Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue
    @Id
    private Integer id;

    @javax.persistence.Column(name = "UUID", unique = true, nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Basic
    private UUID uuid;

    @Column(name = "description", unique = true, nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @Basic
    private String description;

    @Column(name = "minamount", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    private Integer minamount;

    @Column(name = "maxamount", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    private Integer maxamount;

    @Column(name = "rate", nullable = false, insertable = true, updatable = true, precision = 6, scale = 2)
    @Basic
    private BigDecimal rate;

    @Column(name = "multiplier", nullable = false, insertable = true, updatable = true, precision = 6, scale = 4)
    @Basic
    private BigDecimal multiplyFactor;

    @Column(name = "lastmodified", columnDefinition = "TIMESTAMP", insertable = false, updatable = false, length = 19, precision = 0)
    @GeneratedValue
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastmodified;

    public Tax() {
    }

    public Tax(String description, Integer minamount, Integer maxamount, BigDecimal rate, BigDecimal multiplyFactor) {
        this.description = description;
        this.minamount = minamount;
        this.maxamount = maxamount;
        this.rate = rate;
        this.multiplyFactor = multiplyFactor;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMinamount() {
        return minamount;
    }

    public void setMinamount(Integer minamount) {
        this.minamount = minamount;
    }

    public Integer getMaxamount() {
        return maxamount;
    }

    public void setMaxamount(Integer maxamount) {
        this.maxamount = maxamount;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public BigDecimal getMultiplyFactor() {
        return multiplyFactor;
    }

    public void setMultiplyFactor(BigDecimal multiplyFactor) {
        this.multiplyFactor = multiplyFactor;
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

        Tax tax = (Tax) o;

        if (id != null ? !id.equals(tax.id) : tax.id != null) return false;
        if (uuid != null ? !uuid.equals(tax.uuid) : tax.uuid != null) return false;
        if (description != null ? !description.equals(tax.description) : tax.description != null) return false;
        if (maxamount != null ? !maxamount.equals(tax.maxamount) : tax.maxamount != null) return false;
        if (minamount != null ? !minamount.equals(tax.minamount) : tax.minamount != null) return false;
        if (rate != null ? !rate.equals(tax.rate) : tax.rate != null) return false;
        if (multiplyFactor != null ? !multiplyFactor.equals(tax.multiplyFactor) : tax.multiplyFactor != null)
            return false;
        if (lastmodified != null ? !lastmodified.equals(tax.lastmodified) : tax.lastmodified != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (minamount != null ? minamount.hashCode() : 0);
        result = 31 * result + (maxamount != null ? maxamount.hashCode() : 0);
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        result = 31 * result + (multiplyFactor != null ? multiplyFactor.hashCode() : 0);
        result = 31 * result + (lastmodified != null ? lastmodified.hashCode() : 0);
        return result;
    }
}
