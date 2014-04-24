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
     * @return DefaultTableModel for Swing TablePane (data + field names)
     */
    public static DefaultTableModel bibToTable(BibDatabase db) {
        BibToTableConverter converter=new BibToTableConverter(db);
        return converter.convertToDefaultTableModel();
    }

    /**
     * Convert TablePane contents into BibDatabase for saving. TODO: very ugly
     * and hairy with all the null checks
     *
     * @param model TablePane model (via GetModel())
     * @return new BibDatabase with entries populated from TablePane
     */
    public static BibDatabase tableToBib(TableModel model) {
        TableToBibConverter converter = new TableToBibConverter(model);
        return converter.convertToBibDatabase();
    }
}
