package vinkkeri.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class DatabaseCheck {

    public static boolean checkDatabase(String databaseAddress) {
        final String[] splits = databaseAddress.split(":");
        if (splits.length != 3 || !splits[0].equals("jdbc") || !splits[1].equals("sqlite")) {
            Logger.getGlobal().log(new LogRecord(Level.SEVERE,
                    "Invalid path for database. Was: " +
                            databaseAddress + ". Expected: jdbc:sqlite:filename.db"));
            return false;
        }
        final String filename = splits[2];
        File file = new File(filename);
        if (file.exists()) {
            return true;
        }
        file.delete();
        createTables(databaseAddress);
        return false;
    }

    private static void createTables(String databaseAddress) {
        try (Connection conn = DriverManager.getConnection(databaseAddress)) {
            final String tipTable = "CREATE TABLE IF NOT EXISTS Tip (\n" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "date date NOT NULL,\n" +
                    "title text,\n" +
                    "author text,\n" +
                    "summary text,\n" +
                    "url text,\n" +
                    "read text\n" +
                    ");";
            final String tagTable = "CREATE TABLE IF NOT EXISTS Tag (\n" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "name text NOT NULL UNIQUE\n" +
                    ");";
            final String courseTable = "CREATE TABLE IF NOT EXISTS Course (\n" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "name text NOT NULL UNIQUE\n" +
                    ");";
            final String tiptagTable = "CREATE TABLE IF NOT EXISTS TipTag (\n" +
                    "tip int NOT NULL,\n" +
                    "tag int NOT NULL,\n" +
                    "FOREIGN KEY (tip) REFERENCES Tip(id),\n" +
                    "FOREIGN KEY (tag) REFERENCES Tag(id)\n" +
                    ");";
            final String relcourseTable = "CREATE TABLE IF NOT EXISTS RelCourse (\n" +
                    "tip int NOT NULL,\n" +
                    "course int NOT NULL,\n" +
                    "FOREIGN KEY (tip) REFERENCES Tip(id),\n" +
                    "FOREIGN KEY (course) REFERENCES Course(id)\n" +
                    ");";
            final String reqcourseTable = "CREATE TABLE IF NOT EXISTS ReqCourse (\n" +
                    "tip int NOT NULL,\n" +
                    "course int NOT NULL,\n" +
                    "FOREIGN KEY (tip) REFERENCES Tip(id),\n" +
                    "FOREIGN KEY (course) REFERENCES Course(id)\n" +
                    ");";
            final String[] queries = {tipTable, tagTable, courseTable, tiptagTable, relcourseTable, reqcourseTable};

            for (String query : queries) {
                Statement statement = conn.createStatement();
                statement.execute(query);
                statement.close();
            }

        } catch (SQLException e) {
            System.out.println("Failed to create sqlite database. Make sure you have write access on disk");
        }
    }
}
