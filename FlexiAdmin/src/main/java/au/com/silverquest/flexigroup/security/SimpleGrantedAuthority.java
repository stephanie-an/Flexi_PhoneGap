package au.com.silverquest.flexigroup.security;

/**
 * Created with IntelliJ IDEA.
 * User: Stephanie
 * Date: 10/03/13
 * Time: 12:03 AM
 * To change this template use File | Settings | File Templates.
 */

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

public final class SimpleGrantedAuthority implements GrantedAuthority {


    private final String role;

    public SimpleGrantedAuthority(String role) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }

    public String getAuthority() {
        return role;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof SimpleGrantedAuthority) {
            return role.equals(((SimpleGrantedAuthority) obj).role);
        }

        return false;
    }

    public int hashCode() {
        return this.role.hashCode();
    }

    public String toString() {
        return this.role;
    }
}