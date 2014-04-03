package ohtu.bibtex.app;

import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;

/**
 * Minimalistinen viitteen syöttämisen testiohjelma
 *
 */
public class BibApp {

    public static void main(String[] args) {
        BibCli cli = new BibCli();
        org.jbibtex.BibTeXDatabase database = new BibTeXDatabase();
        BibTeXEntry entry = cli.readBookRef();
        if (entry != null) {
            database.addObject(entry);
            cli.printDatabase(database);
        }
    }
}
