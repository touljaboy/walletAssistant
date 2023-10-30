package pl.touljaboy.app;

import pl.touljaboy.exception.NoSuchOptionException;
import pl.touljaboy.io.ConsolePrinter;
import pl.touljaboy.io.DataReader;
import pl.touljaboy.model.Expense;
import pl.touljaboy.model.ExpenseType;
import pl.touljaboy.model.User;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;

//Class used to manage the flow of information in the app. It will be used to manage basic functionality.
//Classes will most likely be (idk yet) static so they can be used without creating the object of the class.
public class ExpenseAppManager {
    DataReader dataReader = new DataReader();
    ExpenseAnalyser expenseAnalyser = new ExpenseAnalyser();

    //constructor for using the app in the terminal
    public ExpenseAppManager() {
        ConsolePrinter.printLine("Expense Manager created!");
    }

    //the class is used in the alpha version of the program to print the options into the terminal
    public void controlLoop() {
        Options option;
        do {
            ConsolePrinter.printLine(ExpenseManagerApp.APP_VERSION);
            printOptions();
            option = getOption();

            switch(option) {
            //TODO FILL IN THESE CASES, ADD MORE FUNCTIONALITY
                //TODO I think you should add a functionality to display
                // all expenses of a given kind, including all in 0.5
                case EXIT -> exitProgramm();
                case NEWENTRY -> addNewExpense();
                case DISPLAYAVERAGEEXPENSES -> displayAverageExpenses();
                case ADDUSER ->{
                    addNewUser();
                }
                case DISPLAYEXPENSETYPES -> ExpenseType.expenseTypes
                        .forEach(value -> ConsolePrinter.printLine(value.toString()));
                case ADDEXPENSETYPE -> {
                    addNewExpenseType();
                }
                case REMOVEEXPENSETYPE -> {
                    //TODO add in version 0.5
                }
                case REMOVEEXPENSE -> {
                    //TODO add in version 0.5
                }
            }
        } while(option != Options.EXIT);
    }

    private void addNewUser() {
        ConsolePrinter.printLine("Podaj nazwę użytkownika: ");
        String username = dataReader.readLine();

        ConsolePrinter.printLine("Podaj hasło: ");
        String password = dataReader.readLine();

        ConsolePrinter.printLine("Czy użytkownik jest adminem (Y/N)? ");
        String decision = dataReader.readLine().toLowerCase();
        switch(decision) {
            case "y" -> User.addUser(new User(username,password,true));
            case "n" -> User.addUser(new User(username,password,false));
            default -> ConsolePrinter.printError("Podano nieznaną komendę");
        }

    }

    private void addNewExpenseType() {
        ConsolePrinter.printLine("Podaj ID wydatku: ");
        int id = dataReader.readInt();

        ConsolePrinter.printLine("Podaj opis wydatku: ");
        String desc = dataReader.readLine();

        ExpenseType.expenseTypes.add(new ExpenseType(desc, id));
    }

    private void displayAverageExpenses() {
        for (ExpenseType expenseType : ExpenseType.expenseTypes) {
            String entry = String.format("%.2f",expenseAnalyser.calculateAverageExpenses(expenseType));
            ConsolePrinter.printLine
                    (expenseType.getDescription() + " avg = " + entry);
        }
    }

    //This function speaks for itself - it reads information from appUser and creates a new Expense and
    //adds it to the expenses ArrayList
    private void addNewExpense() {
        try {
            ConsolePrinter.printLine("Podaj kwotę wydatku (oddzielone kropką): ");
            double value = dataReader.readDouble();

            ConsolePrinter.printLine("Podaj ID kategorii wydatku: ");
            ExpenseType expenseType = ExpenseType.createFromInt(dataReader.readInt());

            ConsolePrinter.printLine("Czy wydatek poniosłeś dzisiaj? Y/N");
            LocalDate localDate = LocalDate.now();

            String decision = dataReader.readLine();
                if(decision.equals("N")){
                    ConsolePrinter.printLine("Podaj rok: ");
                    int year = dataReader.readInt();
                    ConsolePrinter.printLine("Podaj numer miesiąca: ");
                    int month = dataReader.readInt();
                    ConsolePrinter.printLine("Podaj dzień: ");
                    int day = dataReader.readInt();
                    localDate = LocalDate.of(year,month,day);
                }

            Expense.addExpense(new Expense(value,expenseType,localDate));

        } catch (InputMismatchException | NoSuchOptionException e) {
            ConsolePrinter.printError("You used the wrong datatype! Use double (f.e. 55.43) for value," +
                    "int for id of an expenseType (make sure an expenseType with such an ID exists first) " +
                    ", and int/int/int for date");
        }
    }

    private void exitProgramm() {
        ConsolePrinter.printLine("Koniec programu ");
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
                ConsolePrinter.printLine(e.getMessage());
            } catch (InputMismatchException e) {
                ConsolePrinter.printLine("Wprowadzono wartość nieprawidłowego typu (nie jest liczbą całkowitą)");
            }
        }
        return option;
    }

    private void printOptions() {
        ConsolePrinter.printLine("Wybierz opcję: ");
        for(Options value : Options.values()) {
            ConsolePrinter.printLine(value.toString());
        }
    }

    //enum Option is created here temporarily to manage Options in the 'menu' in the alpha version of the program
    //I figure it can be declared here since a) it's used by this class and b) it a temporary solution
    public enum Options {
        EXIT(0,"Wyjście"),
        NEWENTRY(1, "Wprowadź nową transakcję"),
        DISPLAYAVERAGEEXPENSES(2, "Wyświetl dotychczasowe średnie wydatki przez kategorię"),
        ADDUSER(3,"Dodaj nowego użytkownika"),
        DISPLAYEXPENSETYPES(4,"Wyświetl dostępne kategorie wydatków"),
        ADDEXPENSETYPE(5,"Dodaj nową kategorię wydatków"),
        REMOVEEXPENSETYPE(6,"Usuń daną kategorię wydaktu"),
        REMOVEEXPENSE(7,"Usuń wpis wydatku");
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
