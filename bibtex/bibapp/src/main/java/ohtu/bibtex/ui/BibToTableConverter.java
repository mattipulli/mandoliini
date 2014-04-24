package ohtu.bibtex.ui;

import java.util.ArrayList;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import ohtu.bibtex.app.BibDatabase;
import org.jbibtex.BibTeXDatabase;
import org.jbibtex.BibTeXEntry;
import org.jbibtex.Key;
import org.jbibtex.Value;

public class BibToTableConverter {
    
    BibTeXDatabase bdb;
    Map<Key, BibTeXEntry> entryMap;
    ArrayList<String> fieldnames;
    ArrayList<BibTeXEntry> allentries;
    Object[][] data;
    
    public BibToTableConverter(BibDatabase db){
        this.bdb = db.getDatabase();
        this.entryMap = bdb.getEntries();
        this.fieldnames = new ArrayList<String>();
        this.allentries = new ArrayList<BibTeXEntry>(entryMap.values());
    }
    
    public DefaultTableModel convertToDefaultTableModel(){
        this.addCiteKeyAndType();
        this.collectUniqueFieldNamesAndCountThem();
        this.storeEntryData();
        this.createData();
        return new DefaultTableModel(this.data, this.fieldnames.toArray());
    }
    
    public void addCiteKeyAndType(){
        this.fieldnames.add(0, "citekey");
        this.fieldnames.add(1, "type");
    }
    
    public void collectUniqueFieldNamesAndCountThem(){
        for (BibTeXEntry b : this.allentries) {
            for (Key k : b.getFields().keySet()) {
                String field = k.toString().toLowerCase();
                if (!this.fieldnames.contains(field)) {
                    this.fieldnames.add(field);
                }
            }
        }
    }
    
    public void storeEntryData(){
        this.data = new Object[allentries.size()][fieldnames.size()];
    }
    
    public void createData(){
         // Variables for iterating data array (i = entry, j = field in entry)
        int i = 0;
        int j;

        // For all entries
        for (BibTeXEntry entry : this.allentries) {
            // Store their cite key and type as first two fields
            this.data[i][0] = entry.getKey().toString();
            this.data[i][1] = entry.getType().toString().toLowerCase();
            // Start with first field
            j = 0;
            // For all fields in an entry
            for (String k : this.fieldnames) {
                // Read their values
                Value value = entry.getField(new Key(k));
                // Store non-null value in array
                if (value != null) {
                    this.data[i][j] = value.toUserString();
                }
                // Proceed with next field
                j++;
            }
            // Next entry in array
            i++;
        }
    }

    public BibTeXDatabase getBdb() {
        return bdb;
    }

    public Map<Key, BibTeXEntry> getEntryMap() {
        return entryMap;
    }

    public ArrayList<String> getFieldnames() {
        return fieldnames;
    }

    public ArrayList<BibTeXEntry> getAllentries() {
        return allentries;
    }

    public void setBdb(BibTeXDatabase bdb) {
        this.bdb = bdb;
    }

    public void setEntryMap(Map<Key, BibTeXEntry> entryMap) {
        this.entryMap = entryMap;
    }

    public void setFieldnames(ArrayList<String> fieldnames) {
        this.fieldnames = fieldnames;
    }

    public void setAllentries(ArrayList<BibTeXEntry> allentries) {
        this.allentries = allentries;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }
    
    
    
}
