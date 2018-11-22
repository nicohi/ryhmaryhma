package vinkkeri.database;

import java.util.List;

public interface CourseDao {

    List<String> getCourses();

    void addCourses(List<String> courses);
}
