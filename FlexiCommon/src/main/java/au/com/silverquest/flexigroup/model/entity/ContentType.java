package au.com.silverquest.flexigroup.model.entity;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Stephanie
 * Date: 5/03/13
 * Time: 5:22 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class ContentType {
    @Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue
    @Id
    private Integer id;

    @javax.persistence.Column(name = "UUID", unique = true, nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Basic
    private UUID uuid;

    @Column(name = "type", unique = true, nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @Basic
    private String type;

    public ContentType() {
    }


    public ContentType(String type) {
        this.uuid = UUID.randomUUID();
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

   /* @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Content content1 = (Content) o;

        if (activate != null ? !activate.equals(content1.activate) : content1.activate != null) return false;
        if (content != null ? !content.equals(content1.content) : content1.content != null) return false;
        if (expire != null ? !expire.equals(content1.expire) : content1.expire != null) return false;
        if (id != null ? !id.equals(content1.id) : content1.id != null) return false;
        if (imagelink != null ? !imagelink.equals(content1.imagelink) : content1.imagelink != null) return false;
        if (type != null ? !type.equals(content1.type) : content1.type != null) return false;
        if (uuid != null ? !uuid.equals(content1.uuid) : content1.uuid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (activate != null ? activate.hashCode() : 0);
        result = 31 * result + (expire != null ? expire.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (imagelink != null ? imagelink.hashCode() : 0);
        return result;
    }*/

}