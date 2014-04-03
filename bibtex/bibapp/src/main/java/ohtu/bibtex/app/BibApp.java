package ohtu.bibtex.app;

import java.io.File;
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;

/**
 * Minimalistinen viitteen syöttämisen testiohjelma
 *
 */
public class BibApp {

    // viitetietokantatiedosto, luetaan käynnistyksessä
    private final static String dbpath = "refdb.bibtex";

    public static void main(String[] args) {
        boolean next = true;
        boolean changed = false;
        BibTeXDatabase database = null;
        BibCli cli = new BibCli();
        File f = new File(dbpath);

        // ladataan olemassaoleva tietokanta tai luodaan uusi
        if (f.exists()) {
            database = cli.readDatabase(dbpath);
        } else {
            database = new BibTeXDatabase();
        }

        // kysytään silmukassa viitteitä
        while (next) {
            BibTeXEntry entry = cli.readBookRef();
            if (entry != null) {
                database.addObject(entry);
                changed = true;
            }
            next = cli.continuePrompt("Add another entry?");
        }

        if (!database.getObjects().isEmpty() && changed) {
            if (cli.continuePrompt("Save changes?")) {
                cli.saveDatabase(database, cli.confirmFilename(dbpath));
            }
        }
        if(!changed) System.out.println("Nothing changed, exiting.");
    }
}
