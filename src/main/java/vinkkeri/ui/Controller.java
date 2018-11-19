package vinkkeri.ui;

import vinkkeri.database.SQLiteTipDao;
import vinkkeri.database.TipDao;
import vinkkeri.objects.Tip;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private List<Tip> tips;
    private TipDao tipDao;

    public Controller() {
        this.tipDao = new SQLiteTipDao("jdbc:sqlite:database.db");
        try {
            this.tips = tipDao.getTips();
        } catch (Exception e) {
            e.printStackTrace();
            this.tips = new ArrayList<>();
        }
    }

    public void newTip(Tip tip) {

        try {
            tipDao.insertTip(tip);
            tips = tipDao.getTips();
        } catch (Exception e) {
            System.out.println("SQL error");
            e.printStackTrace();
        }
    }

    public List<Tip> getTips() {
        return tips;
    }
}
