package pl.touljaboy.app;

import pl.touljaboy.exception.DataExportException;
import pl.touljaboy.exception.DataImportException;
import pl.touljaboy.exception.NoSuchOptionException;
import pl.touljaboy.io.ConsolePrinter;
import pl.touljaboy.io.CsvFileManager;
import pl.touljaboy.io.DataReader;
import pl.touljaboy.model.Environment;
import pl.touljaboy.model.Expense;
import pl.touljaboy.model.ExpenseType;
import pl.touljaboy.model.User;

import java.time.LocalDate;
import java.util.InputMismatchException;

//Class used to manage the flow of information in the app. It will be used to manage basic functionality.
//Classes will most likely be (idk yet) static so they can be used without creating the object of the class.
public class ExpenseAppManager {
    DataReader dataReader = new DataReader();
    ExpenseAnalyser expenseAnalyser = new ExpenseAnalyser();
    CsvFileManager csvFileManager = new CsvFileManager();
    Environment environment;

    //constructor for using the app in the terminal. Data is imported from file in constructor.
    public ExpenseAppManager() {
        try {
            environment = csvFileManager.importData();
            ConsolePrinter.printLine("Import danych zakończony sukcesem");

        } catch (DataImportException e) {
            ConsolePrinter.printError(e.getMessage());
            environment = new Environment();
            ConsolePrinter.printLine("Zainicjowano nową bazę danych");
        }
    }

    //the class is used in the alpha version of the program to print the options into the terminal
    public void controlLoop() {
        Options option;
        do {
            ConsolePrinter.printLine(ExpenseManagerApp.APP_VERSION);
            printOptions();
            option = getOption();

            switch(option) {
                case EXIT -> exitProgramm();
                case NEWENTRY -> addNewExpense();
                case DISPLAYAVERAGEEXPENSES -> displayAverageExpenses();
                case ADDUSER -> addNewUser();
                case DISPLAYEXPENSETYPES -> displayExpenseTypes();
                case ADDEXPENSETYPE -> addNewExpenseType();
                case REMOVEEXPENSETYPE -> removeExpenseType();
                case REMOVEEXPENSE -> removeExpense();
                case AVERAGEEXPENSEPERTYPE -> averageExpensesInGivenCategory();
                case PLOTAGRAPH -> expenseAnalyser.plotAFullGraph();
                case REMOVEUSER -> removeUser();
                case DISPLAYALLEXPENSES -> displayExpenses();
                case DISPLAYUSERS -> displayUsers();
            }
        } while(option != Options.EXIT);
    }

    private void displayUsers() {
        for (int i = 0; i < Environment.users.size(); i++) {
            ConsolePrinter.printLine(i+": "+Environment.users.get(i));
        }
    }

    private void removeUser() {
        ConsolePrinter.printLine("Podaj nazwę użytkownika, którego chcesz usunąć. ");
        displayUsers();
        int choice = dataReader.readInt();
        //Ask the user if he really wants to delete the user.
        //TODO remmeber, you cannot delete your current user

        ConsolePrinter.printLine("Zamierzasz usunąć użytkownika: " + Environment.users.get(choice) +
                ". Czy chcesz kontynuować? (Y/N)");
        if(dataReader.readLine().equalsIgnoreCase("Y")) {
            Environment.users.remove(choice);
        }
    }

    private void displayExpenses() {
        for (Expense expense : environment.expenses) {
            ConsolePrinter.printLine(expense.toString());
        }
    }

    //Remove a singular Expense entry. I believe it is better to first ask for a category from which an entry
    //will be deleted, and only then to ask which entry to delete. It would be problematic to find a
    //singular entry to delete if there are like 50000 entries, right?
    private void removeExpense() {
        ConsolePrinter.printLine("Podaj kategorię, w której chcesz usunąć wydatek: ");
        displayExpenseTypes();

        //ExpenseType chosen by user by its id
        ExpenseType expenseType = getExpenseType();

        ConsolePrinter.printLine("Wybierz wydatek do usunięcia: ");
        //display expenses within a given expenseType
        environment.expenses.stream()
                .filter(expense -> expense.getExpenseType().equals(expenseType))
                .forEach(expense -> ConsolePrinter.printLine(environment.expenses.indexOf(expense)
                        + ": " + expense));

        //remove the expense based on user input of index (remove the index from arrayList expenses)
        int choice = dataReader.readInt();


        //are you sure you wish to delete the entry?
        ConsolePrinter.printLine("Zamierzasz usunąć wydatek: " + environment.expenses.get(choice).toString() +", " +
                "Czy chcesz kontynuować? (Y/N)");
        if(dataReader.readLine().equalsIgnoreCase("Y"))
            environment.expenses.remove(choice);
    }


