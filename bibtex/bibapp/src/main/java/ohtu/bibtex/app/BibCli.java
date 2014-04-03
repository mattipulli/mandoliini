package ohtu.bibtex.app;

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
import org.jbibtex.BibTeXEntry;
import static org.jbibtex.BibTeXEntry.*;
import org.jbibtex.BibTeXParser;
import org.jbibtex.Key;
import org.jbibtex.ParseException;
import org.jbibtex.StringValue;
import org.jbibtex.TokenMgrError;

/**
 * Yksinkertainen komentorivikäli
 *
 * @author Jouko Strömmer
 */
public class BibCli {

    private final ConsoleIO io;

    public BibCli() {
        io = new ConsoleIO();
    }

    public boolean continuePrompt(String prompt) {
        return io.readYesNo(prompt);
    }

    /**
     * Tulostaa viitetietokannan (testausta varten)
     *
     * @param db viitetietokanta
     */
    public void printDatabase(BibTeXDatabase db) {
        Writer writer = new StringWriter();
        org.jbibtex.BibTeXFormatter bibtexFormatter = new org.jbibtex.BibTeXFormatter();
        try {
            bibtexFormatter.format(db, writer);
        } catch (IOException ex) {
            Logger.getLogger(BibCli.class.getName()).log(Level.SEVERE, "Failed to print database", ex);
        }
        io.print(writer.toString());
    }

    /**
     * Lukee tietokannan tiedostosta
     *
     * @param filename tiedostonimi
     * @return luettu viitetietokanta
     */
    public BibTeXDatabase readDatabase(String filename) {
        Reader reader = null;
        BibTeXDatabase database = null;
        try {
            reader = new FileReader(filename);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BibCli.class.getName()).log(Level.SEVERE, "File not found: " + filename, ex);
        }
        if (reader != null) {
            BibTeXParser bibtexParser = new org.jbibtex.BibTeXParser();
            try {
                database = bibtexParser.parse(reader);
            } catch (ParseException ex) {
                Logger.getLogger(BibCli.class.getName()).log(Level.SEVERE, "Failed to parse database", ex);
            } catch (TokenMgrError ex) {
                Logger.getLogger(BibCli.class.getName()).log(Level.SEVERE, "Token error", ex);
            }
        }
        return database;
    }

    /**
     * Tallenna viitetietokanta tiedostoon
     *
     * @param db viitetietokanta
     * @param filename tiedostonimi
     */
    public void saveDatabase(BibTeXDatabase db, String filename) {
        Writer writer = null;
        try {
            writer = new FileWriter(filename, false);
        } catch (IOException ex) {
            Logger.getLogger(BibCli.class.getName()).log(Level.SEVERE, "Failed to open " + filename + " for writing", ex);
        }
        org.jbibtex.BibTeXFormatter bibtexFormatter = new org.jbibtex.BibTeXFormatter();
        try {
            bibtexFormatter.format(db, writer);
        } catch (IOException ex) {
            Logger.getLogger(BibCli.class.getName()).log(Level.SEVERE, "Failed to write database", ex);
        }
    }

    public String confirmFilename(String fn) {
        String filename;
        if (continuePrompt("Use file " + fn + " ?")) {
            return fn;
        } else {
            while (true) {
                filename = io.readLine("Enter new filename:");
                if (!filename.equals("")) {
                    return filename;
                }
            }
        }
    }

    /**
     * Kirjaviitteen luku
     *
     * @return Viiteobjekti
     */
    public org.jbibtex.BibTeXEntry readBookRef() {
        // (Wikipedia, BibTeX book entry) "Required fields: author/editor, title, publisher, year"
        BibTeXEntry entry = null;
        io.print("Adding Book reference - required fields: author, title, publisher, year");
        io.print("Entering an empty field cancels.");
        String cite = io.readLine("Cite key:");
        if (cite.equals("")) {
            return null;
        }
        StringValue author = new StringValue(io.readLine("Author:"), StringValue.Style.BRACED);
        if (author.toUserString().equals("")) {
            return null;
        }
        
        StringValue title = new StringValue(io.readLine("Title:"), StringValue.Style.BRACED);
        if (title.toUserString().equals("")) {
            return null;
        }
        StringValue publisher = new StringValue(io.readLine("Publisher:"), StringValue.Style.BRACED);
        if (publisher.toUserString().equals("")) {
            return null;
        }
        StringValue year = new StringValue(io.readLine("Year:"), StringValue.Style.BRACED);
        if (year.toUserString().equals("")) {
            return null;
        }
        entry = new BibTeXEntry(new Key("Book"), new Key(cite));
        entry.addField(KEY_AUTHOR, author);
        entry.addField(KEY_TITLE, title);
        entry.addField(KEY_PUBLISHER, publisher);
        entry.addField(KEY_YEAR, year);
        return entry;
    }

}
