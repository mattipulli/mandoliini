package ohtu.bibtex.app;

import ohtu.bibtex.IO.ConsoleIO;
import ohtu.bibtex.IO.IO;
import org.jbibtex.BibTeXEntry;
import static org.jbibtex.BibTeXEntry.*;
import org.jbibtex.Key;
import org.jbibtex.StringValue;

/**
 * Simple text interface for adding BibTeX references
 *
 *
 */
public class BibCli {

    private final IO io;

    public BibCli() {
        io = new ConsoleIO();
    }

    public BibCli(IO io) {
        this.io = io;
    }

    /**
     * Yes/No prompt
     *
     * @param prompt prompt text
     * @return answer, true means Yes
     */
    public boolean continuePrompt(String prompt) {
        return io.readYesNo(prompt);
    }

    /**
     * Ask confirmation for filename
     *
     * @param fn filename to ask about
     * @return chosen filename
     */
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
     * Read in values for creating a Book reference
     *
     * @return reference entry
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

    /**
     * Ask user to input Book references in a loop
     *
     * @param db database to store entries in
     */
    public void askEntries(BibDatabase db) {
        boolean next = true;
        boolean changed = false;
        while (next) {
            BibTeXEntry entry = readBookRef();
            if (entry != null) {
                io.print("You entered:");
                BibUtil.printBookEntry(this.io, entry);
                if (!continuePrompt("Is this OK?")) {
                    System.out.println("Not adding entry.");
                } else {
                    db.getDatabase().addObject(entry);
                    changed = true;
                }
            }
            next = continuePrompt("Add another entry?");
        }

        if (!db.getDatabase().getObjects().isEmpty() && changed) {
            if (continuePrompt("Save changes?")) {
                db.saveDatabase(confirmFilename(db.getDbpath()));
            }
        }
        if (!changed) {
            io.print("Nothing changed, exiting.");
        }
    }
}
