package entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_roles", schema = "library", catalog = "")
@IdClass(UserRolesPK.class)
public class UserRoles {
    private String userName;
    private String roleName;
    private Users usersByUserName;
    private Roles rolesByRoleName;

    @Id
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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
        UserRoles userRoles = (UserRoles) o;
        return Objects.equals(userName, userRoles.userName) &&
                Objects.equals(roleName, userRoles.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, roleName);
    }

    @ManyToOne
    @JoinColumn(name = "user_name", referencedColumnName = "user_name", nullable = false, insertable = false, updatable = false)
    public Users getUsersByUserName() {
        return usersByUserName;
    }

    public void setUsersByUserName(Users usersByUserName) {
        this.usersByUserName = usersByUserName;
    }

    @ManyToOne
    @JoinColumn(name = "role_name", referencedColumnName = "role_name", nullable = false, insertable = false, updatable = false)
    public Roles getRolesByRoleName() {
        return rolesByRoleName;
    }

    public void setRolesByRoleName(Roles rolesByRoleName) {
        this.rolesByRoleName = rolesByRoleName;
    }
}
