package ohtu.bibtex.app;

import java.io.File;
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;

/**
 * Minimalist program for inputting BibTeX references into a file
 *
 */
public class BibApp {

    // default reference database filename, will be read if exists
    private final static String dbpath = "refdb.bibtex";

    public static void main(String[] args) {
        boolean next = true;
        boolean changed = false;
        BibTeXDatabase database = null;
        BibCli cli = new BibCli();
        File f = new File(dbpath);

        // load existing database from file or create an empty one
        if (f.exists()) {
            database = cli.readDatabase(dbpath);
        } else {
            database = new BibTeXDatabase();
        }

        // loop and prompt user to add a reference
        while (next) {
            BibTeXEntry entry = cli.readBookRef();
            if (entry != null) {
                System.out.println("You entered:");
                BibUtil.printBookEntry(entry);
                if (!cli.continuePrompt("Is this OK?")) {
                    System.out.println("Not adding entry.");
                } else {
                    database.addObject(entry);
                    changed = true;
                }
            }
            next = cli.continuePrompt("Add another entry?");
        }

        if (!database.getObjects().isEmpty() && changed) {
            if (cli.continuePrompt("Save changes?")) {
                cli.saveDatabase(database, cli.confirmFilename(dbpath));
            }
        }
        if (!changed) {
            System.out.println("Nothing changed, exiting.");
        }
    }
}
