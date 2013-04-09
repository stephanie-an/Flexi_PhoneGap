package au.com.silverquest.flexigroup.model.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
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
public class Content {
    @Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @GeneratedValue
    @Id
    private Integer id;

    @Column(name = "UUID", unique = true, nullable = false, insertable = true, updatable = true, length = 16, precision = 0)
    @Basic
    private UUID uuid;

    @Column(name = "type", nullable = false, insertable = false, updatable = false, length = 10, precision = 0)
    @Basic
    private Integer type;

    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    private String description;

    @Column(name = "productid", nullable = true, insertable = true, updatable = true, length = 16, precision = 0)
    @Basic
    private Integer productid;

    @Column(name = "activate", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    private Date activate;

    @Column(name = "expire", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    @Temporal(TemporalType.TIMESTAMP)
    private Date expire;

    @Column(name = "content", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    private String content;

    @Column(name = "imagelink", nullable = true, insertable = true, updatable = true, length = 2147483647, precision = 0)
    @Basic
    private String imagelink;

    @Column(name = "lastmodified", columnDefinition = "TIMESTAMP", insertable = false, updatable = false, length = 19, precision = 0)
    @GeneratedValue
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastmodified;

    @ManyToOne
    @JoinColumn(name = "type", referencedColumnName = "id")
    @JsonIgnore
    private ContentType contentType;

    public Content() {
    }

    public Content(ContentType type, Integer productid, Date activate, Date expire, String content, String imagelink) {
        this.uuid = UUID.randomUUID();
        this.contentType = type;
        this.productid = productid;
        this.activate = activate;
        this.expire = expire;
        this.content = content;
        this.imagelink = imagelink;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getProductid() {
        return productid;
    }

    public void setProductid(Integer productid) {
        this.productid = productid;
    }

    public Date getActivate() {
        return activate;
    }

    public void setActivate(Date activate) {
        this.activate = activate;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }

    public Date getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(Date lastmodified) {
        this.lastmodified = lastmodified;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Content content1 = (Content) o;

        if (activate != null ? !activate.equals(content1.activate) : content1.activate != null) return false;
        if (content != null ? !content.equals(content1.content) : content1.content != null) return false;
        if (expire != null ? !expire.equals(content1.expire) : content1.expire != null) return false;
        if (id != null ? !id.equals(content1.id) : content1.id != null) return false;
        if (imagelink != null ? !imagelink.equals(content1.imagelink) : content1.imagelink != null) return false;
        if (description != null ? !description.equals(content1.description) : content1.description != null) return false;
        if (uuid != null ? !uuid.equals(content1.uuid) : content1.uuid != null) return false;
        if (productid != null ? !productid.equals(content1.productid) : content1.productid != null) return false;
        if (lastmodified != null ? !lastmodified.equals(content1.lastmodified) : content1.lastmodified != null) return false;
        if (type != null ? !type.equals(content1.type) : content1.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
        result = 31 * result + (description != null? description.hashCode() : 0);
        result = 31 * result + (productid != null ? productid.hashCode() : 0);
        result = 31 * result + (activate != null ? activate.hashCode() : 0);
        result = 31 * result + (expire != null ? expire.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (imagelink != null ? imagelink.hashCode() : 0);
        result = 31 * result + (lastmodified != null ? lastmodified.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);

        return result;
    }
}
