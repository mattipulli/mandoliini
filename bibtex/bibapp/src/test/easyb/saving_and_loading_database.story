import ohtu.bibtex.app.BibDatabase
import org.jbibtex.Key
import org.jbibtex.BibTeXEntry
import org.jbibtex.StringValue
import java.io.*
import java.util.ArrayList

description """Saving a database saves its entries to a file.
Loading a bibtex file loads the entries into database."""

def addKeyAndValueToEntry(entry, key, value) {
    entry.addField(key, new StringValue(value,StringValue.Style.BRACED))
}

def getLinesFromFile(file) {
    FileReader fin = new FileReader(file);
    Scanner src = new Scanner(fin);
    ArrayList<String> lines = new ArrayList<String>();

    while (src.hasNext()) {
        lines.add(src.nextLine());
    }
    return lines
}

scenario "Database is saved to a file", {
    given 'Database has entries and is saved', {
       db = new BibDatabase("")
       entry = new BibTeXEntry(new Key("Book"), new Key("avain"))
       addKeyAndValueToEntry(entry, BibTeXEntry.KEY_AUTHOR, "hessu")
       addKeyAndValueToEntry(entry, BibTeXEntry.KEY_PUBLISHER, "ankkalinna")
       addKeyAndValueToEntry(entry, BibTeXEntry.KEY_TITLE, "varjohanhi")
       addKeyAndValueToEntry(entry, BibTeXEntry.KEY_YEAR, "1999")
       db.getDatabase().addObject(entry)
    }
 
    when 'entries are saved to a file', {
      db.saveDatabase("test.bibtex")
    }

    then 'the file should exist.', {    
      file = new File("test.bibtex")
      file.exists().shouldBe true
    }
}

scenario "Database is saved to a file in proper format", {
    given 'Database has entries and is saved', {
       db = new BibDatabase("")
       entry = new BibTeXEntry(new Key("Book"), new Key("avain"))
       addKeyAndValueToEntry(entry, BibTeXEntry.KEY_AUTHOR, "hessu")
       addKeyAndValueToEntry(entry, BibTeXEntry.KEY_PUBLISHER, "ankkalinna")
       addKeyAndValueToEntry(entry, BibTeXEntry.KEY_TITLE, "varjohanhi")
       addKeyAndValueToEntry(entry, BibTeXEntry.KEY_YEAR, "1999")
       db.getDatabase().addObject(entry)
    }
 
    when 'entries are saved to a file', {
      db.saveDatabase("test.bibtex")
    }

    then 'the entry should be saved in a correct bibtex format', {    
      file = new File("test.bibtex")
      lines = getLinesFromFile(file)
      lines.shouldHave("@Book{avain,")
      lines.shouldHave("\tauthor = {hessu},")
      lines.shouldHave("publisher = {ankkalinna},")
      lines.shouldHave("title = {varjohanhi},")
      lines.shouldHave("year = {1999}")
    }
}