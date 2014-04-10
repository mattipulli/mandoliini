/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ohtu.bibtex.app;

import ohtu.bibtex.IO.IOStub;
import ohtu.bibtex.ui.BibSwingApp;
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
    private BibSwingApp gui;
    
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
    public void databaseReturnDatabasePath(){
         this.clidb=new BibDatabase("tietokanta");
         String db=this.clidb.getDbpath();
         assertEquals(db, "tietokanta");
    }
     
    @Test
    public void databaseDatabaseReturn(){
        this.clidb=new BibDatabase("refdb.bibtex");
        assertNotNull(this.clidb.getDatabase());
    }
    
    @Test
    public void guiAddEntry(){
        this.gui=new BibSwingApp();
        int before=this.gui.getReftable().getRowCount();
        this.gui.getAddbutton().doClick();
        int after=this.gui.getReftable().getRowCount();
        assertTrue(after==before+1);
    }
    
    @Test
    public void guiDropEntry(){
        this.gui=new BibSwingApp();
        this.gui.getAddbutton().doClick();
        int before=this.gui.getReftable().getRowCount();
        this.gui.getReftable().setRowSelectionInterval(0,0);
        this.gui.getRemovebutton().doClick();
        int after=this.gui.getReftable().getRowCount();
        assertTrue(after==before-1);
    }
    
    @Test
    public void guiAddDataToEntry(){
        
    }
    
    @Test
    public void guiDeleteDataFromEntry(){
        
    }
     
     
}
