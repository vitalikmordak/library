package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Roles {
    private String roleName;
    private Collection<UserRoles> userRolesByRoleName;

    @Id
    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roles roles = (Roles) o;
        return Objects.equals(roleName, roles.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName);
    }

    @OneToMany(mappedBy = "rolesByRoleName")
    public Collection<UserRoles> getUserRolesByRoleName() {
        return userRolesByRoleName;
    }

    public void setUserRolesByRoleName(Collection<UserRoles> userRolesByRoleName) {
        this.userRolesByRoleName = userRolesByRoleName;
    }
}
