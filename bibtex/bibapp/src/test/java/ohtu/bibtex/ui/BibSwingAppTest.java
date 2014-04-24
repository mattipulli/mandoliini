/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.bibtex.ui;

import java.awt.Component;
import java.io.File;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import org.fest.swing.cell.JTableCellReader;
import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.GenericTypeMatcher;
import org.fest.swing.core.Robot;
import org.fest.swing.data.TableCell;
import org.fest.swing.data.TableCellByColumnId;
import org.fest.swing.data.TableCellFinder;
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
    private JOptionPaneFixture optionFixt;
    private FrameFixture frameFixt;
    private String testEntry = "@article{KEY01,\n" +
                                "	author = {Dolan},\n" +
                                "	title = {Ducks101},\n" +
                                "	publisher = {Daily Duck}\n" +
                                "}";

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
        this.robot.settings().delayBetweenEvents(50);

        this.optionFixt = JOptionPaneFinder.findOptionPane().using(this.robot);
        this.optionFixt.okButton().click();
        
        FrameFinder ff = WindowFinder.findFrame(JFrame.class);  
        this.frameFixt = ff.using(robot);
    }

    @After
    public void tearDown() {
        this.frameFixt.cleanUp();
        this.robot.cleanUp();
    }

    @Test
    public void correctMainWindow() {
        this.frameFixt.button("addEntry").requireVisible();
        this.frameFixt.button("removeSelectedEntry").requireVisible();
    }
    
    @Test
    public void addEntryButtonAddsARow() {
        JButtonFixture buttonFixt = this.frameFixt.button("addEntry");
        JTableFixture tableFix = this.frameFixt.table("entryTable");
        tableFix.requireRowCount(1);
        buttonFixt.click();
        tableFix.requireRowCount(2);
        buttonFixt.click();
        tableFix.requireRowCount(3);
    }
    
    @Test
    public void removeEntryButtonRemovesSelectedRow() {
        JButtonFixture addFix = this.frameFixt.button("addEntry");
        JButtonFixture removeFix = this.frameFixt.button("removeSelectedEntry");
        JTableFixture tableFix = this.frameFixt.table("entryTable");
        addFix.click();
        addFix.click();
        tableFix.requireRowCount(3);
        tableFix.selectRows(1);
        removeFix.click();
        tableFix.requireRowCount(2);
    }
    
    @Test
    public void addEntryButtonAddsRowWithCorrectType() {
        JButtonFixture buttonFixt = this.frameFixt.button("addEntry");
        JTableFixture tableFix = this.frameFixt.table("entryTable");
        JComboBoxFixture comboFix = this.frameFixt.comboBox("entryTypeSelector");
        comboFix.selectItem("Book");
        buttonFixt.click();
        JTableCellFixture cellFix = tableFix.cell(TableCellByColumnId.row(1).columnId("type"));
        cellFix.requireValue("Book");
        comboFix.selectItem("Manual");
        buttonFixt.click();
        cellFix = tableFix.cell(TableCellByColumnId.row(2).columnId("type"));
        cellFix.requireValue("Manual");
    }
    
    @Test
    public void editingRowsAndClickingPreviewDisplaysCorrectBibTexFormat() {
        JButtonFixture addButton = this.frameFixt.button("addEntry");
        JTableFixture tableFix = this.frameFixt.table("entryTable");
        addButton.click();
        this.addTestEntry(tableFix, 1);
        JButtonFixture previewButton = this.frameFixt.button("previewSelected");
        previewButton.click();
        JTextComponentFixture previewFix = this.frameFixt.textBox("previewArea");
        previewFix.requireText(testEntry);
    }

    @Test
    public void addingEntryAndSavingUpdatesStatus() {
        JButtonFixture buttonFixt = this.frameFixt.button("addEntry");
        JTableFixture tableFix = this.frameFixt.table("entryTable");
        buttonFixt.click();
        addTestEntry(tableFix, 1);
        JMenuItemFixture saveAsFix = this.frameFixt.menuItem("saveAs");
        saveAsFix.click();
        JFileChooserFixture fileChooserFixt = this.frameFixt.fileChooser();
        fileChooserFixt.fileNameTextBox().enterText("festtest");
        fileChooserFixt.approve();
        JLabelFixture statusFix = this.frameFixt.label("status");
        statusFix.requireText(Pattern.compile(".*Saved.*festtest"));
    }
    
    private void addTestEntry(JTableFixture tableFix, int rowNum){
        JTableCellFixture cellFix = tableFix.cell(TableCellByColumnId.row(rowNum).columnId("citekey"));
        cellFix.enterValue("KEY01");
        cellFix = tableFix.cell(TableCellByColumnId.row(rowNum).columnId("author"));
        cellFix.enterValue("Dolan");
        cellFix = tableFix.cell(TableCellByColumnId.row(rowNum).columnId("title"));
        cellFix.enterValue("Ducks101");
        cellFix = tableFix.cell(TableCellByColumnId.row(rowNum).columnId("publisher"));
        cellFix.enterValue("Daily Duck");
    }
}
