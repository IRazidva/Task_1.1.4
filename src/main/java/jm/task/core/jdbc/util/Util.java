package jm.task.core.jdbc.util;

//import java.lang.module.Configuration;
import org.hibernate.cfg.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

public class Util {
    static final String user = "dbadmin";
    static final String password = "1234";
    static final String url = "jdbc:mysql://localhost:3306/mydb?useSSL=false";

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection) throws SQLException {
        try{
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:3306/mydb?useSSL=false");
                settings.put(Environment.USER, "dbadmin");
                settings.put(Environment.PASS, "1234");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                //settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
