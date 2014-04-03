package ohtu.bibtex.app;

import ohtu.bibtex.IO.ConsoleIO;

/**
 * Minimalist program for inputting BibTeX references into a file
 *
 */
public class BibApp {

    // default reference database filename, will be read if exists
    private final static String dbpath = "refdb.bibtex";

    public static void main(String[] args) {
        BibDatabase db = new BibDatabase(dbpath);
        BibCli cli = new BibCli(new ConsoleIO());
        cli.askEntries(db);
    }
}
