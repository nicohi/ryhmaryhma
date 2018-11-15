
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
public class CourseDao {

    private String databaseAddress;

    /**
     * Database Access Object for Courses
     *
     * Standard path for this project is 'jdbc:sqlite:database.db';
     *
     * @param path : String path to database in hardrive
     */
    public CourseDao(String path) {
        this.databaseAddress = path;
    }

    /**
     * Retrieves all courses from database given in the constructor.
     *
     * @return
     * @throws SQLException
     */
    public List<String> getCourses() throws SQLException {
        List<String> courses = new ArrayList<>();

        Connection conn = DriverManager.getConnection(this.databaseAddress);
        Statement stmt = conn.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM Course");

        while (result.next()) {
            String course = result.getString("name");
            courses.add(course);
        }

        return courses;
    }

    /**
     * Inserts courses from the list into the database, doesn't insert
     * duplicates.
     *
     * @param tags
     * @throws SQLException
     */
    public void addCourses(List<String> courses) throws SQLException {
        List<String> oldCourses = this.getCourses();
        for (String c : courses) {
            if (!oldCourses.contains(c)) {
                this.addCourse(c);
            }
        }
    }

    private void addCourse(String course) throws SQLException {
        Connection conn = DriverManager.getConnection(this.databaseAddress);
        PreparedStatement stmt = conn.prepareStatement("INSERT INTO Course (name) VALUES (?)");
        stmt.setString(1, course);
        stmt.execute();
        stmt.close();
        conn.close();
    }

}
