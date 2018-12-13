package vinkkeri.database;

import vinkkeri.objects.Tip;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        DatabaseCheck.checkDatabase(databaseAddress);
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
                String title = result.getString("title");
                String author = result.getString("author");
                String summary = result.getString("summary");
                String url = result.getString("url");
                String read = result.getString("read");

                List<String> tags = this.getTags(conn, id); //            tags for the object.
                List<String> reqC = this.getRequiredCourses(conn, id); // required courses for the object.
                List<String> relC = this.getRelatedCourses(conn, id); //  related courses for the object.

                Tip tip = new Tip(id, date, title, author, summary, url, read);
                tip.setTags(tags);
                tip.setRelatedCourses(relC);
                tip.setRequiredCourses(reqC);

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
    public void markReadValue(int id, String read) {
        try (Connection conn = DriverManager.getConnection(this.databaseAddress);
             PreparedStatement stmt = conn.prepareStatement("UPDATE Tip SET READ = ? WHERE id = ?;")) {

            stmt.setString(1, read);
            stmt.setInt(2, id);
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

        if (tip == null) {
            System.out.println("Tried to insert a null tip");
            return;
        }

        java.util.Date uDate = new java.util.Date();
        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
        String date = sDate.toString();

        List<String> tags = tip.getTags();
        List<String> related = tip.getRelatedCourses();
        List<String> required = tip.getRequiredCourses();

        try (Connection conn = DriverManager.getConnection(this.databaseAddress)) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Tip (date, title, author, summary, url, read) VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, date);
            stmt.setString(2, tip.getTitle());
            stmt.setString(3, tip.getAuthor());
            stmt.setString(4, tip.getSummary());
            stmt.setString(5, tip.getUrl());
            stmt.setString(6, tip.isRead());

            stmt.execute();

            int id = this.getNewestID();
            // If id == -1 then there was an error in database connection
            if (id == -1) {
                return;
            }

            this.addRelatedCourseConnections(conn, related, id);
            this.addRequiredCourseConnections(conn, required, id);
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

            this.removeRelatedCourseConnections(conn, id);
            this.removeRequiredCourseConnections(conn, id);
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

    private void addRelatedCourseConnections(Connection conn, List<String> courses, int tipID) {
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

    private void removeRelatedCourseConnections(Connection conn, int tipID) {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM RelCourse WHERE tip = ?")) {
            stmt.setInt(1, tipID);
            stmt.execute();
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addRequiredCourseConnections(Connection conn, List<String> courses, int tipID) {
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

    private void removeRequiredCourseConnections(Connection conn, int tipID) {
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

    // --- Update method ---------------------------------------------------------------------

    /**
     * Call TagDao addTags for Tip's new tag list before this method.
     * And removeTags for Tips old tagList after this method.
     *
     * @param tip tip to be updated
     */
    @Override
    public void updateTip(Tip tip) {
        if (tip == null) {
            System.out.println("Tried to update a null tip");
            return;
        }
        try (Connection conn = DriverManager.getConnection(this.databaseAddress);
             PreparedStatement stmt = conn.prepareStatement("UPDATE Tip SET date = ?, title = ?, author = ?, summary = ?, url = ?, read = ? WHERE id = ?")) {
            stmt.setString(1, tip.getDate());
            stmt.setString(2, tip.getTitle());
            stmt.setString(3, tip.getAuthor());
            stmt.setString(4, tip.getSummary());
            stmt.setString(5, tip.getUrl());
            String read = tip.isRead();
            if (read.equals("false")) {
                read = "";
            }
            stmt.setString(6, read);
            stmt.setInt(7, tip.getId());

            removeTagConnections(conn, tip.getId());

            stmt.execute();

            this.addTagConnections(conn, tip.getTags(), tip.getId());

        } catch (SQLException ex) {
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
