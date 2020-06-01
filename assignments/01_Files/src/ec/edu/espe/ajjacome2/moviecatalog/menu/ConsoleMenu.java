package ec.edu.espe.ajjacome2.moviecatalog.menu;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class to create a simple menu console handling options
 * 
 * @author jon_m
 */
public class ConsoleMenu {
    private String title;
    private boolean isRunning = true;
    private ArrayList<ConsoleMenuOption> options = new ArrayList<>();
    private static Scanner scanner;
    
    public ConsoleMenu(String title) {
        try {
            this.scanner = new Scanner(new InputStreamReader(System.in, "UTF8"));
        } catch (UnsupportedEncodingException exception) {
            this.scanner = new Scanner(System.in);
        }
        
        this.title = title;
    }
    
    public static Scanner getScanner() {
        return scanner;
    }
    
    public void addOption(ConsoleMenuOption option) {
        options.add(option);
    }
    
    public void display() {
        while (isRunning) {
            clearConsole();
            String headerText =
                "Andres Jacome - 01 Files Assignment\n\n" +
                "--------------------| " +
                title +
                " |------------------\n";
            int index = 0;
            
            System.out.print(headerText);            
            
            for (ConsoleMenuOption option : options) {
                System.out.printf("%d: %s", index + 1, option.getLabel());
                System.out.println();
                index++;
            }
            
            System.out.print(
                new String(new char[headerText.length()-1]).replace("\0", "-"));
            System.out.println();
            
            int indexChoosed;
            boolean isValidOption = false;
            
            do {
                System.out.print("Please enter an option: ");
                indexChoosed = scanner.nextInt();
                scanner.nextLine();
                isValidOption = (indexChoosed > 0 
                        && indexChoosed <= options.size());
            } while (!isValidOption);
            
            System.out.println();
            
            ConsoleMenuOption option = options.get(indexChoosed - 1);
            option.execute();
            
            if (option.mustAwait()) {
                System.out.print("\n\nPress <enter> to continue...");
                try {
                    System.in.read();
                } catch (Exception exception) {}
            }
        }
    }
    
    public void exit() {
        scanner.close();
        isRunning = false;
    }
    
    private void clearConsole() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            
            if (os.contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls")
                    .inheritIO().start().waitFor();
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (Exception exception) {}
    }
}
