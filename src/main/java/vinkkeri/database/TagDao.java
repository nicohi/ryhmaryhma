package vinkkeri.database;

import java.sql.SQLException;
import java.util.List;

public interface TagDao {
    List<String> getTags() throws SQLException;

    void addTags(List<String> tags) throws SQLException;
}
