package vinkkeri.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class DatabaseCheckTest {

    @Before
    public void setUp() {
    }

    @Test
    public void existingDatabaseReturnsTrue() {
        File db = new File("test.db");
        try {
            db.createNewFile();
        } catch (IOException e) {
            fail("no write access");
        }
        assertTrue(DatabaseCheck.checkDatabase("jdbc:sqlite:test.db"));
        db.delete();
    }

    @Test
    public void creatingNewDatabaseWithTablesWorks() {
        File file = new File("test.db");
        if (file.exists()) {
            file.delete();
        }
        DatabaseCheck.checkDatabase("jdbc:sqlite:test.db");
        file = new File("test.db");
        assertTrue(file.exists());
        checkTables();
    }

    public void checkTables() {
        HashSet<String> tables = new HashSet<>();
        String[] tableNames = {"Course", "RelCourse", "ReqCourse", "Tag", "Tip", "TipTag", "sqlite_sequence"};
        tables.addAll(Arrays.asList(tableNames));
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, "%", null);
            while (rs.next()) {
                final int COLUMN_NAME_INDEX = 3;
                assertTrue(tables.contains(rs.getString(COLUMN_NAME_INDEX)));
            }
        } catch (Exception e) {
            fail("Sql exception");
        }
    }

    @After
    public void tearDown() {
        File db = new File("test.db");
        if (db.exists()) {
            db.delete();
        }
    }
}
