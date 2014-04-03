import ohtu.bibtex.IO.IOStub
import ohtu.bibtex.app.*

description """Client asks for entries and prints an entry after each submission."""

def queryEntry(io) {
       db = new BibDatabase("refdb.bibtex")
       cli = new BibCli(io)
       cli.askEntries(db)
}

scenario "Submitted entry is printed after correct submission", {
    given 'entry submission is selected', {
       io = new IOStub("999","Paku Kankku", "Diipadaapa", "BestBooks", "9999", "y","n","n") 
    }
 
    when 'an entry is submitted without cancel', {
      queryEntry(io)
    }

    then 'the entry is printed for confirmation', {
      io.getPrints().shouldHave("\nKey: 999\n==========\nAuthor:\t\tPaku Kankku\nTitle:\t\tDiipadaapa\nPublisher:\tBestBooks\nYear:\t\t9999\n\n")
    }
}