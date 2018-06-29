package pl.wojtektrzos.model;

import pl.wojtektrzos.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Excercise {

    private String title;
    private String description;
    private int id;

    private Excercise() {

    }

    public Excercise setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Excercise setDescription(String description) {
        this.description = description;
        return this;
    }


    public Excercise(String name, String description) {
        this.title = name;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public static Excercise findById(int id) throws SQLException {
        Connection conn = DbUtil.getConn();
        String querry = "SELECT id, title, description FROM excercises WHERE id=?";
        PreparedStatement sql = conn.prepareStatement(querry);
        sql.setInt(1, id);
        ResultSet rs = sql.executeQuery();
        if (rs.next()) {
            Excercise exc = new Excercise();
            exc.title = rs.getString("title");
            exc.description = rs.getString("description");
            exc.id = rs.getInt("id");
            return exc;
        } else {
            return null;
        }
    }

    public static ArrayList<Excercise> loadAll() throws SQLException {
        Connection conn = DbUtil.getConn();
        ArrayList<Excercise> excercises = new ArrayList<>();
        PreparedStatement sql = conn.prepareStatement("SELECT * FROM excercises;");
        ResultSet rs = sql.executeQuery();
        while (rs.next()) {
            Excercise excercise = new Excercise();
            excercise.title = rs.getString("title");
            excercise.description = rs.getString("description");
            excercises.add(excercise);
        }
        return excercises;
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
        String querry = "UPDATE excercises SET title = ?, description = ? WHERE id=?;";
        PreparedStatement sql = conn.prepareStatement(querry);
        sql.setString(1, title);
        sql.setString(2, description);
        sql.setInt(3, this.id);
        sql.executeUpdate();
    }

    private void insert() throws SQLException {
        Connection conn = DbUtil.getConn();
        String querry = "INSERT INTO excercises(title, description) VALUES (?,?);";
        PreparedStatement sql = conn.prepareStatement(querry, new String[]{"id"});
        sql.setString(1, title);
        sql.setString(2, description);
        sql.executeUpdate();
        ResultSet rs = sql.getGeneratedKeys();
        if (rs.next()) {
            id = rs.getInt(1);
        }
    }

    public void deleteExcercise() throws SQLException {
        Connection conn = DbUtil.getConn();
        if(id!=0){
            PreparedStatement sql = conn.prepareStatement("DELETE FROM excercises WHERE id=" + this.id);
            sql.executeUpdate();
        }
    }

    @Override
    public String toString() {
        return "Excercise{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
