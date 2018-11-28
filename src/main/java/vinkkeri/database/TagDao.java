package vinkkeri.database;

import java.util.List;

public interface TagDao {

    List<String> getTags();

    void addTags(List<String> tags);

    void removeTags(List<String> tags);
}
