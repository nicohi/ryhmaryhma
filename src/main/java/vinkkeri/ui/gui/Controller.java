package vinkkeri.ui.gui;

import vinkkeri.database.SQLiteTagDao;
import vinkkeri.database.SQLiteTipDao;
import vinkkeri.database.TagDao;
import vinkkeri.database.TipDao;
import vinkkeri.objects.Tip;

import java.util.List;

//Controller:ista voisi tehdä rajapinnan tai abstraktin luokkan ja ehkä nimetä paremmin
public class Controller {

    private TipDao tipDao;
    private TagDao tagDao;

    public Controller() {
        String databaseAddress = "jdbc:sqlite:database.db";
        final String testProperty = System.getProperty("use.test.db");
        if (testProperty != null && testProperty.equals("true")) {
            databaseAddress = "jdbc:sqlite:test.db";
        }
        this.tipDao = new SQLiteTipDao(databaseAddress);
        this.tagDao = new SQLiteTagDao(databaseAddress);

    }

    public void removeTip(Tip tip) {
        tipDao.remove(tip.getId());
    }

    public List<Tip> getTips() {
        return tipDao.getTips();
    }

    public void markRead(String value, int id) {
        tipDao.markReadValue(id, value);
    }

    public void addTags(List<String> tags) {
        tagDao.addTags(tags);
    }

    public void insertTip(Tip tip) {
        tipDao.insertTip(tip);
    }

    public void updateTip(Tip tip) {
        tipDao.updateTip(tip);
    }

    public void removeTags(List<String> tags) {
        tagDao.removeTags(tags);
    }
}
