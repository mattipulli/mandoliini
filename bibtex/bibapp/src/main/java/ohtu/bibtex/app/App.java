package ohtu.bibtex.app;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jbibtex.ParseException;
import org.jbibtex.TokenMgrError;

/**
 * Minimalistinen jbibtex-esimerkki
 *
 */
public class App {

    public static void main(String[] args) {
        Reader reader = null;

        try {
            reader = new FileReader("src/resources/malli.bibtex");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, "Bibtex file not found", ex);
        }
        if (reader != null) {
            org.jbibtex.BibTeXParser bibtexParser = new org.jbibtex.BibTeXParser();
            try {
                org.jbibtex.BibTeXDatabase database = bibtexParser.parse(reader);
                Map<org.jbibtex.Key, org.jbibtex.BibTeXEntry> entryMap = database.getEntries();

                // iteroidaan luettu viitetietokanta
                Collection<org.jbibtex.BibTeXEntry> entries = entryMap.values();
                for (org.jbibtex.BibTeXEntry entry : entries) {

                    // luetaan viitteestä title ja author
                    org.jbibtex.Value title = entry.getField(org.jbibtex.BibTeXEntry.KEY_TITLE);
                    org.jbibtex.Value author = entry.getField(org.jbibtex.BibTeXEntry.KEY_AUTHOR);

                    if (title == null || author == null) {
                        continue;
                    }
                    // tulostetaan
                    System.out.println(author.toUserString() + " -- " + title.toUserString());
                }

            } catch (ParseException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, "Failed to parse bibtex file", ex);
            } catch (TokenMgrError ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, "Token error", ex);
            }

            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, "Failed to close bibtex file", ex);
            }
        }
    }
}
