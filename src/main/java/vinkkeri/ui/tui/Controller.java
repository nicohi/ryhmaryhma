package vinkkeri.ui.tui;

import vinkkeri.database.SQLiteTipDao;
import vinkkeri.database.TipDao;
import vinkkeri.objects.Tip;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import vinkkeri.database.CourseDao;
import vinkkeri.database.SQLiteCourseDao;
import vinkkeri.database.SQLiteTagDao;
import vinkkeri.database.TagDao;

public class Controller {
    
    private List<Tip> tips;
    private TipDao tipDao;
    private CourseDao courseDao;
    private TagDao tagDao;
    
    public Controller() {
        this.tipDao = new SQLiteTipDao("jdbc:sqlite:database.db");
        this.courseDao = new SQLiteCourseDao("jdbc:sqlite:database.db");
        this.tagDao = new SQLiteTagDao("jdbc:sqlite:database.db");
        try {
            this.tips = tipDao.getTips();
        } catch (Exception e) {
            e.printStackTrace();
            this.tips = new ArrayList<>();
        }
    }
    
    public void newTip(Tip tip) {
        try {
            courseDao.addCourses(tip.getRelC());
            courseDao.addCourses(tip.getReqC());
            tagDao.addTags(tip.getTags());
            tipDao.insertTip(tip);
            tips = tipDao.getTips();
        } catch (Exception e) {
            System.out.println("SQL error");
            e.printStackTrace();
        }
    }

    public void removeTip(Tip tip) {
        try {
			tipDao.remove(tip.getId());
            tips = tipDao.getTips();
        } catch (Exception e) {
            System.out.println("SQL error");
            e.printStackTrace();
        }
    }
    
    public List<Tip> getTips() {
        return tips;
    }
    
    public void markRead(boolean value, String name) {
        try {
            tipDao.markReadValue(name, value);
            for (Tip tip : tips) {
                if (tip.getTitle().equals(name)) {
                    tip.setRead(value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
