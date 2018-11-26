package vinkkeri.ui.gui;

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

//tämä on copypastattu tui.controller
//Controller:ista voisi tehdä rajapinnan tai abstraktin luokkan
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
    }

    public void removeTip(Tip tip) {
    }
    
    public List<Tip> getTips() {
		return null;
    }
    
    public void markRead(boolean value, String name) {
    }
}
