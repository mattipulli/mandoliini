/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.bibtex.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXParser;
import org.jbibtex.ParseException;
import org.jbibtex.TokenMgrError;

/**
 * BibTeX database methods
 *
 */
public final class BibDatabase {

    private final String dbpath;
    private BibTeXDatabase database;

    /**
     * Return database filename
     *
     * @return
     */
    public String getDbpath() {
        return dbpath;
    }

    public BibTeXDatabase getDatabase() {
        return database;
    }

    public BibDatabase(String databaseFilename) {
        dbpath = databaseFilename;
        File f = new File(dbpath);

        // load existing database from file or create an empty one
        if (f.exists()) {
            database = readDatabase(dbpath);
        } else {
            database = new BibTeXDatabase();
        }
    }

    /**
     * Read database from BibTeX file
     *
     * @param filename database filename
     * @return parsed database
     */
    public BibTeXDatabase readDatabase(String filename) {
        Reader reader = null;
        BibTeXDatabase db = null;
        try {
            reader = new FileReader(filename);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BibCli.class.getName()).log(Level.SEVERE, "File not found: " + filename, ex);
        }
        if (reader != null) {
            BibTeXParser bibtexParser = new org.jbibtex.BibTeXParser();
            try {
                db = bibtexParser.parse(reader);
            } catch (ParseException ex) {
                Logger.getLogger(BibCli.class.getName()).log(Level.SEVERE, "Failed to parse database", ex);
            } catch (TokenMgrError ex) {
                Logger.getLogger(BibCli.class.getName()).log(Level.SEVERE, "Token error", ex);
            }
        }
        return db;
    }

    /**
     * Write reference database to a file in BibTeX format
     *
     * @param filename filename to write to
     */
    public void saveDatabase(String filename) {
        Writer writer = null;
        try {
            writer = new FileWriter(filename, false);
        } catch (IOException ex) {
            Logger.getLogger(BibCli.class.getName()).log(Level.SEVERE, "Failed to open " + filename + " for writing", ex);
        }
        org.jbibtex.BibTeXFormatter bibtexFormatter = new org.jbibtex.BibTeXFormatter();
        try {
            bibtexFormatter.format(database, writer);
        } catch (IOException ex) {
            Logger.getLogger(BibCli.class.getName()).log(Level.SEVERE, "Failed to write database", ex);
        }
    }

    /**
     * Print out the reference database in BibTeX format (for testing)
     *
     * @param db reference database
     */
    public void printDatabase(BibTeXDatabase db, ConsoleIO io) {
        Writer writer = new StringWriter();
        org.jbibtex.BibTeXFormatter bibtexFormatter = new org.jbibtex.BibTeXFormatter();
        try {
            bibtexFormatter.format(db, writer);
        } catch (IOException ex) {
            Logger.getLogger(BibCli.class.getName()).log(Level.SEVERE, "Failed to print database", ex);
        }
        io.print(writer.toString());
    }

}
