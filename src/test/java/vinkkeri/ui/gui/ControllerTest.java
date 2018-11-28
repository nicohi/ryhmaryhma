/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vinkkeri.ui.gui;

import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import vinkkeri.database.SQLiteTagDao;
import vinkkeri.database.SQLiteTipDao;
import vinkkeri.objects.Tip;
import vinkkeri.ui.gui.controllers.AddTipController;

/**
 *
 * @author Olli K. Kärki
 */
public class ControllerTest {

    Controller controller;
    SQLiteTipDao tipDao;
    SQLiteTagDao tagDao;
    String dataAdress;

    @Before
    public void setUp() {
        this.controller = new Controller();
        this.dataAdress = "jdbc:sqlite:database.db";
        this.tipDao = new SQLiteTipDao(dataAdress);
        this.tagDao = new SQLiteTagDao(dataAdress);
    }
    
    @Test
    public void getTipsWorks() {
        List<Tip> tipsController = controller.getTips();
        assertFalse("Controller returns empty list, can't complete test", tipsController.isEmpty());
        
        List<Tip> tipsDao = tipDao.getTips();
        assertFalse("TipDao returns empty list, can't complete test", tipsDao.isEmpty());
        
        boolean missMatch = true;
        
        for(Tip tip : tipsController) {
            if(!tipsDao.contains(tip)) {
                missMatch = false;
                break;
            }
        }
        
        assertTrue("Controller returns a tip which dao doesn't.", missMatch);
        
        for(Tip tip : tipsDao) {
            if(!tipsController.contains(tip)) {
                missMatch = false;
                break;
            }
        }
        
        assertTrue("Dao returns a tip which controller doesn't.", missMatch);
    }
    
    @Test
    public void removeAndAddTipWorks() {
        Tip tip = new Tip(3, "2018-11-15", "Book", "Introduction to Algorithms - that exists solely for654654654654654SecretHasHasHas", "CLRS", "algos", "9780262033848", "http://mitpress.mit.edu", true);
        
        this.controller.insertTip(tip);
        List<Tip> tips = controller.getTips();
        
        boolean exists = false;
        
        for(Tip t : tips) {
            if(t.getTitle().equals(tip.getTitle())) {
                tip = t;
                exists = true;
                break;
            }
        }
        
        assertTrue("Tip was not added correctly, check out controller.insertTip()", exists);
        
        controller.removeTip(tip);
        
        tips = controller.getTips();
        
        exists = false;
        
        for(Tip t : tips) {
            if(t.equals(tip)) {
                exists = true;
                break;
            }
        }
        
        assertFalse("Tip was not added correctly, check out controller.insertTip()", exists);
        
    }
    
    @Test
    public void markAsReadWorks() {
        Tip tip = new Tip(3, "2018-11-15", "Book", "Intrasfasfa12124ms - t124hat exists solely125125 for654654654654654SecretHasHasHas", "CLRS", "algos", "9780262033848", "http://mitpress.mit.edu", false);
        this.controller.insertTip(tip);
        List<Tip> tips = controller.getTips();
        
        boolean exists = false;
        
        for(Tip t : tips) {
            if(t.getTitle().equals(tip.getTitle())) {
                tip = t;
                exists = true;
                break;
            }
        }
        
        assertTrue("Tip was not added correctly, check out controller.insertTip()", exists);
        assertTrue("Tip should be initialize with read = false, but came as " + tip.isRead(), !tip.isRead());
        controller.markRead(true, tip.getId());
        
        tips = controller.getTips();
        
        for(Tip t : tips) {
            if(t.getTitle().equals(tip.getTitle())) {
                tip = t;
                exists = true;
                break;
            }
        }
        
        assertTrue("After calling controller.markRead(false, tip.getId()), tip should have read = true, but was " + tip.isRead(), tip.isRead());
        
        controller.removeTip(tip);
    }

}
