package vinkkeri.database;

import java.io.File;
import java.sql.*;

public class DatabaseCheck {

    public static void checkDatabase(String databaseAddress) {
        File file = new File("database.db");
        if (file.exists()) {
            return;
        }
        file.delete();
        System.out.println("Creating missing database");
        createTables(databaseAddress);
    }

    private static void createTables(String databaseAddress) {
        System.out.println("Creating missing tables");
        try (Connection conn = DriverManager.getConnection(databaseAddress)) {
            String tipTable = "CREATE TABLE IF NOT EXISTS Tip (\n" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "date date NOT NULL,\n" +
                    "type text,\n" +
                    "title text,\n" +
                    "author text,\n" +
                    "summary text,\n" +
                    "isbn text,\n" +
                    "url text,\n" +
                    "read boolean\n" +
                    ");";
            String tagTable = "CREATE TABLE IF NOT EXISTS Tag (\n" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "name text NOT NULL UNIQUE\n" +
                    ");";
            String courseTable = "CREATE TABLE IF NOT EXISTS Course (\n" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "name text NOT NULL UNIQUE\n" +
                    ");";
            String tiptagTable = "CREATE TABLE IF NOT EXISTS TipTag (\n" +
                    "tip int NOT NULL,\n" +
                    "tag int NOT NULL,\n" +
                    "FOREIGN KEY (tip) REFERENCES Tip(id),\n" +
                    "FOREIGN KEY (tag) REFERENCES Tag(id)\n" +
                    ");";
            String relcourseTable = "CREATE TABLE IF NOT EXISTS RelCourse (\n" +
                    "tip int NOT NULL,\n" +
                    "course int NOT NULL,\n" +
                    "FOREIGN KEY (tip) REFERENCES Tip(id),\n" +
                    "FOREIGN KEY (course) REFERENCES Course(id)\n" +
                    ");";
            String reqcourseTable = "CREATE TABLE IF NOT EXISTS ReqCourse (\n" +
                    "tip int NOT NULL,\n" +
                    "course int NOT NULL,\n" +
                    "FOREIGN KEY (tip) REFERENCES Tip(id),\n" +
                    "FOREIGN KEY (course) REFERENCES Course(id)\n" +
                    ");";
            String[] queries = {tipTable, tagTable, courseTable, tiptagTable, relcourseTable, reqcourseTable};

            for (String query : queries) {
                Statement statement = conn.createStatement();
                statement.execute(query);
                statement.close();
            }


        } catch (SQLException e) {
            System.out.println("Failed to create sqlite database. Make sure sqlite is installed");
        }
    }
}
