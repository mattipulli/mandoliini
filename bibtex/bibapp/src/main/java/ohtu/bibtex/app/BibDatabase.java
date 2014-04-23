package ohtu.bibtex.app;

import ohtu.bibtex.IO.ConsoleIO;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        InputStream stream = ClassLoader.getSystemResourceAsStream(dbpath);
        
        if (stream != null) {
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
            InputStream stream = ClassLoader.getSystemResourceAsStream(filename);
            reader = new InputStreamReader(stream);
            //reader = new FileReader(filename);
        } catch (Exception ex) {
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
        if (writer != null) {
            try {
                writer.write(formatDatabase());
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(BibDatabase.class.getName()).log(Level.SEVERE, "Failed to write to file", ex);
            }
        }
    }

    public String formatDatabase() {
        Writer writer = new StringWriter();
        org.jbibtex.BibTeXFormatter bibtexFormatter = new org.jbibtex.BibTeXFormatter();
        try {
            bibtexFormatter.format(database, writer);
        } catch (IOException ex) {
            Logger.getLogger(BibDatabase.class.getName()).log(Level.SEVERE, "Failed to format database", ex);
        }
        return writer.toString();
    }

    /**
     * Print out the reference database in BibTeX format (for testing)
     *
     * @param io I/O object
     */
    public void printDatabase(ConsoleIO io) {
        io.print(formatDatabase());
    }

}
