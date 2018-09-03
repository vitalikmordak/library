package dao;

import entities.HibernateUtil;
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
}
