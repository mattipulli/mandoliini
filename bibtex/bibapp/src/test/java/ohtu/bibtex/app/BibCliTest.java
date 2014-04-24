/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.bibtex.app;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import ohtu.bibtex.IO.IOStub;
import ohtu.bibtex.ui.BibSwingApp;
import ohtu.bibtex.ui.BibToTableConverter;
import ohtu.bibtex.ui.TableToBibConverter;
import org.jbibtex.BibTeXEntry;
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
public class BibCliTest {

    private BibCli cli;
    private BibDatabase clidb;
    private BibUtil cliutil;
    private IOStub iostub;
    private TableToBibConverter tabletobib;
    private BibToTableConverter bibtotable;

    public BibCliTest() {
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

    @Test
    public void confirmFilenameReturnsFileNameIfConfirmed() {
        this.cli = new BibCli(new IOStub("y"));
        String fn = this.cli.confirmFilename("filename");
        assertEquals("filename", fn);
    }

    @Test
    public void confirmFilenameReturnsSuppliedFileNameIfNotConfirmed() {
        this.cli = new BibCli(new IOStub("n", "filename"));
        String fn = this.cli.confirmFilename("somename");
        assertEquals("filename", fn);
    }

    @Test
    public void readBookRefReturnsEntryWithSuppliedFields() {
        this.cli = new BibCli(new IOStub("111", "Satupukki", "Barry Butter", "Tinakirjat", "1000"));
        BibTeXEntry entry = this.cli.readBookRef();
        assertEquals("Satupukki", entry.getField(BibTeXEntry.KEY_AUTHOR).toUserString());
        assertEquals("Barry Butter", entry.getField(BibTeXEntry.KEY_TITLE).toUserString());
        assertEquals("Tinakirjat", entry.getField(BibTeXEntry.KEY_PUBLISHER).toUserString());
        assertEquals("1000", entry.getField(BibTeXEntry.KEY_YEAR).toUserString());
    }

    @Test
    public void readBookRefReturnsNullIfARequiredFieldIsNotGiven() {
        this.cli = new BibCli(new IOStub("111", "Satupukki", ""));
        BibTeXEntry entry = this.cli.readBookRef();
        assertNull(entry);
    }

    @Test
    public void databaseReturnDatabasePath() {
        this.clidb = new BibDatabase("tietokanta");
        String db = this.clidb.getDbpath();
        assertEquals(db, "tietokanta");
    }

    @Test
    public void databaseDatabaseReturn() {
        this.clidb = new BibDatabase("refdb.bibtex");
        assertNotNull(this.clidb.getDatabase());
    }

    @Test
    public void testIOPrint() {
        this.iostub = new IOStub();
        int before = this.iostub.getPrints().size();
        this.iostub.print("Testirivi");
        int after = this.iostub.getPrints().size();
        assertFalse(before == after);
    }

    @Test
    public void testIOPrintNULL() {
        this.iostub = new IOStub();
        int before = this.iostub.getPrints().size();
        this.iostub.print(null);
        int after = this.iostub.getPrints().size();
        assertFalse(before == after);
    }

    @Test
    public void testIOReadInt() {
        this.iostub = new IOStub("1");
        int before = this.iostub.getPrints().size();
        this.iostub.readInt("1");
        int after = this.iostub.getPrints().size();
        assertFalse(before == after);
    }

    @Test
    public void testIOReadIntNoNumbers() {
        this.iostub = new IOStub("<asdg<");
        int before = this.iostub.getPrints().size();
        try {
            this.iostub.readInt("Testi");
        } catch (Exception e) {
            assertFalse(false);
        }
    }

    @Test
    public void testTableToBibConstructor() {
        JTable table = new JTable();
        TableModel model = table.getModel();
        this.tabletobib = new TableToBibConverter(model);
        assertTrue(this.tabletobib.getModel() == model);
    }

    @Test
    public void testTableToBibConversion1() {
        final String[] columnNames = {"citekey", "type", "author", "title", "publisher", "year",
            "volume", "series", "address", "edition", "month", "note",
            "key", "journal", "number", "pages", "booktitle", "editor",
            "organization"};

        final Object[][] emptyData = new Object[1][19];
        
        JTable editedtable=new JTable();
        editedtable.setModel(
            new DefaultTableModel(emptyData, columnNames)
        );
        DefaultTableModel model = (DefaultTableModel) editedtable.getModel();
        model.addRow(new Object[]{"Article5", "Article", "Testaaja", "Testi", "r", "1970",
            "", "", "", "", "", "",
            "", "", "", "", "", "",
            ""});
        
        this.tabletobib = new TableToBibConverter(model);
        BibDatabase db=this.tabletobib.convertToBibDatabase();
        assertTrue(db.getDatabase().getEntries().size()==1);
    }
    
    @Test
    public void testTableToBibConversion2() {
        final String[] columnNames = {"citekey", "type", "author", "title", "publisher", "year",
            "volume", "series", "address", "edition", "month", "note",
            "key", "journal", "number", "pages", "booktitle", "editor",
            "organization"};

        final Object[][] emptyData = new Object[1][19];
        
        JTable editedtable=new JTable();
        editedtable.setModel(
            new DefaultTableModel(emptyData, columnNames)
        );
        DefaultTableModel model = (DefaultTableModel) editedtable.getModel();
        model.addRow(new Object[]{"Article5", "Article", "Testaaja", "Testi", "r", "1970",
            "", "", "", "", "", "",
            "", "", "", "", "", "",
            ""});
        model.addRow(new Object[]{"Article6", "Article", "Testaaja", "Testi2", "r", "1970",
            "", "", "", "", "", "",
            "", "", "", "", "", "",
            ""});
        
        this.tabletobib = new TableToBibConverter(model);
        BibDatabase db=this.tabletobib.convertToBibDatabase();
        assertTrue(db.getDatabase().getEntries().size()==2);
    }

}
