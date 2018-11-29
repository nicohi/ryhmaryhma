package vinkkeri.database;

import vinkkeri.objects.Tip;

import java.util.List;

public interface TipDao {

    List<Tip> getTips();

    void insertTip(Tip tip);

    void remove(int id);

    int getNewestID();

    void markReadValue(int id, String read);
}
