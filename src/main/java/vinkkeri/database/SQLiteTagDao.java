package vinkkeri.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Olli K. Kärki
 */
public class SQLiteTagDao implements TagDao {

    private String databaseAddress;

    /**
     * Database Access Object for Tags
     * <p>
     * Standard path for this project is 'jdbc:sqlite:database.db';
     *
     * @param path : String path to database in hardrive
     */
    public SQLiteTagDao(String path) {
        this.databaseAddress = path;
        DatabaseCheck.checkDatabase(databaseAddress);
    }

    /**
     * Retrieves all tags from database given in the constructor.
     *
     * @return list of tags
     */
    @Override
    public List<String> getTags() {
        List<String> tags = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(this.databaseAddress);
             ResultSet result = conn.createStatement().executeQuery("SELECT * FROM Tag")) {

            while (result.next()) {
                String course = result.getString("name");
                tags.add(course);
            }
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTagDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return tags;
    }

    /**
     * Inserts tags from the list into the database, doesn't insert duplicates.
     *
     * @param tags tags to be added
     */
    @Override
    public void addTags(List<String> tags) {
        List<String> oldTags = this.getTags();
        for (String t : tags) {
            if (!oldTags.contains(t)) {
                this.addTag(t);
            }
        }
    }

    private void addTag(String tag) {
        try (Connection conn = DriverManager.getConnection(this.databaseAddress);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Tag (name) VALUES (?)")) {

            stmt.setString(1, tag);
            stmt.execute();
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTagDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void removeTags(List<String> tags) {
        try (Connection conn = DriverManager.getConnection(this.databaseAddress)) {
            List<String> existingTags = getTagsList(conn);
            for (String tag : tags) {
                if (!existingTags.contains(tag)) {
                    removeTag(conn, tag);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteTagDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void removeTag(Connection conn, String name) {
        try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Tag WHERE name = (?)")) {
            stmt.setString(1, name);
            stmt.execute();
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLiteTagDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<String> getTagsList(Connection conn) {
        List<Integer> tags = new ArrayList<>();
        try (ResultSet result = conn.createStatement().executeQuery("SELECT * FROM TipTag")) {
            while (result.next()) {
                Integer id = result.getInt("tag");
                if (!tags.contains(id)) {
                    tags.add(id);
                }
            }
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return getTagNames(conn, tags);
    }

    private List<String> getTagNames(Connection conn, List<Integer> idList) {
        List<String> tags = new ArrayList<>();
        try (ResultSet result = conn.createStatement().executeQuery("SELECT * FROM Tag")) {
            while (result.next()) {
                String name = result.getString("name");
                Integer id = result.getInt("id");
                if (idList.contains(id)) {
                    tags.add(name);
                }
            }
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteTipDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tags;
    }
}
