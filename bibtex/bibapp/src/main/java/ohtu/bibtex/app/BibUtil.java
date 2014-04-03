package ohtu.bibtex.app;

import org.jbibtex.BibTeXEntry;

/**
 * Some simple utility methods
 * 
 */
public class BibUtil {

    public static void printBookEntry(BibTeXEntry entry) {
        String title = entry.getField(BibTeXEntry.KEY_TITLE).toUserString();
        String author = entry.getField(BibTeXEntry.KEY_AUTHOR).toUserString();
        String publisher = entry.getField(BibTeXEntry.KEY_PUBLISHER).toUserString();
        String year = entry.getField(BibTeXEntry.KEY_YEAR).toUserString();
        System.out.print("\nKey: " + entry.getKey() + "\n==========\nAuthor:\t\t" + author + "\nTitle:\t\t"
                + title + "\nPublisher:\t" + publisher + "\nYear:\t\t" + year + "\n\n");
    }
}
