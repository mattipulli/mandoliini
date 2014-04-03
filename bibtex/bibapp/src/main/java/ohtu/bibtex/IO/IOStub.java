package ohtu.bibtex.IO;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author greatman
 * use for testing
 */

public class IOStub implements IO {

	private String[] lines;
	private int i;
	private ArrayList<String> prints;

	public IOStub(String... values) {
		this.lines = values;
		prints = new ArrayList<String>();
	}

	@Override
	public void print(String toPrint) {
		prints.add(toPrint);
	}

	@Override
	public int readInt(String prompt) {
		print(prompt);
		return Integer.parseInt(lines[i++]);
	}

	@Override
	public String readLine(String prompt) {
		print(prompt);
		if (i < lines.length) {
			return lines[i++];
		}
		return "";
	}
        
        public List<String> getPrints(){
            return this.prints;
        }

	@Override
	public boolean readYesNo(String prompt) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
}
