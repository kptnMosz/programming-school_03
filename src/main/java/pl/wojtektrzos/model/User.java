package pl.wojtektrzos.model;

import pl.wojtektrzos.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class User {

    private int id;
    private String username;
    private String email;
    private String password;
    private int groupId;


    public User(String username, String email, int groupId, String password) {
        this.username = username;
        this.email = email;
        this.groupId = groupId;
        setPassword(password);
    }

    private User() {

    }

    public int getGroupId() {
        return groupId;
    }

    public User setGroupId(int groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        //ToDo add lib to hash
        // this.password = password;
        System.out.println("zahaszuj mnie jeszcze!");
        this.password = password;
    }

    public int getId() {
        return id;
    }

    //    ---------------===========ładowanie z bazy===========-----------------
    public static User findById( int id) throws SQLException {
        Connection conn = DbUtil.getConn();
        String querry = "SELECT username, email, id, password, group_id FROM users WHERE id=?";
        PreparedStatement sql = conn.prepareStatement(querry);
        sql.setInt(1, id);
        ResultSet rs = sql.executeQuery();
        if (rs.next()) {
            User user = new User();
            user.username = rs.getString("username");
            user.email = rs.getString("email");
            user.id = rs.getInt("id");
            user.password = rs.getString("password");
            user.groupId = rs.getInt("group_id");
            return user;
        } else {
            return null;
        }

    }

    private static ArrayList<User> loadUsers(String query) throws SQLException {
        Connection conn = DbUtil.getConn();
        ArrayList<User> users = new ArrayList<>();
        PreparedStatement sql = conn.prepareStatement(query);
        ResultSet rs = sql.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.username = rs.getString("username");
            user.email = rs.getString("email");
            user.id = rs.getInt("id");
            user.password = rs.getString("password");
            user.groupId = rs.getInt("group_id");
            users.add(user);
        }
        return users;
    }

    public static ArrayList<User> loadAllUsers() throws SQLException {
        Connection conn = DbUtil.getConn();
        String query = "SELECT username, email, id, password, group_id FROM users;";
        return loadUsers( query);
    }

    public static ArrayList<User> loadAllByGrupId( int groupId) throws SQLException {
        Connection conn = DbUtil.getConn();
        String query = "SELECT username, email, id, password, group_id FROM users WHERE group_id = " +groupId+" ;";
        return loadUsers( query);
    }

    public static int emailCheck(String email) throws SQLException {
        Connection conn = DbUtil.getConn();
        PreparedStatement sql = conn.prepareStatement("SELECT id FROM users WHERE email = ?");
        sql.setString(1, email);
        ResultSet rs = sql.executeQuery();
        if(rs.next()){
            return rs.getInt(1);
        }
        return -1;
    }


    //    ---------------===========operacje na bazie danych===========-----------------
    public void deleteUser() throws SQLException {
        Connection conn = DbUtil.getConn();
        if (id > 0) {
            PreparedStatement sql = conn.prepareStatement("DELETE FROM users WHERE id=" + id);
            sql.executeUpdate();
            id = 0;
        }
    }

    public void saveToDb() throws SQLException {
        Connection conn = DbUtil.getConn();
        if (id == 0) {
            insert();
        } else {
            update();
        }
    }

    private void update() throws SQLException {
        Connection conn = DbUtil.getConn();
        String querry = "UPDATE users SET username = ?, email=?, password=?, group_id = ? WHERE id=?;";
        PreparedStatement sql = conn.prepareStatement(querry);
        sql.setString(1, username);
        sql.setString(2, email);
        sql.setString(3, password);
        sql.setInt(5, id);
        sql.setInt(4, groupId);
        sql.executeUpdate();
    }

    private void insert() throws SQLException {
        Connection conn = DbUtil.getConn();
        String querry = "INSERT INTO users(username, email, password, group_id) VALUES (?,?,?,?);";
        PreparedStatement sql = conn.prepareStatement(querry, new String[]{"id"});
        sql.setString(1, username);
        sql.setString(2, email);
        sql.setString(3, password);
        sql.setInt(4, groupId);
        sql.executeUpdate();
        ResultSet rs = sql.getGeneratedKeys();
        if (rs.next()) {
            id = rs.getInt(1);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", group='" + groupId + '\'' +
                '}';
    }

}
