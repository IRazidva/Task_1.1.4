package jm.task.core.jdbc.dao;

//import com.mysql.cj.Session;
import jm.task.core.jdbc.model.User;

import java.util.ArrayList;
import java.util.List;

import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS user (" +
                "id INT PRIMARY KEY AUTO_INCREMENT," +
                "name VARCHAR (15)," +
                "lastname VARCHAR (20)," +
                "age INT )";
        session.createSQLQuery(sql).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Transaction transaction = null;

        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            transaction.commit();
            session.close();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null){
                session.delete(user);
            }
            transaction.commit();
            session.close();

        }
        catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        List<User> allUser = new ArrayList<>();

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            allUser = session.createQuery("from User", User.class).list();
            transaction.commit();
            session.close();
        }
        catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return allUser;
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;

        try (Session session = Util.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.createQuery("delete from User").executeUpdate();
            transaction.commit();
            session.close();
        }
        catch (Exception e){
            if (transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
