package au.com.silverquest.flexigroup.model.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

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
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"productid", "minamount", "maxamount", "term"})
})
public class Rates {
    @Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue
    @Id
    private Integer id;

    @javax.persistence.Column(name = "UUID", unique = true, nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Basic
    private UUID uuid;

    @Column(name = "minamount", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    private Integer minamount;

    @Column(name = "maxamount", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    private Integer maxamount;

    @Column(name = "term", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    private Integer term;

    @Column(name = "rate", nullable = true, insertable = true, updatable = true, precision = 6, scale = 2)
    @Basic
    private BigDecimal rate;

    @Column(name = "productid", nullable = true, insertable = false, updatable = false, length = 10, precision = 0)
    @Basic
    private Integer productid;

    @Column(name = "lastmodified", columnDefinition = "TIMESTAMP", insertable = false, updatable = false, length = 19, precision = 0)
    @GeneratedValue
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastmodified;

    @ManyToOne
    @JoinColumn(name = "productid", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Product product;

    public Rates() {
    }

    public Rates(Integer minamount, Integer maxamount, Integer term, BigDecimal rate, Product product) {
        this.minamount = minamount;
        this.maxamount = maxamount;
        this.term = term;
        this.rate = rate;
        this.product = product;
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

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
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

        Rates rates = (Rates) o;

        if (id != null ? !id.equals(rates.id) : rates.id != null) return false;
        if (uuid != null ? !uuid.equals(rates.uuid) : rates.uuid != null) return false;
        if (maxamount != null ? !maxamount.equals(rates.maxamount) : rates.maxamount != null) return false;
        if (minamount != null ? !minamount.equals(rates.minamount) : rates.minamount != null) return false;
        if (rate != null ? !rate.equals(rates.rate) : rates.rate != null) return false;
        if (term != null ? !term.equals(rates.term) : rates.term != null) return false;
        if (productid != null ? !productid.equals(rates.productid) : rates.productid != null) return false;
        if (lastmodified != null ? !lastmodified.equals(rates.lastmodified) : rates.lastmodified != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (minamount != null ? minamount.hashCode() : 0);
        result = 31 * result + (maxamount != null ? maxamount.hashCode() : 0);
        result = 31 * result + (term != null ? term.hashCode() : 0);
        result = 31 * result + (rate != null ? rate.hashCode() : 0);
        result = 31 * result + (productid != null ? productid.hashCode() : 0);
        result = 31 * result + (lastmodified != null ? lastmodified.hashCode() : 0);
        return result;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
