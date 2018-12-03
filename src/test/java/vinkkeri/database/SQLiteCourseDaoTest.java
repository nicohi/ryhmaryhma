package vinkkeri.database;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author jpssilve
 */
public class SQLiteCourseDaoTest {

    private SQLiteCourseDao SQLiteCourseDao;

    @Before
    public void setUp() {
        this.SQLiteCourseDao = new SQLiteCourseDao("jdbc:sqlite:test.db");
    }

    // FIXME: dependaa populoidusta tietokannasta
    @Test
    public void gettingCoursesWorks1() {
        //FIXME
    }

    @Test
    public void addingCoursesWorks1() {
        List<String> courses = new ArrayList<>();
        courses.add("LaMa");
        courses.add("TiTo");
        courses.add("TilPe");
        this.SQLiteCourseDao.addCourses(courses);

        List<String> found = this.SQLiteCourseDao.getCourses();
        assertTrue(found.contains("TilPe"));
        assertTrue(found.contains("TiTo"));
        assertTrue(found.contains("LaMa"));
    }
}
