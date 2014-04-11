/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.bibtex.ui;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author skaipio
 */
public class BibSwingAppTest {
    private BibSwingApp gui;

    public BibSwingAppTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

   // Temporarily commented out, jenkins can't handle GUI
    @Test
    public void guiAddEntry() {
        this.gui = new BibSwingApp();
        int before = this.gui.getReftable().getRowCount();
        this.gui.getAddbutton().doClick();
        int after = this.gui.getReftable().getRowCount();
        assertEquals(before + 1,after);
    }

    @Test
    public void guiDropEntry() {
        this.gui = new BibSwingApp();
        this.gui.getAddbutton().doClick();
        int before = this.gui.getReftable().getRowCount();
        this.gui.getReftable().setRowSelectionInterval(0, 0);
        this.gui.getRemovebutton().doClick();
        int after = this.gui.getReftable().getRowCount();
        assertEquals(before - 1, after);
    }

    @Test
    public void guiAddDataToEntry() {

    }

    @Test
    public void guiDeleteDataFromEntry() {

    }

}
