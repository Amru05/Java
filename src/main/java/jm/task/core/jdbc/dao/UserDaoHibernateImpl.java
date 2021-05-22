package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.Query;
import org.hibernate.Session;

import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.metamodel.MetadataSources;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;


import java.sql.SQLException;
import java.util.EnumSet;
import java.util.List;
public class UserDaoHibernateImpl implements UserDao {
    private static Session session;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        try {
            session = (Session) Util.getSession();
            session.beginTransaction();
            String sql = "CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.close();
        }


    }

    @Override
    public void dropUsersTable() {

        try {
            session = (Session) Util.getSession();
            session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS users";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.close();
        }


    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try  {
            session = (Session) Util.getSession();
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.println("User" + name + " добавлен в базу данных");
        }catch (Exception e){
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = (Session) Util.getSession();
            session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }catch (Exception e){
            session.close();
        }

    }

    @Override
    public List<User> getAllUsers() {

        session = (Session) Util.getSession();
        session.beginTransaction();
        List <User> users = (List<User>) session.createQuery("FROM " + User.class.getSimpleName());
        session.getTransaction().commit();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        session = (Session) Util.getSession();
        session.beginTransaction();
        session.createSQLQuery("TRUNCATE TABLE " + User.class.getSimpleName()).executeUpdate();
        session.getTransaction().commit();


    }
}
