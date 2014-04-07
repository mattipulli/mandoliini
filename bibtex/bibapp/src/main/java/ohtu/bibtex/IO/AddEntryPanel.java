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
import com.googlecode.lanterna.gui.component.Table;
import com.googlecode.lanterna.gui.component.TextBox;
import com.googlecode.lanterna.gui.layout.VerticalLayout;
import java.util.HashMap;
import java.util.Map;
import ohtu.bibtex.app.BibDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;
import org.jbibtex.StringValue;

/**
 *
 * @author skaipio
 */
public class AddEntryPanel extends Panel {

    private final Window window;
    private final Map<String, Key[]> keySets = new HashMap<>();
    private String type;
    private TextBox citeKeyTextBox;
    private final Map<Key, TextBox> textBoxes = new HashMap<>();

    public AddEntryPanel(Window window, Panel menuPanel, String type, BibDatabase bibDb) {
        super("Add BibTeX entry", Panel.Orientation.VERTICAL);
        this.window = window;
        this.type = type;
        this.keySets.put("Book", new Key[]{BibTeXEntry.KEY_AUTHOR,
            BibTeXEntry.KEY_TITLE, BibTeXEntry.KEY_PUBLISHER, BibTeXEntry.KEY_YEAR});
        this.addFieldComponents(type, this.keySets.get(type));
        this.addComponent(new SubmitButton(window, this, menuPanel, bibDb));
        this.addComponent(new CancelButton(window, this, menuPanel));
    }

    private void addFieldComponents(String type, Key[] keys) {
        Table keysAndValues = new Table(2, type);
        this.citeKeyTextBox = new TextBox();
        keysAndValues.addRow(new Label("cite key: "), this.citeKeyTextBox);
        for (Key key : keys) {
            TextBox textBox = new TextBox();
            keysAndValues.addRow(new Label(key + ": "), textBox);
            this.textBoxes.put(key, textBox);
        }
        this.addComponent(keysAndValues);
    }

    private static class CancelButton extends Button {

        public CancelButton(Window window, Panel addEntryPanel, Panel menuPanel) {
            super("Cancel", new CancelAction(window, addEntryPanel, menuPanel));
        }

        private static class CancelAction implements Action {

            private Window window;
            private Panel addEntryPanel;
            private Panel menuPanel;

            public CancelAction(Window window, Panel addEntryPanel, Panel menuPanel) {
                this.window = window;
                this.addEntryPanel = addEntryPanel;
                this.menuPanel = menuPanel;
            }

            @Override
            public void doAction() {
                this.window.removeComponent(this.addEntryPanel);
                this.window.addComponent(this.menuPanel, VerticalLayout.GROWS_HORIZONTALLY);
            }

        }
    }

    private class SubmitButton extends Button {

        public SubmitButton(Window window, AddEntryPanel addEntryPanel, Panel menuPanel, BibDatabase bibDb) {
            super("Submit", new SubmitAction(window, addEntryPanel, menuPanel, bibDb));

        }

    }

    private class SubmitAction implements Action {

        private final Window window;
        private final Panel addEntryPanel;
        private final Panel menuPanel;
        private final BibDatabase bibDb;

        public SubmitAction(Window window, Panel addEntryPanel, Panel menuPanel, BibDatabase bibDb) {
            this.window = window;
            this.addEntryPanel = addEntryPanel;
            this.menuPanel = menuPanel;
            this.bibDb = bibDb;
        }

        @Override
        public void doAction() {
            BibTeXEntry entry = new BibTeXEntry(new Key(type), new Key(citeKeyTextBox.getText()));
            for (Key key : textBoxes.keySet()) {
                StringValue value = new StringValue(textBoxes.get(key).getText(), StringValue.Style.BRACED);
                entry.addField(key, value);
            }

            this.bibDb.addEntry(entry);

            this.window.removeComponent(this.addEntryPanel);
            this.window.addComponent(this.menuPanel, VerticalLayout.GROWS_HORIZONTALLY);
        }

    }
}
