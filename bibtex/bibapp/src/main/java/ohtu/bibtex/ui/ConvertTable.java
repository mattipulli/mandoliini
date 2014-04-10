package ohtu.bibtex.ui;

import java.util.ArrayList;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import ohtu.bibtex.app.BibDatabase;
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;
import org.jbibtex.StringValue;
import org.jbibtex.Value;

/**
 *
 * Conversion functions to populate a Swing TablePane with .bib and vice versa
 */
public class ConvertTable {

    /**
     * Convert BibDatabase to a DefaultTableModel for Swing TablePane
     *
     * @param db BibDatabase to convert
     * @return
     */
    public static DefaultTableModel bibToTable(BibDatabase db) {
        // Load reference database
        BibTeXDatabase bdb = db.getDatabase();
        // Get all entries
        Map<Key, BibTeXEntry> entryMap = bdb.getEntries();
        // Array for storing field names *in order*
        ArrayList<String> fieldnames = new ArrayList<String>();
        // Array for storing BibTeX entries *in order*
        ArrayList<BibTeXEntry> allentries = new ArrayList<BibTeXEntry>(entryMap.values());

        // Add cite key and type into table fields
        fieldnames.add(0, "citekey");
        fieldnames.add(1, "type");

        // Collect unique field names and count them
        for (BibTeXEntry b : allentries) {
            for (Key k : b.getFields().keySet()) {
                String field = k.toString();
                if (!fieldnames.contains(field)) {
                    fieldnames.add(field);
                }
            }
        }

        // Store entry data
        Object[][] data = new Object[allentries.size()][fieldnames.size()];

        // Variables for iterating data array (i = entry, j = field in entry)
        int i = 0;
        int j;

        // For all entries
        for (BibTeXEntry entry : allentries) {
            // Store their cite key and type as first two fields
            data[i][0] = entry.getKey().toString();
            data[i][1] = entry.getType().toString();
            // Start with first field
            j = 0;
            // For all fields in an entry
            for (String k : fieldnames) {
                // Read their values
                Value value = entry.getField(new Key(k));
                // Store non-null value in array
                if (value != null) {
                    data[i][j] = value.toUserString();
                }
                // Proceed with next field
                j++;
            }
            // Next entry in array
            i++;
        }
        // Return data and field names for TablePane
        return new DefaultTableModel(data, fieldnames.toArray());
    }

    /**
     * Convert TablePane contents into BibDatabase for saving. TODO: very ugly
     * and hairy with all the null checks
     *
     * @param model TablePane model (via GetModel())
     * @return new BibDatabase with entries populated from TablePane
     */
    public static BibDatabase tableToBib(TableModel model) {
        BibTeXEntry entry;
        // Use dummy filename - need to fix this filename business
        BibDatabase database = new BibDatabase("dummy.bib");
        // For each to-be entry (row) in table
        for (int i = 0; i < model.getRowCount(); i++) {
            // Initialize variables for null checks
            entry = null;
            Key cite = null;
            Key type = null;

            // If at least two columns, read the first two as cite key and type
            if (model.getColumnCount() > 1) {
                // Empty cite key or type -> entry is deleted (ie. not created)

                // Read cite key from *FIRST* column
                if (model.getValueAt(i, 0) != null && !model.getValueAt(i, 0).toString().trim().equals("")) {
                    cite = new Key(model.getValueAt(i, 0).toString().trim());
                }
                // Read type from *SECOND* column
                if (model.getValueAt(i, 1) != null && !model.getValueAt(i, 1).toString().trim().equals("")) {
                    type = new Key(model.getValueAt(i, 1).toString().trim());
                }
            }

            // Add entry only if both cite key and type are non-null
            if (cite != null && type != null) {
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

            // Add non-null entry
            if (entry != null) {
                database.getDatabase().addObject(entry);
            }
        }
        // Return new database
        return database;
    }
}
