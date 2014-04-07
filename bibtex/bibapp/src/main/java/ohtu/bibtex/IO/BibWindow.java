/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.bibtex.IO;

import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.layout.VerticalLayout;
import ohtu.bibtex.app.BibDatabase;

/**
 *
 * @author skaipio
 */
public class BibWindow extends Window {

    public BibWindow(GUIScreen gui, BibDatabase bibDb) {
        super("BibTeX Reference Database");
        MenuPanel menu = new MenuPanel(this, bibDb);       
        this.addComponent(menu, VerticalLayout.GROWS_HORIZONTALLY);
    }  
}
