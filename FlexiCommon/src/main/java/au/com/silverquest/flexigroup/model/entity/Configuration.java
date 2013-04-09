package au.com.silverquest.flexigroup.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * User: coreyb
 * Date: 18/02/13
 * Time: 8:45 AM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Configuration {
    @Column(name = "configkey", unique = true, nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @Id
    private String key;

    @Column(name = "configvalue", nullable = false, insertable = true, updatable = true, length = 4096, precision = 0)
    @Basic
    private String value;

    public Configuration() {
    }

    public Configuration(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String configkey) {
        this.key = configkey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String configurationvalue) {
        this.value = configurationvalue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Configuration that = (Configuration) o;

        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
