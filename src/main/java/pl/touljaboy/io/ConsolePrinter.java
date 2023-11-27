package pl.touljaboy.io;

//Having a separate console printer class, rather than declaring systemoutprint every time, allows me to have
//more control over the printed text. I can, for example, convert all the text to uppercase with no problem
//right here!
public class ConsolePrinter {
    public static void printLine(String line) {
        System.out.println(line);
    }
    public static void printMenu(int id, String line) {
        String format = "%2d - %-50s%n";
        System.out.printf(format,id,line);
    }
    public static void printError(String line) {
        System.err.println(line);
    }
}
