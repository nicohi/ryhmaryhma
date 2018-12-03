package vinkkeri.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Olli K. Kärki
 */
public class SQLiteCourseDao implements CourseDao {

    private String databaseAddress;

    /**
     * Database Access Object for Courses
     * Standard path for this project is 'jdbc:sqlite:database.db';
     *
     * @param path : String path to database in hardrive
     */
    public SQLiteCourseDao(String path) {
        this.databaseAddress = path;
        DatabaseCheck.checkDatabase(databaseAddress);
    }

    /**
     * Retrieves all courses from database given in the constructor.
     *
     * @return
     */
    @Override
    public List<String> getCourses() {
        List<String> courses = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(this.databaseAddress);
             ResultSet result = conn.createStatement().executeQuery("SELECT * FROM Course")) {

            while (result.next()) {
                String course = result.getString("name");
                courses.add(course);
            }
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteCourseDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return courses;
    }

    /**
     * Inserts courses from the list into the database, doesn't insert
     * duplicates.
     *
     * @param courses
     */
    @Override
    public void addCourses(List<String> courses) {
        List<String> oldCourses = this.getCourses();
        for (String c : courses) {
            if (!oldCourses.contains(c)) {
                this.addCourse(c);
            }
        }
    }

    private void addCourse(String course) {
        try (Connection conn = DriverManager.getConnection(this.databaseAddress);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Course (name) VALUES (?)")) {

            stmt.setString(1, course);
            stmt.execute();
        } catch (SQLException ex) {
            // Add desired action here
            Logger.getLogger(SQLiteCourseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
