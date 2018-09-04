package dao;

import entities.HibernateUtil;
import entities.UserRoles;
import entities.Users;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class UserDAO {

    public static boolean login(String username, String password) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Users> criteriaQuery = criteriaBuilder.createQuery(Users.class);
        Root<Users> user = criteriaQuery.from(Users.class);
        try {
            criteriaQuery.select(user).where(criteriaBuilder.equal(user.get("userName"), username), criteriaBuilder.equal(user.get("userPass"), password));
            session.createQuery(criteriaQuery).getSingleResult();
        } catch (NoResultException e) {
            return false;
        }
        return true;
    }

    // Check if user can use edit mode
    public static boolean editModeAccess(String username) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UserRoles> query = criteriaBuilder.createQuery(UserRoles.class);
        Root<UserRoles> userRoles = query.from(UserRoles.class);
        try {
            query.select(userRoles).where(criteriaBuilder.equal(userRoles.get("userName"), username), criteriaBuilder.equal(userRoles.get("roleName"), "admin"));
            session.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return false;
        }
        return true;
    }
}
