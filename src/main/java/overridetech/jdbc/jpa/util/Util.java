package overridetech.jdbc.jpa.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import overridetech.jdbc.jpa.dao.UserDaoHibernateImpl;
import overridetech.jdbc.jpa.dao.UserDaoJDBCImpl;
import overridetech.jdbc.jpa.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    public static void createConnection(UserDaoJDBCImpl userDao){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/User","root",
                    "root");
            userDao.setConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void createHibernateConnection(UserDaoHibernateImpl userDao){
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.put(Environment.URL, "jdbc:mysql://localhost:3306/User");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "root");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.HBM2DDL_AUTO, "none");
        SessionFactory sessionFactory = new Configuration().addAnnotatedClass(User.class).setProperties(properties)
                .buildSessionFactory();
        userDao.setSessionFactory(sessionFactory);
    }
}
