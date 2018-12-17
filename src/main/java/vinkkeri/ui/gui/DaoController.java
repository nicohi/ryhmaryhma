package vinkkeri.ui.gui;

import vinkkeri.database.TagDao;
import vinkkeri.database.TipDao;
import vinkkeri.objects.Tip;

import java.util.List;

public class DaoController {

    private TipDao tipDao;
    private TagDao tagDao;

    public DaoController(TipDao tipDao, TagDao tagDao) {
        this.tipDao = tipDao;
        this.tagDao = tagDao;
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
