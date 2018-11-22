package vinkkeri.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Olli K. Kärki
 */
public class SQLiteTagDao implements TagDao {

    private String databaseAddress;

    /**
     * Database Access Object for Tags
     *
     * Standard path for this project is 'jdbc:sqlite:database.db';
     *
     * @param path : String path to database in hardrive
     */
    public SQLiteTagDao(String path) {
        this.databaseAddress = path;
    }

    /**
     * Retrieves all tags from database given in the constructor.
     *
     * @return
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
     * @param tags
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
}
