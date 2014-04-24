/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.bibtex.ui;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.finder.DialogFinder;
import org.fest.swing.finder.FrameFinder;
import org.fest.swing.finder.JOptionPaneFinder;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.*;
import org.fest.swing.launcher.ApplicationLauncher;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author skaipio
 */
public class BibSwingAppTest {

    private ApplicationLauncher app;
    private Robot robot;
    private DialogFixture dialogFixt;
    private FrameFixture frameFixt;

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
        this.app = ApplicationLauncher.application(BibSwingApp.class);
        this.app.start();

        this.robot = BasicRobot.robotWithCurrentAwtHierarchy();
        this.robot.settings().delayBetweenEvents(100);

        JOptionPaneFixture optionPaneFix = JOptionPaneFinder.findOptionPane().using(this.robot);
        optionPaneFix.okButton().click();
        
        FrameFinder ff = WindowFinder.findFrame(JFrame.class);  
        this.frameFixt = ff.using(robot);
    }

    @After
    public void tearDown() {
        this.frameFixt.cleanUp();
    }

    @Test
    public void correctMainWindow() {
        this.frameFixt.button("addEntry").requireVisible();
        this.frameFixt.button("removeSelectedEntry").requireVisible();
    }
    
//    @Test
//    public void addEntryButtonAddsARow() {
//        this.frameFixt.button("addEntry").click();
//        this.frameFixt.
//    }

    // Temporarily commented out, should be implemented with fest (or Xvfb plugin)   
//    @Test
//    public void guiAddEntry() {
//        this.optionPaneFixt.buttonWithText("OK").click();
//        this.frameFixt.button("addEntry").click();
//        Component c = this.gui.getScrollpane().getComponent(0);
//        int before = this.gui.getReftable().getRowCount();
//        this.gui.getAddbutton().doClick();
//        int after = this.gui.getReftable().getRowCount();
//        assertEquals(before + 1, after);
//
//    }

//    @Test
//    public void guiDropEntry() {
//        this.gui.getAddbutton().doClick();
//        int before = this.gui.getReftable().getRowCount();
//        this.gui.getReftable().setRowSelectionInterval(0, 0);
//        this.gui.getRemovebutton().doClick();
//        int after = this.gui.getReftable().getRowCount();
//        assertEquals(before - 1, after);
//    }
//    
//
//    @Test
//    public void guiAddDataToEntry() {
//
//    }
//
//    @Test
//    public void guiDeleteDataFromEntry() {
//
//    }
}
