package ohtu.bibtex.IO;

import java.util.Scanner;

/**
 * Some basic Console I/O methods
 */
public class ConsoleIO implements IO {
    private final Scanner scanner = new Scanner(System.in);
    
    /**
     * Print text
     * @param toPrint text to be printed
     */
    @Override
    public void print(String toPrint) {
        System.out.println(toPrint);
    }

    /**
     * Read integer
     * @param prompt prompt string
     * @return integer parsed from text
     */
    @Override
    public int readInt(String prompt) {
        System.out.print(prompt+" ");
        return Integer.parseInt(scanner.nextLine());
    }

    /**
     * Read a single line of input
     * @param prompt prompt string
     * @return line
     */
    @Override
    public String readLine(String prompt) {
        System.out.print(prompt+" ");
        return scanner.nextLine();
    }
    
    /**
     * Read "Y" or "N" (case insensitive), accepts nothing else
     * @param prompt prompt string
     * @return true for "Yes"
     */
    @Override
    public boolean readYesNo(String prompt) {
        String ans;
        while(true) {
            System.out.print(prompt+" [y/n]: ");
            ans = scanner.nextLine();
            if(ans.equalsIgnoreCase("y")) return true;
            if(ans.equalsIgnoreCase("n")) return false;
        }
    }
    
}

