package ohtu.bibtex.IO;

/**
 *
 * @author Greaman Lim
 */
public interface IO {
	void print(String toPrint);
	int readInt(String prompt);
	String readLine(String prompt);
	boolean readYesNo(String prompt);
}
