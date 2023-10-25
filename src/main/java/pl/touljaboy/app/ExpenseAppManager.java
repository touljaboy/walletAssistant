package pl.touljaboy.app;

import pl.touljaboy.exception.NoSuchOptionException;
import pl.touljaboy.io.ConsolePrinter;
import pl.touljaboy.io.DataReader;

import java.util.InputMismatchException;
import java.util.Objects;

//Class used to manage the flow of information in the app. It will be used to manage basic functionality.
//Classes will most likely be (idk yet) static so they can be used without creating the object of the class.
public class ExpenseAppManager {
    ConsolePrinter printer = new ConsolePrinter();
    DataReader dataReader = new DataReader(printer);

    //constructor for using the app in the terminal


    public ExpenseAppManager() {
        printer.printLine("Expense Manager created!");
    }

    //the class is used in the alpha version of the program to print the options into the terminal
    public void controlLoop() {
        Options option;
        do {
            printer.printLine(ExpenseManagerApp.APP_VERSION);
            printOptions();
            option = getOption();
            switch(option) {
            //FILL IN THESE CASES, ADD FUNCTIONALITY
                case EXIT -> {
                    exitProgramm();
                }
                case NEWENTRY -> {
                }
                case DISPLAYAVERAGEEXPENSES -> {
                }
            }
        } while(option != Options.EXIT);
    }

    private void exitProgramm() {
        printer.printLine("Koniec programu ");
        dataReader.closeRead();
    }

    private Options getOption() {
        boolean isOkOption = false;
        Options option = null;
        while(!isOkOption) {
            try{
                option = Options.createFromInt(dataReader.readInt());
                isOkOption = true;
            } catch (NoSuchOptionException e) {
                printer.printLine(e.getMessage());
            } catch (InputMismatchException e) {
                printer.printLine("Wprowadzono wartość nieprawidłowego typu (nie jest liczbą całkowitą)");
            }
        }
        return option;
    }

    private void printOptions() {
        printer.printLine("Wybierz opcję: ");
        for(Options value : Options.values()) {
            printer.printLine(value.toString());
        }
    }

    //enum Option is created here temporarily to manage Options in the 'menu' in the alpha version of the program
    //I figure it can be declared here since a) it's used by this class and b) it a temporary solution
    public enum Options {
        EXIT(0,"Wyjście"),
        NEWENTRY(1, "Wprowadź nową transakcję"),
        DISPLAYAVERAGEEXPENSES(2, "Wyświetl dotychczasowe średnie wydatki/kategorie");
        private final int value;
        private final String description;

        Options(int value, String optionName) {
            this.value = value;
            this.description = optionName;
        }

        public int getValue() {
            return value;
        }
        public String getDescription() {
            return description;
        }
        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Options createFromInt(int choice) throws NoSuchOptionException {
            try {
                return Options.values()[choice];
            } catch (ArrayIndexOutOfBoundsException e){
                throw new NoSuchOptionException("Brak opcji o ID: " + choice);
            }
        }


    }
}
