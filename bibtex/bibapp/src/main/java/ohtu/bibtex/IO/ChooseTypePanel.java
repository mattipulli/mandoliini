/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ohtu.bibtex.IO;

import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.ActionListBox;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.layout.VerticalLayout;
import ohtu.bibtex.app.BibDatabase;

/**
 *
 * @author skaipio
 */
class ChooseTypePanel extends Panel {

        public ChooseTypePanel(Window window, Panel menuPanel, BibDatabase bibDb) {
            super("Choose Type", Panel.Orientation.VERTICAL);
            ActionListBox types = new ActionListBox();
            types.addAction("Book", new ChooseType(window, this, menuPanel, "Book", bibDb));
            types.addAction("Back to Menu", new BackToMenu(window, this, menuPanel));
            this.addComponent(types);
        }

        private class ChooseType implements Action {

            private final Window window;
            private final Panel chooseTypePanel;
            private final Panel menuPanel;
            private final String type;
            private final BibDatabase bibDb;

            public ChooseType(Window window, Panel chooseTypePanel, Panel menuPanel, String type, BibDatabase bibDb) {
                this.window = window;
                this.chooseTypePanel = chooseTypePanel;
                this.menuPanel = menuPanel;
                this.type = type;
                this.bibDb = bibDb;
            }        
            
            @Override
            public void doAction() {
                this.window.removeComponent(this.chooseTypePanel);
                AddEntryPanel addEntryPanel = new AddEntryPanel(this.window, this.menuPanel, type, bibDb);
                this.window.addComponent(addEntryPanel, VerticalLayout.GROWS_HORIZONTALLY);
            }
        }
        
        private class BackToMenu implements Action {

            private final Window window;
            private final Panel menuPanel;
            private final Panel chooseTypePanel;

            public BackToMenu(Window window, Panel chooseTypePanel, Panel menuPanel) {
                this.window = window;
                this.menuPanel = menuPanel;
                this.chooseTypePanel = chooseTypePanel;
            }        
            
            @Override
            public void doAction() {
                this.window.removeComponent(this.chooseTypePanel);
                this.window.addComponent(this.menuPanel, VerticalLayout.GROWS_HORIZONTALLY);
            }
        }
    }