    //Remove an entire expenseType together with associated expenses
    private void removeExpenseType() {
        //are you sure you wish to continue?
        ConsolePrinter.printLine("Usunięcie danego typu wydatku spowoduje usunięcie wszystkich wydatków danej" +
                "kategorii. Czy chcesz kontynuować? (Y/N)");
        if(dataReader.readLine().equalsIgnoreCase("Y")) {
            ConsolePrinter.printLine("Wybierz kategorię do usunięcia: ");
            displayExpenseTypes();

            //ExpenseType chosen by user by its id
            ExpenseType expenseType = getExpenseType();

            //are you really truly sure you wish to continue?
            ConsolePrinter.printLine("UWAGA! Zamierzasz usunąć kategorię "+expenseType.description() +" oraz całą" +
                    "jej zawartość. Czy chcesz kontynuować? (Y/N)");
            if(dataReader.readLine().equalsIgnoreCase("Y")) {

                //Remove expenses with the given category from the arraylist
                environment.expenses.removeIf(expense -> expense.getExpenseType().equals(expenseType));

                //Remove the expenseType
                Environment.expenseTypes.remove(expenseType);
            }
        }
    }

    //I know the name is sort of confusing, but this method essentialy calculates the average expenses ONLY from
    //the ExpenseType chosen by the user
    private void averageExpensesInGivenCategory() {
        ExpenseType expenseType;
            ConsolePrinter.printLine("Wybierz kategorię: ");
            displayExpenseTypes();
            expenseType = getExpenseType();

            expenseAnalyser.printAverageExpense(expenseType);
    }



    private void displayExpenseTypes() {
        Environment.expenseTypes
                .forEach(value -> ConsolePrinter.printLine(value.toString()));
    }

    private void addNewUser() {
        ConsolePrinter.printLine("Podaj nazwę użytkownika: ");
        String username = dataReader.readLine();

        ConsolePrinter.printLine("Podaj hasło: ");
        String password = dataReader.readLine();

        ConsolePrinter.printLine("Czy użytkownik jest adminem (Y/N)? ");
        String decision = dataReader.readLine().toLowerCase();
        switch(decision) {
            case "y" -> environment.addUser(new User(username,password,true));
            case "n" -> environment.addUser(new User(username,password,false));
            default -> ConsolePrinter.printError("Podano nieznaną komendę");
        }

    }

    private void addNewExpenseType() {
        ConsolePrinter.printLine("Podaj ID wydatku: ");
        int id = dataReader.readInt();

        ConsolePrinter.printLine("Podaj opis wydatku: ");
        String desc = dataReader.readLine();

        Environment.expenseTypes.add(new ExpenseType(desc, id));
    }

    private void displayAverageExpenses() {
        for (ExpenseType expenseType : Environment.expenseTypes)
            expenseAnalyser.printAverageExpense(expenseType);
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

            environment.addExpense(new Expense(value,expenseType,localDate));

        } catch (InputMismatchException | NoSuchOptionException e) {
            ConsolePrinter.printError("You used the wrong datatype! Use double (f.e. 55.43) for value," +
                    "int for id of an expenseType (make sure an expenseType with such an ID exists first) " +
                    ", and int/int/int for date");
        }
    }

    private void exitProgramm() {
        try {
            csvFileManager.exportData();
            ConsolePrinter.printLine("Export danych do pliku zakończony powodzeniem");
        } catch (DataExportException e) {
            ConsolePrinter.printError(e.getMessage());
        }
        ConsolePrinter.printLine("Koniec programu ");
        dataReader.closeRead();
    }

   //Implementing a parameter method to replace the two methods below would be a really nice to flex on your friends,
    //but it requires much refactoring (I tried to do this, not worth it for now)
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
    private ExpenseType getExpenseType() {
        boolean isOkExpenseType = false;
        ExpenseType expenseType = null;
        while(!isOkExpenseType) {
            try {
                expenseType = ExpenseType.createFromInt(dataReader.readInt());
                isOkExpenseType = true;
            } catch (NoSuchOptionException e) {
                ConsolePrinter.printError(e.getMessage());
            } catch (InputMismatchException e) {
                ConsolePrinter.printLine("Wprowadzono wartość nieprawidłowego typu (nie jest liczbą całkowitą)");
            }
        }
        return expenseType;
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
        ADDEXPENSETYPE(2,"Dodaj nową kategorię wydatków"),
        ADDUSER(3,"Dodaj nowego użytkownika"),
        REMOVEEXPENSE(4,"Usuń wpis wydatku"),
        REMOVEEXPENSETYPE(5,"Usuń daną kategorię wydaktu"),

        REMOVEUSER(6, "Usuń użytkownika"),
        DISPLAYALLEXPENSES(7, "Wyświetl wszystkie wydatki"),
        DISPLAYEXPENSETYPES(8,"Wyświetl dostępne kategorie wydatków"),
        DISPLAYUSERS(9, "Wyświetl nazwy użytkowników"),
        DISPLAYAVERAGEEXPENSES(10, "Wyświetl dotychczasowe średnie wydatki przez wszystkie kategorie"),
        AVERAGEEXPENSEPERTYPE(11, "Wyświetl dotychczasowe średnie wydatki tylko danej kategorii"),
        PLOTAGRAPH(12,"Utwórz wykres z dotychczasowych wydatków");

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
