package jm.task.core.jdbc.dao;

import com.mysql.jdbc.Connection;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection con = (Connection) Util.getConnection()) {
            String sql = "CREATE TABLE users " +
                    "(id INTEGER not NULL, " +
                    " name VARCHAR(45), " +
                    " lastName VARCHAR(45), " +
                    " age TINYINT, " +
                    " PRIMARY KEY ( id ))" +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
            con.createStatement().execute(sql);
        }catch (SQLException e){}


    }

    public void dropUsersTable() {
        try (Connection con = (Connection) Util.getConnection()){
            con.createStatement().execute("DROP TABLE IF EXISTS `users`");

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection con = (Connection) Util.getConnection()){
            PreparedStatement pst = con.prepareStatement("INSERT INTO `users` (`name`, `lastName`, `age`)" +
                    "VALUES (?, ?, ?);");
            pst.setString(1, name);
            pst.setString(2, lastName);
            pst.setByte(3, age);
            pst.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void removeUserById(long id) {
        try (Connection con = (Connection) Util.getConnection()){
            PreparedStatement pst = con.prepareStatement("DELETE FROM users WHERE ID=?");
            pst.setLong(1, id);
            pst.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> us = new ArrayList<>();
        try (Connection con = (Connection) Util.getConnection()){
            ResultSet rs = con.createStatement().executeQuery("SELECT*FROM `users`");
            while (rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                us.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return us;
    }

    public void cleanUsersTable() {
        try (Connection con = (Connection) Util.getConnection()){
            con.createStatement().execute("TRUNCATE TABLE `users`");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
