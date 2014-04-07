/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ohtu.bibtex.IO;

import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.layout.VerticalLayout;
import ohtu.bibtex.app.BibDatabase;
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.Key;

/**
 *
 * @author skaipio
 */
public class ListEntriesPanel extends Panel {
    public ListEntriesPanel(BibDatabase bibDb, Window window, Panel menuPanel){
        this.addEntries(bibDb);
        this.addComponent(new BackToMenuButton(window, this, menuPanel));
    }
    
    private void addEntries(BibDatabase bibDb){
        for(Key key : bibDb.getDatabase().getEntries().keySet()){
            this.addComponent(new Label(key.getValue()));
        }
    }
    
    public class BackToMenuButton extends Button{

        public BackToMenuButton(Window window, Panel listEntriesPanel, Panel menuPanel) {
            super("Back to Menu", new BackToMenuAction(window, listEntriesPanel, menuPanel));
        }
        
    }
    
    public class BackToMenuAction implements Action{
        private final Window window;
        private final Panel listEntriesPanel;
        private final Panel menuPanel;

        public BackToMenuAction(Window window, Panel listEntriesPanel, Panel menuPanel) {
            this.window = window;
            this.listEntriesPanel = listEntriesPanel;
            this.menuPanel = menuPanel;
        }
        
        @Override
        public void doAction() {
            this.window.removeComponent(this.listEntriesPanel);
            this.window.addComponent(this.menuPanel, VerticalLayout.GROWS_HORIZONTALLY);
        }
        
    }
}
