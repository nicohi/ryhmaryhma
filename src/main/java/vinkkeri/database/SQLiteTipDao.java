package vinkkeri.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import vinkkeri.objects.Tip;

/**
 *
 * @author Olli K. Kärki
 */
public class SQLiteTipDao implements TipDao {

    private String databaseAddress;

    /**
     * Database Access Object for Tip table and other immediately related
     * tables.
     *
     * Standard path for this project is 'jdbc:sqlite:database.db';
     *
     * @param path : String path to database in hardrive
     */
    public SQLiteTipDao(String path) {
        this.databaseAddress = path;
    }

    // getTips() ------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * SQL Query into database that is given to SQLiteTipDao object in
     * constructor. Returns a java.util.List of Tip objects.
     *
     * @return List of Tip
     * @throws Exception
     */
    @Override
    public List<Tip> getTips() throws Exception {

        Connection conn = DriverManager.getConnection(this.databaseAddress);
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM Tip ORDER BY date DESC");

        List<Tip> tips = new ArrayList<>();

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

            Tip tip = new Tip(id, date, type, title, author, summary, isbn, url, read);
            tip.setTags(tags);
            tip.setRelC(relC);
            tip.setReqC(reqC);

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

    // Mark as Read -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void markReadValue(String name, boolean read) throws SQLException {
        Connection conn = DriverManager.getConnection(this.databaseAddress);
        PreparedStatement stmt = conn.prepareStatement("UPDATE Tip SET READ = ? WHERE title = ?;");
        stmt.setBoolean(1, read);
        stmt.setString(2, name);
        stmt.execute();
        stmt.close();
        conn.close();
    }

    // insertTip() --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Writes given Tip into the database.
     *
     * @throws SQLException
     */
    @Override
    public void insertTip(Tip tip) throws SQLException {

        java.util.Date uDate = new java.util.Date();
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        String date = sDate.toString();

        boolean read = false;
        List<String> tags = tip.getTags();
        List<String> relC = tip.getRelC();
        List<String> reqC = tip.getReqC();

        Connection conn = DriverManager.getConnection(this.databaseAddress);
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Tip (date, type, title, author, summary, isbn, url, read) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setString(1, date);
        stmt.setString(2, tip.getType());
        stmt.setString(3, tip.getTitle());
        stmt.setString(4, tip.getAuthor());
        stmt.setString(5, tip.getSummary());
        stmt.setString(6, tip.getIsbn());
        stmt.setString(7, tip.getUrl());
        stmt.setBoolean(8, read);

        stmt.execute();

        int id = this.getNewestID();

        this.addRelCourseConnections(conn, reqC, id);
        this.addReqCourseConnections(conn, relC, id);
        this.addTagConnections(conn, tags, id);

        conn.close();
    }

    @Override
    public void remove(int id) throws SQLException {
        Connection conn = DriverManager.getConnection(this.databaseAddress);
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Tip WHERE id = ?");
        stmt.setInt(1, id);
        stmt.execute();
        stmt.close();

        this.removeRelCourseConnections(conn, id);
        this.removeReqCourseConnections(conn, id);
        this.removeTagConnections(conn, id);

        conn.close();
    }

    @Override
    public int getNewestID() throws SQLException {
        Connection conn = DriverManager.getConnection(this.databaseAddress);
        Statement stmt_maxID = conn.createStatement();
        ResultSet result = stmt_maxID.executeQuery("SELECT MAX(id) FROM Tip");
        int id = result.getInt("MAX(id)");
        result.close();
        stmt_maxID.close();
        return id;
    }

    private void addRelCourseConnections(Connection conn, List<String> courses, int tipID) throws SQLException {
        Map<String, Integer> courseID = this.getCourseIdTable(conn);

        for (String course : courses) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO RelCourse VALUES (?, ?)");
            stmt.setInt(1, tipID);
            stmt.setInt(2, courseID.get(course));
            stmt.execute();
            stmt.close();
        }
    }

    private void removeRelCourseConnections(Connection conn, int tipID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM RelCourse WHERE tip = ?");
        stmt.setInt(1, tipID);
        stmt.execute();
        stmt.close();
    }

    private void addReqCourseConnections(Connection conn, List<String> courses, int tipID) throws SQLException {
        Map<String, Integer> courseID = this.getCourseIdTable(conn);

        for (String course : courses) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO ReqCourse VALUES (?, ?)");
            stmt.setInt(1, tipID);
            stmt.setInt(2, courseID.get(course));
            stmt.execute();
            stmt.close();
        }
    }

    private void removeReqCourseConnections(Connection conn, int tipID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM ReqCourse WHERE tip = ?");
        stmt.setInt(1, tipID);
        stmt.execute();
        stmt.close();
    }

    private void addTagConnections(Connection conn, List<String> tags, int tipID) throws SQLException {
        Map<String, Integer> courseID = this.getTagIdTable(conn);

        for (String course : tags) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO TipTag VALUES (?, ?)");
            stmt.setInt(1, tipID);
            stmt.setInt(2, courseID.get(course));
            stmt.execute();
            stmt.close();
        }
    }

    private void removeTagConnections(Connection conn, int tipID) throws SQLException {
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM TipTag WHERE tip = ?");
        stmt.setInt(1, tipID);
        stmt.execute();
        stmt.close();
    }

    private Map<String, Integer> getCourseIdTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM Course");

        Map<String, Integer> idTable = new HashMap<>();

        while (result.next()) {
            String name = result.getString("name");
            int id = result.getInt("id");
            idTable.put(name, id);
        }
        
        result.close();
        stmt.close();

        return idTable;
    }

    private Map<String, Integer> getTagIdTable(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM Tag");

        Map<String, Integer> idTable = new HashMap<>();

        while (result.next()) {
            String name = result.getString("name");
            int id = result.getInt("id");
            idTable.put(name, id);
        }

        result.close();
        stmt.close();

        return idTable;
    }
}
