/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.bibtex.app;

import org.jbibtex.BibTeXEntry;

/**
 *
 * @author Jouko Strömmer
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
