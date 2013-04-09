package au.com.silverquest.flexigroup.model.entity;

import javax.persistence.*;
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
        @UniqueConstraint(columnNames = {"id"}),
        @UniqueConstraint(columnNames = {"UUID"})
})
public class Dealer {
    @Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue
    @Id
    private Integer id;

    @javax.persistence.Column(name = "UUID", unique = true, nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Basic
    private UUID uuid;

    @Column(name = "name", unique = false, nullable = false, insertable = true, updatable = true, length = 255, precision = 0)
    @Basic
    private String name;

    @Column(name = "email", unique = false, nullable = true, insertable = true, updatable = true, length = 255, precision = 0)
    @Basic
    private String email;

    @Column(name = "phone", nullable = true, insertable = true, updatable = true, length = 20, precision = 0)
    @Basic
    private String phone;

    public Dealer() {
    }

    public Dealer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dealer dealer = (Dealer) o;

        if (email != null ? !email.equals(dealer.email) : dealer.email != null) return false;
        if (id != null ? !id.equals(dealer.id) : dealer.id != null) return false;
        if (name != null ? !name.equals(dealer.name) : dealer.name != null) return false;
        if (phone != null ? !phone.equals(dealer.phone) : dealer.phone != null) return false;
        if (uuid != null ? !uuid.equals(dealer.uuid) : dealer.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }
}
