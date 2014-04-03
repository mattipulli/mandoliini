package ohtu.bibtex.app;

import ohtu.bibtex.IO.IO;
import org.jbibtex.BibTeXEntry;

/**
 * Some simple utility methods
 *
 */
public class BibUtil {

    /**
     * Pretty print an inputted entry for user confirmation
     * @param io IO to use for printing
     * @param entry Book reference entry to print
     */
    public static void printBookEntry(IO io, BibTeXEntry entry) {
        String title = entry.getField(BibTeXEntry.KEY_TITLE).toUserString();
        String author = entry.getField(BibTeXEntry.KEY_AUTHOR).toUserString();
        String publisher = entry.getField(BibTeXEntry.KEY_PUBLISHER).toUserString();
        String year = entry.getField(BibTeXEntry.KEY_YEAR).toUserString();
        io.print("\nKey: " + entry.getKey() + "\n==========\nAuthor:\t\t" + author + "\nTitle:\t\t"
                + title + "\nPublisher:\t" + publisher + "\nYear:\t\t" + year + "\n\n");
    }
}
