package vinkkeri.database;

import java.sql.SQLException;
import java.util.List;

public interface CourseDao {
    List<String> getCourses() throws SQLException;

    void addCourses(List<String> courses) throws SQLException;
}
