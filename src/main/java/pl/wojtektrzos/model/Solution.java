package pl.wojtektrzos.model;


import pl.wojtektrzos.DbUtil;

import java.sql.*;
import java.util.ArrayList;

public class Solution {

    private int id;
    private Date created;
    private Date updated;
    private String description;
    private int excerciseId;
    private int userId;

//    ------------=========konstruktory=============--------------------

    public Solution(Date created, Date updated, String description, int excerciseId, int userId) {
        this.created = created;
        this.updated = updated;
        this.description = description;
        this.excerciseId = excerciseId;
        this.userId = userId;
    }

    private Solution() {

    }

//    --------------==========gettery i setery============-----------------

    public int getId() {
        return id;
    }

    public Date getCreated() {
        return created;
    }

    public Date getUpdated() {
        return updated;
    }

    public String getDescription() {
        return description;
    }

    public int getExcerciseId() {
        return excerciseId;
    }

    public int getUserId() {
        return userId;
    }

    public Solution setUpdated(Date updated) {
        this.updated = updated;
        return this;
    }

    public Solution setDescription(String description) {
        this.description = description;
        return this;
    }

    //    ---------------===========ładowanie z bazy===========-----------------
    public static Solution findById(int id) throws SQLException {
        Connection conn = DbUtil.getConn();
        String querry = "SELECT id, created, updated, description, excercise_id, user_id FROM solutions WHERE id=?";
        PreparedStatement sql = conn.prepareStatement(querry);
        sql.setInt(1, id);
        ResultSet rs = sql.executeQuery();
        if (rs.next()) {
            Solution solution = new Solution();
            solution.created = rs.getDate("created");
            solution.updated = rs.getDate("updated");
            solution.id = rs.getInt("id");
            solution.description = rs.getString("description");
            solution.excerciseId = rs.getInt("excercise_id");
            solution.userId = rs.getInt("user_id");
            return solution;
        } else {
            return null;
        }

    }


    private static ArrayList<Solution> loadList(String query) throws SQLException {
        Connection conn = DbUtil.getConn();
        ArrayList<Solution> solutions = new ArrayList<>();
        PreparedStatement sql = conn.prepareStatement("SELECT id, created, updated, description, excercise_id, user_id FROM solutions;");
        ResultSet rs = sql.executeQuery();
        while (rs.next()) {
            Solution solution = new Solution();
            solution.created = rs.getDate("created");
            solution.updated = rs.getDate("updated");
            solution.id = rs.getInt("id");
            solution.description = rs.getString("description");
            solution.excerciseId = rs.getInt("excercise_id");
            solution.userId = rs.getInt("user_id");
            solutions.add(solution);
        }
        return solutions;

    }

    public static ArrayList<Solution> loadAllSolutions() throws SQLException {
        Connection conn = DbUtil.getConn();
        String query = "SELECT id, created, updated, description, excercise_id, user_id FROM solutions;";
        return loadList(query);
    }

    public static ArrayList<Solution> loadAllByUserId(int userId) throws SQLException {
        Connection conn = DbUtil.getConn();
        String query = "SELECT id, created, updated, description, excercise_id, user_id FROM solutions WHERE user_id=" + userId + ";";
        return loadList( query);
    }

    public static ArrayList<Solution> loadAllByExerciseId( int exId) throws SQLException {
        Connection conn = DbUtil.getConn();
        String query = "SELECT id, created, updated, description, excercise_id, user_id FROM solutions WHERE excercise_id=" + exId + " ORDER BY created;";
        return loadList( query);
    }


    //    -----------------------=====operacje na bazie danych=====--------------------
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
        String querry = "UPDATE solutions SET created = ?, updated = ?, description=?, excercise_id=?, user_id=? WHERE id=?;";
        PreparedStatement sql = conn.prepareStatement(querry);
        sql.setDate(1, created);
        sql.setDate(2, updated);
        sql.setString(3, description);
        sql.setInt(4, excerciseId);
        sql.setInt(5, userId);
        sql.executeUpdate();
    }

    private void insert() throws SQLException {
        Connection conn = DbUtil.getConn();
        String querry = "INSERT INTO solutions(created, updated, description, excercise_id, user_id) VALUES (?,?,?,?,?);";
        PreparedStatement sql = conn.prepareStatement(querry, new String[]{"id"});
        sql.setDate(1, created);
        sql.setDate(2, updated);
        sql.setString(3, description);
        sql.setInt(4, excerciseId);
        sql.setInt(5, userId);
        sql.executeUpdate();
        ResultSet rs = sql.getGeneratedKeys();
        if (rs.next()) {
            id = rs.getInt(1);
        }
    }

    public void deleteSolution() throws SQLException {
        Connection conn = DbUtil.getConn();
        if (id != 0) {
            PreparedStatement sql = conn.prepareStatement("DELETE FROM solutions WHERE id=" + this.id);
            sql.executeUpdate();
        }
    }

    @Override
    public String toString() {
        return "Solution{" +
                "id=" + id +
                ", created=" + created +
                ", updated=" + updated +
                ", description='" + description + '\'' +
                ", excerciseId=" + excerciseId +
                ", userId=" + userId +
                '}';
    }

}
