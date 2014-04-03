/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ohtu.bibtex.app;

import ohtu.bibtex.IO.IOStub;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;
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
public class BibDatabaseTest {
    private String dbPath = "testentries.bibtex";
    private BibDatabase db;
    private BibCli cli;
    
    public BibDatabaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.cli = new BibCli(new IOStub("111", "Satupukki", "Barry Butter", "Tinakirjat", "1000", "y", "n", "y", "y"));
        this.db = new BibDatabase(dbPath);
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void databaseCanBeSavedAndLoaded() {
         this.cli.askEntries(db);
         this.db.saveDatabase(dbPath);
         this.db = new BibDatabase(dbPath);
         this.db.readDatabase(dbPath);
         assertNotNull(this.db.getDatabase());
         assertEquals(1, this.db.getDatabase().getEntries().size());
         BibTeXEntry entry = this.db.getDatabase().resolveEntry(new Key("111"));
         assertNotNull(entry);
         assertEquals("Satupukki", entry.getField(BibTeXEntry.KEY_AUTHOR).toUserString());
         assertEquals("Barry Butter", entry.getField(BibTeXEntry.KEY_TITLE).toUserString());
         assertEquals("Tinakirjat", entry.getField(BibTeXEntry.KEY_PUBLISHER).toUserString());
         assertEquals("1000", entry.getField(BibTeXEntry.KEY_YEAR).toUserString());
     }
}
