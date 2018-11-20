package vinkkeri.database;

import vinkkeri.objects.Tip;

import java.sql.SQLException;
import java.util.List;

public interface TipDao {
    List<Tip> getTips() throws Exception;

    void insertTip(Tip tip) throws SQLException;

    void remove(int id) throws SQLException;

    int getNewestID() throws SQLException;
    
    void markReadValue(String name, boolean read) throws SQLException;
}
