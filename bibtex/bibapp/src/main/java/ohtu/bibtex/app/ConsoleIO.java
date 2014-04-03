package ohtu.bibtex.app;

import java.util.Scanner;

/**
 *
 * @author Jouko Strömmer
 */
public class ConsoleIO {
    private final Scanner scanner = new Scanner(System.in);
    
    public void print(String toPrint) {
        System.out.println(toPrint);
    }

    public int readInt(String prompt) {
        System.out.print(prompt+" ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String readLine(String prompt) {
        System.out.print(prompt+" ");
        return scanner.nextLine();
    }
    
}

