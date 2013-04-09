package au.com.silverquest.flexigroup.model.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
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
public class Customer {
    @Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue
    @Id
    private Integer id;

    @javax.persistence.Column(name = "UUID", unique = true, nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Basic
    private UUID uuid;

    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 255, precision = 0)
    @Basic
    private String name;

    @Column(name = "email", nullable = true, insertable = true, updatable = true, length = 255, precision = 0)
    @Basic
    private String email;

    @Column(name = "phone", nullable = true, insertable = true, updatable = true, length = 20, precision = 0)
    @Basic
    private String phone;

    @Column(name = "comment", nullable = true, insertable = true, updatable = true, length = 65535, precision = 0)
    @Basic
    private String comment;

    public Customer() {
    }

    public Customer(String name, String email, String phone, String comment) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.comment = comment;
        this.uuid = UUID.randomUUID();
    }

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private Collection<Quote> quotes;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (comment != null ? !comment.equals(customer.comment) : customer.comment != null) return false;
        if (email != null ? !email.equals(customer.email) : customer.email != null) return false;
        if (id != null ? !id.equals(customer.id) : customer.id != null) return false;
        if (name != null ? !name.equals(customer.name) : customer.name != null) return false;
        if (phone != null ? !phone.equals(customer.phone) : customer.phone != null) return false;
        if (uuid != null ? !uuid.equals(customer.uuid) : customer.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    public Collection<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(Collection<Quote> quotes) {
        this.quotes = quotes;
    }
}
