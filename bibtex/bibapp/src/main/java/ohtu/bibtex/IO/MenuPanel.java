/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu.bibtex.IO;

import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.layout.LinearLayout;
import com.googlecode.lanterna.gui.layout.VerticalLayout;
import ohtu.bibtex.app.BibDatabase;

/**
 *
 * @author skaipio
 */
public class MenuPanel extends Panel {

    public MenuPanel(Window window, BibDatabase bibDb) {
        super("Menu", Panel.Orientation.VERTICAL);
        this.addComponent(new AddButton(bibDb, window, this), LinearLayout.GROWS_HORIZONTALLY);
        this.addComponent(new ListEntriesButton(bibDb, window, this), LinearLayout.GROWS_HORIZONTALLY);
        this.addComponent(new QuitButton(window), LinearLayout.GROWS_HORIZONTALLY);
    }

    private static class AddButton extends Button {

        public AddButton(BibDatabase bibDb, Window window, MenuPanel menuPanel) {
            super("Add Entry", new AddAction(bibDb, window,menuPanel));
        }

        private static class AddAction implements Action {

            private final BibDatabase bibDb;
            private final Window window;
            private final MenuPanel menuPanel;

            public AddAction(BibDatabase bibDb, Window window, MenuPanel menuPanel) {
                this.bibDb = bibDb;
                this.window = window;
                this.menuPanel = menuPanel;
            }

            @Override
            public void doAction() {
                this.window.removeComponent(this.menuPanel);
                this.window.addComponent(new ChooseTypePanel(this.window, this.menuPanel, this.bibDb), VerticalLayout.GROWS_HORIZONTALLY);
            }

        }
    }
    
    private static class ListEntriesButton extends Button {

        public ListEntriesButton(BibDatabase bibDb, Window window, MenuPanel menuPanel) {
            super("List Entries", new ListAction(bibDb, window,menuPanel));
        }

        private static class ListAction implements Action {

            private final BibDatabase bibDb;
            private final Window window;
            private final MenuPanel menuPanel;

            public ListAction(BibDatabase bibDb, Window window, MenuPanel menuPanel) {
                this.bibDb = bibDb;
                this.window = window;
                this.menuPanel = menuPanel;
            }

            @Override
            public void doAction() {
                this.window.removeComponent(this.menuPanel);
                this.window.addComponent(new ListEntriesPanel(this.bibDb, this.window, this.menuPanel), VerticalLayout.GROWS_HORIZONTALLY);
            }

        }
    }

    private static class QuitButton extends Button {

        public QuitButton(Window window) {
            super("Quit", new QuitAction(window));
        }

        private static class QuitAction implements Action {

            private final Window window;

            public QuitAction(Window window) {
                this.window = window;
            }

            @Override
            public void doAction() {
                this.window.close();
            }

        }
    }
}
