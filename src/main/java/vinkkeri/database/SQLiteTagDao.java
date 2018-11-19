
package vinkkeri.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
     * @throws SQLException 
     */
    @Override
    public List<String> getTags() throws SQLException {
        List<String> tags = new ArrayList<>();

        Connection conn = DriverManager.getConnection(this.databaseAddress);
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM Tag");

        while (result.next()) {
            String course = result.getString("name");
            tags.add(course);
        }

        return tags;
    }

    /**
     * Inserts tags from the list into the database, doesn't insert duplicates.
     * 
     * @param tags
     * @throws SQLException 
     */
    @Override
    public void addTags(List<String> tags) throws SQLException {
        List<String> oldTags = this.getTags();
        for (String t : tags) {
            if (!oldTags.contains(t)) {
                this.addTag(t);
            }
        }
    }

    private void addTag(String tag) throws SQLException {
        Connection conn = DriverManager.getConnection(this.databaseAddress);
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Tag (name) VALUES (?)");
        stmt.setString(1, tag);
        stmt.execute();
        stmt.close();
        conn.close();
    }
}
