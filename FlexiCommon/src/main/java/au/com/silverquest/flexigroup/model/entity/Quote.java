package au.com.silverquest.flexigroup.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
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
public class Quote {
    @Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue
    @Id
    private Integer id;

    @Column(name = "UUID", unique = true, nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Basic
    private UUID uuid;

    @Column(name = "term", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    private Integer term;

    @Column(name = "amount", nullable = false, insertable = true, updatable = true, precision = 10, scale = 2)
    @Basic
    private BigDecimal amount;

    @Column(name = "monthlypayment", nullable = true, insertable = true, updatable = true, precision = 10, scale = 2)
    @Basic
    private BigDecimal monthlypayment;

    @Column(name = "monthlypaymentaftertax", nullable = true, insertable = true, updatable = true, precision = 10, scale = 2)
    @Basic
    private BigDecimal monthlypaymentaftertax;

    @Column(name = "weeklypayment", nullable = true, insertable = true, updatable = true, precision = 10, scale = 2)
    @Basic
    private BigDecimal weeklypayment;

    @Column(name = "weeklypaymentaftertax", nullable = true, insertable = true, updatable = true, precision = 10, scale = 2)
    @Basic
    private BigDecimal weeklypaymentaftertax;

    @javax.persistence.Column(name = "residual", nullable = true, insertable = true, updatable = true, precision = 10, scale = 2)
    @Basic
    private BigDecimal residual;

    @Column(name = "submitdate", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    private Date submitdate;

    @ManyToOne
    @JoinColumn(name = "customerid", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "equipmentid", referencedColumnName = "id", nullable = false)
    private Equipment equipment;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "productid", referencedColumnName = "id", nullable = false)
    private Product product;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "salesid", referencedColumnName = "id", nullable = false)
    private SalesStaff salesStaff;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "status", referencedColumnName = "id", nullable = false)
    private StatusCodes statuscode;

    @ManyToOne
    @javax.persistence.JoinColumn(name = "taxid", referencedColumnName = "id", nullable = false)
    private Tax tax;

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

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getMonthlypayment() {
        return monthlypayment;
    }

    public void setMonthlypayment(BigDecimal monthlypayment) {
        this.monthlypayment = monthlypayment;
    }

    public BigDecimal getMonthlypaymentaftertax() {
        return monthlypaymentaftertax;
    }

    public void setMonthlypaymentaftertax(BigDecimal monthlypaymentaftertax) {
        this.monthlypaymentaftertax = monthlypaymentaftertax;
    }

    public BigDecimal getWeeklypayment() {
        return weeklypayment;
    }

    public void setWeeklypayment(BigDecimal weeklypayment) {
        this.weeklypayment = weeklypayment;
    }

    public BigDecimal getWeeklypaymentaftertax() {
        return weeklypaymentaftertax;
    }

    public void setWeeklypaymentaftertax(BigDecimal weeklypaymentaftertax) {
        this.weeklypaymentaftertax = weeklypaymentaftertax;
    }

    public BigDecimal getResidual() {
        return residual;
    }

    public void setResidual(BigDecimal residual) {
        this.residual = residual;
    }

    public Date getSubmitdate() {
        return submitdate;
    }

    public void setSubmitdate(Date submitdate) {
        this.submitdate = submitdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quote quote = (Quote) o;

        if (amount != null ? !amount.equals(quote.amount) : quote.amount != null) return false;
        if (id != null ? !id.equals(quote.id) : quote.id != null) return false;
        if (monthlypayment != null ? !monthlypayment.equals(quote.monthlypayment) : quote.monthlypayment != null)
            return false;
        if (monthlypaymentaftertax != null ? !monthlypaymentaftertax.equals(quote.monthlypaymentaftertax) : quote.monthlypaymentaftertax != null)
            return false;
        if (residual != null ? !residual.equals(quote.residual) : quote.residual != null) return false;
        if (term != null ? !term.equals(quote.term) : quote.term != null) return false;
        if (uuid != null ? !uuid.equals(quote.uuid) : quote.uuid != null) return false;
        if (weeklypayment != null ? !weeklypayment.equals(quote.weeklypayment) : quote.weeklypayment != null)
            return false;
        if (weeklypaymentaftertax != null ? !weeklypaymentaftertax.equals(quote.weeklypaymentaftertax) : quote.weeklypaymentaftertax != null)
            return false;
        if (submitdate != null ? !submitdate.equals(quote.submitdate) : quote.submitdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (term != null ? term.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        result = 31 * result + (monthlypayment != null ? monthlypayment.hashCode() : 0);
        result = 31 * result + (monthlypaymentaftertax != null ? monthlypaymentaftertax.hashCode() : 0);
        result = 31 * result + (weeklypayment != null ? weeklypayment.hashCode() : 0);
        result = 31 * result + (weeklypaymentaftertax != null ? weeklypaymentaftertax.hashCode() : 0);
        result = 31 * result + (residual != null ? residual.hashCode() : 0);
        result = 31 * result + (submitdate != null ? submitdate.hashCode() : 0);
        return result;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public SalesStaff getSalesStaff() {
        return salesStaff;
    }

    public void setSalesStaff(SalesStaff salesStaff) {
        this.salesStaff = salesStaff;
    }

    public StatusCodes getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(StatusCodes statuscode) {
        this.statuscode = statuscode;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }
}
