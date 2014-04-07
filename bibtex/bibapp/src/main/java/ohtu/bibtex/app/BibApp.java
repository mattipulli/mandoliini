package ohtu.bibtex.app;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.GUIScreen;
import ohtu.bibtex.IO.BibWindow;

/**
 * Minimalist program for inputting BibTeX references into a file
 *
 */
public class BibApp {

    // default reference database filename, will be read if exists
    private final static String dbpath = "refdb.bibtex";

    public static void main(String[] args) {
//        BibDatabase db = new BibDatabase(dbpath);
//        BibCli cli = new BibCli(new ConsoleIO());
//        cli.askEntries(db);
        GUIScreen gui = TerminalFacade.createGUIScreen();
        gui.getScreen().startScreen();
        gui.showWindow(new BibWindow(gui, new BibDatabase(dbpath)), GUIScreen.Position.CENTER);
        gui.getScreen().stopScreen();
    }
}
