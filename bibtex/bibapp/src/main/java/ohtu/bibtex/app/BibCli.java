package ohtu.bibtex.app;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import static org.jbibtex.BibTeXEntry.*;
import org.jbibtex.Key;
import org.jbibtex.StringValue;


/**
 * Yksinkertainen komentorivikäli
 * @author Jouko Strömmer
 */
public class BibCli {
    private final ConsoleIO io;
    
    public BibCli () {
        io = new ConsoleIO();
    }
    
    /**
     * Tulostaa viitetietokannan (testausta varten)
     * @param db 
     */
    public void printDatabase (BibTeXDatabase db) {
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
     * Kirjaviitteen luku
    */
    public org.jbibtex.BibTeXEntry readBookRef () {
        // (Wikipedia, BibTeX book entry) "Required fields: author/editor, title, publisher, year"
        BibTeXEntry entry = null;
        io.print("Adding Book reference - required fields: author, title, publisher, year");
        io.print("Entering an empty field cancels.");
        String cite = io.readLine("Cite key:");
        if(cite.equals("")) return null;
        StringValue author = new StringValue(io.readLine("Author:"), StringValue.Style.BRACED);
        if(author.toString().equals("")) return null;
        StringValue title = new StringValue(io.readLine("Title:"), StringValue.Style.BRACED);
        if(title.toString().equals("")) return null;
        StringValue publisher = new StringValue(io.readLine("Publisher:"), StringValue.Style.BRACED);
        if(publisher.toString().equals("")) return null;
        StringValue year = new StringValue(io.readLine("Year:"), StringValue.Style.BRACED);
        if(year.toString().equals("")) return null;
        entry = new BibTeXEntry(new Key("Book"), new Key(cite));
        entry.addField(KEY_AUTHOR, author);
        entry.addField(KEY_TITLE, title);
        entry.addField(KEY_PUBLISHER, publisher);
        entry.addField(KEY_YEAR, year);
        return entry;
    }
    
}
