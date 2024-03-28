package overridetech.jdbc.jpa;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import overridetech.jdbc.jpa.dao.UserDao;
import overridetech.jdbc.jpa.dao.UserDaoHibernateImpl;
import overridetech.jdbc.jpa.dao.UserDaoJDBCImpl;
import overridetech.jdbc.jpa.model.User;
import overridetech.jdbc.jpa.service.UserService;
import overridetech.jdbc.jpa.service.UserServiceImpl;
import overridetech.jdbc.jpa.util.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;


public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserDaoJDBCImpl userDao = new UserDaoJDBCImpl();
        Util.createConnection(userDao);
//        UserDaoHibernateImpl userDao = new UserDaoHibernateImpl();
//        Util.createHibernateConnection(userDao);

        userDao.createUsersTable();
        addAndPrintUser(userDao, "Петр", "Петров", (byte) 89);
        addAndPrintUser(userDao, "Иван", "Иванов", (byte) 13);
        addAndPrintUser(userDao, "Сидор", "Сидоров", (byte) 67);
        addAndPrintUser(userDao, "Швепс", "Яхз", (byte) 22);
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            System.out.println(user.toString());
        }
        userDao.cleanUsersTable();
        userDao.dropUsersTable();

//        userDao.getSessionFactory().close();

    }
    public static void addAndPrintUser(UserDao userDao, String name, String lastName, byte age){
        userDao.saveUser(name, lastName, age);
        System.out.println("User с именем - " + name + " добавлен в базу данных");
    }
}
