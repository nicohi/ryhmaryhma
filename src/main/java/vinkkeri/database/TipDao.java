/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinkkeri.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Olli K. Kärki
 */
public class TipDao {

    private String databaseAddress;

    /**
     * Database Access Object for Tip table and other immediately related
     * tables.
     * 
     * @param path : String path to database in hardrive
     */
    public TipDao(String path) {
        this.databaseAddress = path;
    }

    /**
     * SQL Query into database that is given to TipDao object in constructor.
     * Returns a java.util.List of Tip objects.
     * 
     * @return List of Tip
     * @throws Exception 
     */
    public List<Object> getTips() throws Exception {

        // Replace Object from List<Object> in return type with Tip or something
        // When it becomes available. Same with tips list below.
        Connection conn = DriverManager.getConnection(this.databaseAddress);
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM Tip ORDER BY date DESC");

        List<Object> tips = new ArrayList<>();

        while (result.next()) {

            // Use these to create tip object
            int id = result.getInt("id");
            String date = result.getString("date");
            String type = result.getString("type");
            String title = result.getString("title");
            String author = result.getString("author");
            String summary = result.getString("summary");
            String isbn = result.getString("isbn");
            String url = result.getString("url");
            boolean read = result.getBoolean("read");

            List<String> tags = this.getTags(conn, id); //            tags for the object.
            List<String> reqC = this.getRequiredCourses(conn, id); // required courses for the object.
            List<String> relC = this.getRelatedCourses(conn, id); //  related courses for the object.
            
            Object tip = new Object(); // This is supposed to be the tip object.

            tips.add(tip);
        }

        result.close();
        conn.close();

        return tips;
    }

    /**
     * Support method for getTips()
     * 
     * @param conn
     * @param id
     * @return List of String
     * @throws SQLException 
     */
    private List<String> getTags(Connection conn, int id) throws SQLException {
        Statement stmt_tags = conn.createStatement();
        ResultSet result_tags = stmt_tags.executeQuery("SELECT * FROM TipTag LEFT JOIN Tag ON TipTag.tag = Tag.id WHERE TipTag.tip = " + id);

        List<String> tags = new ArrayList<>();

        while (result_tags.next()) {
            String tag = result_tags.getString("name");
            tags.add(tag);
        }

        result_tags.close();

        return tags;
    }

    /**
     * Support method for getTips()
     * 
     * @param conn
     * @param id
     * @return List of String
     * @throws SQLException 
     */
    private List<String> getRequiredCourses(Connection conn, int id) throws SQLException {
        Statement stmt_reqC = conn.createStatement();
        ResultSet result_reqC = stmt_reqC.executeQuery("SELECT * FROM ReqCourse LEFT JOIN Course ON ReqCourse.course = Course.id WHERE ReqCourse.tip = " + id);

        List<String> reqC = new ArrayList<>();

        while (result_reqC.next()) {
            String course = result_reqC.getString("name");
            reqC.add(course);
        }

        result_reqC.close();

        return reqC;
    }

    /**
     * Support method for getTips()
     * 
     * @param conn
     * @param id
     * @return List of String
     * @throws SQLException 
     */
    private List<String> getRelatedCourses(Connection conn, int id) throws SQLException {
        Statement stmt_relC = conn.createStatement();
        ResultSet result_relC = stmt_relC.executeQuery("SELECT * FROM RelCourse LEFT JOIN Course ON RelCourse.course = Course.id WHERE RelCourse.tip = " + id);

        List<String> relC = new ArrayList<>();

        while (result_relC.next()) {
            String course = result_relC.getString("name");
            relC.add(course);
        }

        result_relC.close();
        
        return relC;
    }

}
