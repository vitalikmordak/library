package entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Users {
    private String userName;
    private String userPass;
    private Collection<UserRoles> userRolesByUserName;

    @Id
    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "user_pass")
    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return Objects.equals(userName, users.userName) &&
                Objects.equals(userPass, users.userPass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, userPass);
    }

    @OneToMany(mappedBy = "usersByUserName")
    public Collection<UserRoles> getUserRolesByUserName() {
        return userRolesByUserName;
    }

    public void setUserRolesByUserName(Collection<UserRoles> userRolesByUserName) {
        this.userRolesByUserName = userRolesByUserName;
    }
}
