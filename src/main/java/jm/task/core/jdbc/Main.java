package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.*;
import java.util.*;

public class Main {


    public static void main(String[] args) throws SQLException {
        // реализуйте алгоритм здесь
        UserService us = new UserServiceImpl();

        us.createUsersTable();
        us.saveUser("Ivan", "Ivanov", (byte) 15);
        us.saveUser("Peter", "Petrov", (byte) 16);
        us.saveUser("Sidor", "Sidorov", (byte) 17);
        us.saveUser("Maga", "Magomedov", (byte) 18);
        List<User> users = us.getAllUsers();
        for (User user:users){
            System.out.println(user);
        }
        us.removeUserById(1);
        us.cleanUsersTable();
        us.dropUsersTable();
    }


}
