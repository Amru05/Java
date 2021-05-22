package jm.task.core.jdbc.util;

import com.mysql.jdbc.Statement;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.metamodel.Metadata;
import org.hibernate.metamodel.MetadataSources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class Util {
    // реализуйте настройку соеденения с БД
    private static SessionFactory sessionFactory;
    private static MetadataSources metadata;

    private static final String dbName ="shema" ;
    private static final String USERNAME ="root";
    private static final String PASSWORD ="5250" ;
    static boolean useSSL = false;


    public static Connection getConnection() throws SQLException {
        Statement statement = null;
        java.sql.Connection con = null;
        statement = (Statement) con.createStatement();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionURL = "jdbc:mysql://localhost:3306/" + dbName + useSSL;
            con = DriverManager.getConnection(connectionURL, USERNAME, PASSWORD);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error to connection with DB");
            System.exit(0);
        }

        return con;
    }

    public static SessionFactory getSession(){
        if (sessionFactory == null) {
            String dbName = "shema";
            String USERNAME = "root";
            String PASSWORD = "5250";


            Map<String, String> setmap = new HashMap<>();
            setmap.put("connection.driver_Class", "com.mysql.cj.jdbc.Driver");
            setmap.put("dialect", "org.hibernate.dialect.MySQL57InnoDB");
            setmap.put("hibernate.connection.url", "jdbc:mysql://localhost:3306:" + dbName);
            setmap.put("hibernate.connection.useSSL", String.valueOf(false));
            setmap.put("hibernate.connection.userName", USERNAME);
            setmap.put("hibernate.connection.password", PASSWORD);
            setmap.put("hibernate.current_session_context_class", "thread");
            setmap.put("hibernate.hbm2ddl.auto", "update");
            setmap.put("hibernate.show_sql", "true");
            setmap.put("hibernate.format_sql", "true");

            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(setmap).build();

            try {
                metadata = new MetadataSources(serviceRegistry);
            } catch (Exception e) {
                StandardServiceRegistryBuilder.destroy(serviceRegistry);
                throw e;
            }

            metadata.addAnnotatedClass(User.class);
            sessionFactory = metadata.buildMetadata().buildSessionFactory();
        }
        return sessionFactory;
    }

}
