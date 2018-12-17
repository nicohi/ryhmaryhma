/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinkkeri.ui.gui;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import vinkkeri.database.SQLiteTagDao;
import vinkkeri.database.SQLiteTipDao;
import vinkkeri.objects.Tip;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Olli K. Kärki
 */
public class DaoControllerTest {

    private DaoController controller;
    private SQLiteTipDao tipDao;
    private SQLiteTagDao tagDao;

    @Before
    public void setUp() {
        File db = new File("test.db");
        if (db.exists()) {
            db.delete();
        }
        String dataAdress = "jdbc:sqlite:test.db";
        this.tipDao = new SQLiteTipDao(dataAdress);
        this.tagDao = new SQLiteTagDao(dataAdress);
        this.controller = new DaoController(tipDao, tagDao);
    }

    @Test
    public void getTipsWorks() {
        tipDao.insertTip(new Tip("title", "author"));
        tipDao.insertTip(new Tip("toka", "autööri"));

        List<Tip> tipsDao = tipDao.getTips();
        assertFalse("TipDao returns empty list, can't complete test", tipsDao.isEmpty());

        List<Tip> tipsController = controller.getTips();
        assertFalse("DaoController returns empty list, can't complete test", tipsController.isEmpty());

        for (Tip tip : tipsController) {
            assertTrue("DaoController returns a tip which dao doesn't.", tipsDao.contains(tip));
        }

        for (Tip tip : tipsDao) {
            assertTrue("Dao returns a tip which controller doesn't.", tipsController.contains(tip));
        }
    }

    @Test
    public void removeAndAddTipWorks() {
        Tip tip = new Tip(3, "2018-11-15", "Introduction to Algorithms - that exists solely for654654654654654SecretHasHasHas", "CLRS", "algos", "http://mitpress.mit.edu", "notfalse");

        this.controller.insertTip(tip);
        List<Tip> tips = controller.getTips();

        boolean exists = false;

        for (Tip t : tips) {
            if (t.getTitle().equals(tip.getTitle())) {
                tip = t;
                exists = true;
                break;
            }
        }

        assertTrue("Tip was not added correctly, check out controller.insertTip()", exists);

        controller.removeTip(tip);

        tips = controller.getTips();

        exists = false;

        for (Tip t : tips) {
            if (t.equals(tip)) {
                exists = true;
                break;
            }
        }

        assertFalse("Tip was not added correctly, check out controller.insertTip()", exists);

    }

    @Test
    public void markAsReadWorks() {
        Tip tip = new Tip(3, "2018-11-15", "Intrasfasfa12124ms - t124hat exists solely125125 for654654654654654SecretHasHasHas", "CLRS", "algos", "http://mitpress.mit.edu", "");
        this.controller.insertTip(tip);
        List<Tip> tips = controller.getTips();

        boolean exists = false;

        for (Tip t : tips) {
            if (t.getTitle().equals(tip.getTitle())) {
                tip = t;
                exists = true;
                break;
            }
        }

        assertTrue("Tip was not added correctly, check out controller.insertTip()", exists);
        assertTrue("Tip should be initialize with read = false, but came as " + tip.isRead(), tip.isRead().equals("false"));
        controller.markRead("notfalse", tip.getId());

        tips = controller.getTips();

        for (Tip t : tips) {
            if (t.getTitle().equals(tip.getTitle())) {
                tip = t;
                exists = true;
                break;
            }
        }

        assertFalse("After calling controller.markRead(false, tip.getId()), tip should have read = true, but was " + tip.isRead(), tip.isRead().equals("false"));

        controller.removeTip(tip);
    }

}
