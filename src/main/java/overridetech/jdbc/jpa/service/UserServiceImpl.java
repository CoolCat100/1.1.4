package overridetech.jdbc.jpa.service;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import overridetech.jdbc.jpa.dao.UserDaoHibernateImpl;
import overridetech.jdbc.jpa.dao.UserDaoJDBCImpl;
import overridetech.jdbc.jpa.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class UserServiceImpl implements UserService {
//    private UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
    private UserDaoHibernateImpl userDao = new UserDaoHibernateImpl();
//    private Connection connection;
    private SessionFactory sessionFactory;


    public UserServiceImpl() {
//        try {
//            userDao.setConnection(connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/User", "root",
//                    "root"));
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.put(Environment.URL, "jdbc:mysql://localhost:3306/User");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "root");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.HBM2DDL_AUTO, "update");
        sessionFactory = new Configuration().addAnnotatedClass(User.class).setProperties(properties)
                .buildSessionFactory();
        userDao.setSessionFactory(sessionFactory);
    }

    public void createUsersTable() {
        userDao.createUsersTable();
    }

    public void dropUsersTable() {
        userDao.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        userDao.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void cleanUsersTable() {
        userDao.cleanUsersTable();
    }
}
