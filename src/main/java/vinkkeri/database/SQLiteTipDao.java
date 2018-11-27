package vinkkeri.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import vinkkeri.objects.Tip;

/**
 * @author Olli K. Kärki
 */
public class SQLiteTipDao implements TipDao {

    private String databaseAddress;

    /**
     * Database Access Object for Tip table and other immediately related
     * tables.
     * <p>
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
     */
    @Override
    public List<Tip> getTips() {

        List<Tip> tips = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(this.databaseAddress);
                ResultSet result = conn.createStatement().executeQuery("SELECT * FROM Tip ORDER BY date DESC")) {

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
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tips;
    }

    /**
     * Support method for getTips()
     *
     * @param conn
     * @param id
     * @return List of String
     */
    private List<String> getTags(Connection conn, int id) {
        List<String> tags = new ArrayList<>();
        try (ResultSet result_tags = conn.createStatement().
                executeQuery("SELECT * FROM TipTag LEFT JOIN Tag ON TipTag.tag = Tag.id WHERE TipTag.tip = " + id)) {

            while (result_tags.next()) {
                String tag = result_tags.getString("name");
                tags.add(tag);
            }
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tags;
    }

    /**
     * Support method for getTips()
     *
     * @param conn
     * @param id
     * @return List of String
     */
    private List<String> getRequiredCourses(Connection conn, int id) {
        List<String> reqC = new ArrayList<>();
        try (ResultSet result_reqC = conn.createStatement().
                executeQuery("SELECT * FROM ReqCourse LEFT JOIN Course ON ReqCourse.course = Course.id WHERE ReqCourse.tip = " + id)) {

            while (result_reqC.next()) {
                String course = result_reqC.getString("name");
                reqC.add(course);
            }
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return reqC;
    }

    /**
     * Support method for getTips()
     *
     * @param conn
     * @param id
     * @return List of String
     */
    private List<String> getRelatedCourses(Connection conn, int id) {
        List<String> relC = new ArrayList<>();
        try (ResultSet result_relC = conn.createStatement().executeQuery("SELECT * FROM RelCourse LEFT JOIN Course ON RelCourse.course = Course.id WHERE RelCourse.tip = " + id)) {

            while (result_relC.next()) {
                String course = result_relC.getString("name");
                relC.add(course);
            }
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return relC;
    }

    // Mark as Read -------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Override
    public void markReadValue(String name, boolean read) {
        try (Connection conn = DriverManager.getConnection(this.databaseAddress);
                PreparedStatement stmt = conn.prepareStatement("UPDATE Tip SET READ = ? WHERE title = ?;")) {

            stmt.setBoolean(1, read);
            stmt.setString(2, name);
            stmt.execute();
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // insertTip() --------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Writes given Tip into the database. Use TagDao addTags() before calling
     * this!
     *
     * @param tip
     */
    @Override
    public void insertTip(Tip tip) {

        java.util.Date uDate = new java.util.Date();
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        String date = sDate.toString();

        boolean read = false;
        List<String> tags = tip.getTags();
        List<String> related = tip.getRelC();
        List<String> required = tip.getReqC();

        try (Connection conn = DriverManager.getConnection(this.databaseAddress)) {
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
            // If id == -1 then there was an error in database connection
            if (id == -1) {
                return;
            }

            this.addRelCourseConnections(conn, related, id);
            this.addReqCourseConnections(conn, required, id);
            this.addTagConnections(conn, tags, id);
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void remove(int id) {
        try (Connection conn = DriverManager.getConnection(this.databaseAddress); PreparedStatement stmt = conn.prepareStatement("DELETE FROM Tip WHERE id = ?")) {
            stmt.setInt(1, id);
            stmt.execute();

            this.removeRelCourseConnections(conn, id);
            this.removeReqCourseConnections(conn, id);
            this.removeTagConnections(conn, id);
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int getNewestID() {
        int idErrorIndicator = -1;
        try (Connection conn = DriverManager.getConnection(this.databaseAddress);
                ResultSet result = conn.createStatement().executeQuery("SELECT MAX(id) FROM Tip")) {

            return result.getInt("MAX(id)");
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
            return idErrorIndicator;
        }
    }

    private void addRelCourseConnections(Connection conn, List<String> courses, int tipID) {
        try {
            Map<String, Integer> courseID = this.getCourseIdTable(conn);
            for (String course : courses) {
                try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO RelCourse VALUES (?, ?)")) {
                    stmt.setInt(1, tipID);
                    stmt.setInt(2, courseID.get(course));
                    stmt.execute();
                }
            }
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void removeRelCourseConnections(Connection conn, int tipID) {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM RelCourse WHERE tip = ?")) {
            stmt.setInt(1, tipID);
            stmt.execute();
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addReqCourseConnections(Connection conn, List<String> courses, int tipID) {
        try {
            Map<String, Integer> courseID = this.getCourseIdTable(conn);

            for (String course : courses) {
                try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO ReqCourse VALUES (?, ?)")) {
                    stmt.setInt(1, tipID);
                    stmt.setInt(2, courseID.get(course));
                    stmt.execute();
                }
            }
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void removeReqCourseConnections(Connection conn, int tipID) {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM ReqCourse WHERE tip = ?")) {
            stmt.setInt(1, tipID);
            stmt.execute();
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addTagConnections(Connection conn, List<String> tags, int tipID) {
        try {
            Map<String, Integer> tagIdTable = this.getTagIdTable(conn);

            for (String tag : tags) {
                try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO TipTag VALUES (?, ?)")) {
                    stmt.setInt(1, tipID);
                    stmt.setInt(2, tagIdTable.get(tag));
                    stmt.execute();
                }
            }
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void removeTagConnections(Connection conn, int tipID) {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM TipTag WHERE tip = ?")) {
            stmt.setInt(1, tipID);
            stmt.execute();
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Map<String, Integer> getCourseIdTable(Connection conn) {
        Map<String, Integer> idTable = new HashMap<>();
        try (ResultSet result = conn.createStatement().executeQuery("SELECT * FROM Course")) {

            while (result.next()) {
                String name = result.getString("name");
                int id = result.getInt("id");
                idTable.put(name, id);
            }
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return idTable;
    }

    private Map<String, Integer> getTagIdTable(Connection conn) {
        Map<String, Integer> idTable = new HashMap<>();
        try (ResultSet result = conn.createStatement().executeQuery("SELECT * FROM Tag")) {
            while (result.next()) {
                String name = result.getString("name");
                int id = result.getInt("id");
                idTable.put(name, id);
            }
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return idTable;
    }
}
