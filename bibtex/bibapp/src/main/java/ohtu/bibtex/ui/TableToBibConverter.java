package ohtu.bibtex.ui;

import javax.swing.table.TableModel;
import ohtu.bibtex.app.BibDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;
import org.jbibtex.StringValue;

public class TableToBibConverter {

    private BibTeXEntry entry;
    private BibDatabase database;
    private TableModel model;
    private Key cite;
    private Key type;

    public TableToBibConverter(TableModel model) {
        this.model = model;
    }

    public BibDatabase convertToBibDatabase() {
        this.initDummyDatabase();
        this.forEachToBeEntry();
        return this.database;
    }

    public void initDummyDatabase() {
        this.database = new BibDatabase("dummy.bib");
    }

    public void atLeastTwoColumns(int i) {
        if (model.getValueAt(i, 0) != null && !model.getValueAt(i, 0).toString().trim().equals("")) {
            cite = new Key(model.getValueAt(i, 0).toString().trim());
        }
        // Read type from *SECOND* column
        if (model.getValueAt(i, 1) != null && !model.getValueAt(i, 1).toString().trim().equals("")) {
            type = new Key(model.getValueAt(i, 1).toString().trim().toLowerCase());
        }
    }

    public void bothAreNull(int i) {
        // Create new entry
        entry = new BibTeXEntry(type, cite);
        // Iterate rest of the columns
        for (int j = 2; j < model.getColumnCount(); j++) {
            Key field;
            StringValue value;
            // Add non-empty field and value to entry
            if (model.getValueAt(i, j) != null && !model.getValueAt(i, j).toString().trim().equals("")) {
                field = new Key(model.getColumnName(j));
                value = new StringValue(model.getValueAt(i, j).toString().trim(), StringValue.Style.BRACED);
                entry.addField(field, value);
            }
        }
    }

    public void notNull() {
        database.getDatabase().addObject(entry);
    }

    public void forEachToBeEntry() {
        for (int i = 0; i < model.getRowCount(); i++) {

            this.cite = null;
            this.type = null;
            entry = null;

            if (model.getColumnCount() > 1) {
                this.atLeastTwoColumns(i);
            }

            if (cite != null && type != null) {
                this.bothAreNull(i);
            }

            if (entry != null) {
                this.notNull();
            }

        }
    }

    public BibTeXEntry getEntry() {
        return entry;
    }

    public BibDatabase getDatabase() {
        return database;
    }

    public void setEntry(BibTeXEntry entry) {
        this.entry = entry;
    }

    public void setDatabase(BibDatabase database) {
        this.database = database;
    }

}
