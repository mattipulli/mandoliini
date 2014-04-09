package ohtu.bibtex.ui;

import java.util.ArrayList;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import ohtu.bibtex.app.BibDatabase;
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;
import org.jbibtex.Value;

/**
 *
 * Conversion functions to populate a Swing TablePane with .bib
 * - TODO: vice versa
 */
public class ConvertTable {

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
        fieldnames.add(0, "key");
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
}
