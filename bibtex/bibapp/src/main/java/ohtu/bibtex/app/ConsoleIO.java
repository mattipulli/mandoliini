package ohtu.bibtex.app;

import java.util.Scanner;

/**
 * Some basic Console I/O methods
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

