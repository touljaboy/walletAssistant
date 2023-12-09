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
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;

//Class used to manage the flow of information in the app. It will be used to manage basic functionality.
//Classes will most likely be (idk yet) static so they can be used without creating the object of the class.
public class ExpenseAppManager {

    //Im doing the list below, it's place might be in environment, same with info about current user, I will
    //think about it later. Also, since I've introduced such static arraylist, it might be smart to refactor the code

    DataReader dataReader = new DataReader();
    ExpenseAnalyser expenseAnalyser = new ExpenseAnalyser();
    CsvFileManager csvFileManager = new CsvFileManager();
    Environment environment;

    //constructor for using the app in the terminal. Data is imported from file in constructor.
    public ExpenseAppManager() {
        try {
            environment = csvFileManager.initialDataImport();
            ConsolePrinter.printLine("Import użytkowników zakończony sukcesem");

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

            //Can't really decide if I want expenseTypes to be "public" in sense that every user shares the entered
            //expense types or not. I believe there are pros and cons to both solutions
            switch(option) {
                case EXIT -> exitProgram();
                case NEWENTRY -> addNewExpense();
                case DISPLAYAVERAGEEXPENSES -> displayAverageExpenses();
                case ADDUSER -> addNewUser();
                case DISPLAYEXPENSETYPES -> displayExpenseTypes();
                case ADDEXPENSETYPE -> addNewExpenseType();
                case REMOVEEXPENSETYPE -> removeExpenseType();
                case REMOVEEXPENSE -> removeExpense();
                case AVERAGEEXPENSEPERTYPE -> averageExpensesInGivenCategory();
                case PLOTAGRAPH -> plotAGraph();
                case REMOVEUSER -> removeUser();
                case DISPLAYALLEXPENSES -> displayExpenses();
                case DISPLAYUSERS -> displayUsers();
            }
        } while(option != Options.EXIT);
    }



    private void exitProgram() {
        try {
            csvFileManager.exportData();
            ConsolePrinter.printLine("Export danych do pliku zakończony powodzeniem");
        } catch (DataExportException e) {
            ConsolePrinter.printError(e.getMessage());
        }
        ConsolePrinter.printLine("Koniec programu ");
        dataReader.closeRead();
    }
    //This function speaks for itself - it reads information from appUser and creates a new Expense and
    //adds it to the expenses ArrayList
    private void addNewExpense() {
        try {
            ConsolePrinter.printLine("Podaj kwotę wydatku (oddzielone kropką): ");
            double value = dataReader.readDouble();
            displayExpenseTypes();
            ConsolePrinter.printLine("Podaj ID kategorii wydatku: ");
            ExpenseType expenseType = ExpenseType.createFromInt(dataReader.readInt());

            ConsolePrinter.printLine("Czy wydatek poniosłeś dzisiaj? Y/N");
            LocalDate localDate = LocalDate.now();

            String decision = dataReader.readLine();
            if(decision.equals("N")){
                localDate = createDateFromInput();
            }

            environment.addExpense(Environment.CURRENT_USER,new Expense(value,expenseType,localDate));

        } catch (InputMismatchException | NoSuchOptionException e) {
            ConsolePrinter.printError("Użyto błędnego typu danych. Wartość wydatku powinna być oddzielona kropką" +
                    ",np 55.43; id kategorii wydatku jest liczbą naturalną (upewnij się, że kategoria o danym id" +
                    "istnieje), w przypadku daty posługuj się wartościami naturalnymi zgodnymi z kalendarzem");
        }
    }

    private void displayAverageExpenses() {
        ConsolePrinter.printLine("0 - Wyświetl całościowe średnie wydatki");
        ConsolePrinter.printLine("1 - Wyświetl średnie wydatki dla danego przedziału czasu");
        int timePeriodChoice = dataReader.readInt();

        switch(timePeriodChoice) {
            case 0 ->displayAllAverageExpenses();
            case 1 ->displayTimePeriodAverageExpenses();
            default -> ConsolePrinter.printError("Nieznana Opcja");
        }
    }
    //TODO maybe write a function like doInTimePeriod(startDate,finishDate) and exec a method using lambda expressions
    private void displayTimePeriodAverageExpenses() {
        try {
            ConsolePrinter.printLine("Określ datę początkową w formacie yyyy-mm-dd");
            LocalDate startDate = getDate();
            ConsolePrinter.printLine("Określ datę końcową w formacie yyyy-mm-dd");
            LocalDate endDate = getDate();

            if (!Environment.getIfCurrAdmin()) {
                expenseAnalyser.printDateAverageExpensesPerUsername(startDate, endDate, Environment.CURRENT_USER);
            } else {
                int choice = allUsersOrOneUserDisplayExpensesChoice(); //0-->all, 1-->one
                if (choice == 0) {
                    for (User user : Environment.users) {
                        expenseAnalyser.printDateAverageExpensesPerUsername(startDate, endDate, user.getUsername());
                    }
                } else if (choice == 1) {
                    expenseAnalyser.printDateAverageExpensesPerUsername(startDate, endDate, getUsernameKey());

                } else {
                    ConsolePrinter.printError("Nieznana opcja");
                }
            }
        } catch (DateTimeParseException e) {
            ConsolePrinter.printError("Podano nieprawidłowy format daty");
        }
    }

    private void displayAllAverageExpenses() {
        if(!Environment.getIfCurrAdmin()) {
            for (ExpenseType expenseType : Environment.expenseTypes)
                expenseAnalyser.printAverageExpense(Environment.expenses.get(Environment.CURRENT_USER)
                        ,expenseType, Environment.CURRENT_USER);
        } else {
            ConsolePrinter.printLine("0 - Uwzględnij dane wszystkich użytkowników");
            ConsolePrinter.printLine("1 - Wyświetl średnie wydatki danego użytkownika");
            int choice = dataReader.readInt();
            if(choice==0) {
                //I could add this for loop in ExpenseAnalyser, but I cant find the reason why, its more readable now
                for (User user : Environment.users) {
                    expenseAnalyser.printAverageExpensesForUser
                            (Environment.expenses.get(user.getUsername()),user.getUsername());
                }
            } else if(choice==1) {
                String usernameKey = getUsernameKey();
                expenseAnalyser.printAverageExpensesForUser(Environment.expenses.get(usernameKey),usernameKey);
            } else {
                ConsolePrinter.printError("Nieznana opcja");
            }

        }
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
    private void displayExpenseTypes() {
        Environment.expenseTypes
                .forEach(value -> ConsolePrinter.printMenu(value.id(),value.toString()));
    }
    private void addNewExpenseType() {
        int id = Environment.expenseTypes.size();
        ConsolePrinter.printLine("Podaj opis nowej kategorii wydatku (max 15 znaków): ");
        String desc = dataReader.readLine();
        if(desc.length()<=15)
            Environment.expenseTypes.add(new ExpenseType(desc, id));
        else ConsolePrinter.printError
                ("Niepoprawna długość opisu kategorii (max 15 znaków, podano" + desc.length() + " znaków");
    }
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
                Environment.expenses.get(Environment.CURRENT_USER)
                        .removeIf(expense -> expense.getExpenseType().equals(expenseType));

                //Remove the expenseType
                Environment.expenseTypes.remove(expenseType);
            }
        }
    }
    private void removeExpense() {
        if(!Environment.getIfCurrAdmin()) {
            removeExpense(Environment.CURRENT_USER);
        } else {
            removeExpense(getUsernameKey());
        }
    }

    private void averageExpensesInGivenCategory() {
        ExpenseType expenseType;
        ConsolePrinter.printLine("Wybierz kategorię: ");
        displayExpenseTypes();
        expenseType = getExpenseType();

        expenseAnalyser.printAverageExpense(Environment.expenses.get(Environment.CURRENT_USER)
                , expenseType, Environment.CURRENT_USER);
    }

    //TODO maybe there is a way to avoid code repetition with getting dates. Consider creating a new class which
    // will be extended by expense app manager. Dates will be read in that class each time using 1 function
    private void plotAGraph() {
        try {
            ConsolePrinter.printLine("Określ datę początkową w formacie yyyy-mm-dd");
            LocalDate startDate = getDate();
            ConsolePrinter.printLine("Określ datę końcową w formacie yyyy-mm-dd");
            LocalDate endDate = getDate();

            if (!Environment.getIfCurrAdmin()) {
                expenseAnalyser.plotAFullGraph(startDate,endDate,Environment.CURRENT_USER);
            } else {
                expenseAnalyser.plotAFullGraph(startDate,endDate,getUsernameKey());
            }
        } catch (DateTimeParseException e) {
            ConsolePrinter.printError("Podano błędny format dat");
        }
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
        //You cannot delete your own user and therefore you cannot end up in a situation where there are no admin users
        if(Environment.users.get(choice).getUsername().equals(Environment.CURRENT_USER)) {
            ConsolePrinter.printError("Błąd! Próba usunięcia aktualnie zalogowanego użytkownika");
        } else {
            if(Environment.getIfCurrAdmin()) {
                ConsolePrinter.printLine("Zamierzasz usunąć użytkownika: " + Environment.users.get(choice) +
                        ". Czy chcesz kontynuować? (Y/N)");
                if (dataReader.readLine().equalsIgnoreCase("Y")) {
                    Environment.users.remove(choice);
                }
            } else {
                ConsolePrinter.printError("Nie masz uprawnień do usunięcia użytkownika");
            }
        }
    }

    private void displayExpenses() {
        //Display expenses from a given time period or all of them at once
        ConsolePrinter.printLine("0 - Wyświetl wszystkie wydatki");
        ConsolePrinter.printLine("1 - Wyświetl wydatki z danego przedziału czasu");
        int timePeriodChoice = dataReader.readInt();

        switch(timePeriodChoice) {
            case 0 ->displayAllExpenses();
            case 1 ->displayTimePeriodExpenses();
            default -> ConsolePrinter.printError("Nieznana Opcja");
        }
    }

    private LocalDate createDateFromInput() {
        ConsolePrinter.printLine("Podaj rok: ");
        int year = dataReader.readInt();
        ConsolePrinter.printLine("Podaj numer miesiąca: ");
        int month = dataReader.readInt();
        ConsolePrinter.printLine("Podaj dzień: ");
        int day = dataReader.readInt();
        return LocalDate.of(year,month,day);
    }
    private void displayTimePeriodExpenses() {
        try {
            ConsolePrinter.printLine("Określ datę początkową w formacie yyyy-mm-dd");
            LocalDate startDate = getDate();
            ConsolePrinter.printLine("Określ datę końcową w formacie yyyy-mm-dd");
            LocalDate endDate = getDate();

            if (!Environment.getIfCurrAdmin()) {
                printDateExpensesPerUsername(startDate, endDate, Environment.CURRENT_USER);
            } else {
                int choice = allUsersOrOneUserDisplayExpensesChoice(); //0-->all, 1-->one
                if (choice == 0) {
                    for (User user : Environment.users) {
                        printDateExpensesPerUsername(startDate, endDate, user.getUsername());
                    }
                } else if (choice == 1) {
                    printDateExpensesPerUsername(startDate, endDate, getUsernameKey());

                } else {
                    ConsolePrinter.printError("Nieznana opcja");
                }
            }
        } catch (DateTimeParseException e) {
            ConsolePrinter.printError("Podano nieprawidłowy format daty");
        }
    }
    private void displayAllExpenses() {
        if(!Environment.getIfCurrAdmin()) {
            Environment.sortExpenses(Environment.CURRENT_USER);
            Environment.expenses.get(Environment.CURRENT_USER)
                    .forEach(expense -> ConsolePrinter.printLine(expense.toString()));
        } else {
            int choice = allUsersOrOneUserDisplayExpensesChoice(); //0->all 1-->one
            if(choice==0) {
                for (User user : Environment.users) {
                    Environment.sortExpenses(user.getUsername());
                    Environment.expenses.get(user.getUsername())
                            .forEach(expense -> ConsolePrinter.printLine(expense.toString()));
                }
            } else if (choice==1) {
                String usernameKey = getUsernameKey();
                Environment.sortExpenses(usernameKey);
                Environment.expenses.get(usernameKey)
                        .forEach(expense -> ConsolePrinter.printLine(expense.toString()));
            } else {
                ConsolePrinter.printError("Nieznana opcja");
            }
        }
    }
    private LocalDate getDate() throws DateTimeParseException {
        String date = dataReader.readLine();
        return LocalDate.parse(date);
    }
    //Function used to print an expense based on user and date period parameters
    private void printDateExpensesPerUsername(LocalDate startDate, LocalDate endDate, String user) {
        //Sorting the arraylist before displaying
        Environment.sortExpenses(user);
        while(startDate.isBefore(endDate)) {
            for (int j = 0; j < Environment.expenses.get(user).size(); j++) {
                if (Environment.expenses.get(user).get(j)
                        .getDate().equals(startDate)) {
                    ConsolePrinter.printLine(Environment.expenses.get(user).get(j).toString());
                }
            }
            startDate = startDate.plusDays(1);
        }
    }

    private String getUsernameKey() {
        ConsolePrinter.printLine("Wybierz użytkownika (wpisz jego nazwę): ");
        displayUsers();
        return dataReader.readLine();
    }
    private int allUsersOrOneUserDisplayExpensesChoice() {
        ConsolePrinter.printLine("0 - Wyświetl wszystkie wydatki każdego z użytkowników");
        ConsolePrinter.printLine("1 - Wyświetl wydatki tylko konkretnego użytkownika");
        return dataReader.readInt();
    }

    //Remove a singular Expense entry. I believe it is better to first ask for a category from which an entry
    //will be deleted, and only then to ask which entry to delete. It would be problematic to find a
    //singular entry to delete if there are like 50000 entries, right?


    private void removeExpense(String username) {
        //If not an admin, then you can only delete your expenses
            ConsolePrinter.printLine("Podaj kategorię, w której chcesz usunąć wydatek: ");
            displayExpenseTypes();

            //ExpenseType chosen by user by its id
            ExpenseType expenseType = getExpenseType();

        List<Expense> expensesWithinTheCategory = Environment.expenses.get(username).stream()
                .filter(expense -> expense.getExpenseType().equals(expenseType)).toList();

        if(expensesWithinTheCategory.isEmpty()) {
            ConsolePrinter.printLine("Brak wydatków w danej kategorii");
        } else {
            ConsolePrinter.printLine("Wybierz wydatek do usunięcia: ");
            //display expenses within a given expenseType
            expensesWithinTheCategory.forEach(expense -> ConsolePrinter.printLine(Environment.expenses.
                    get(username).indexOf(expense)
                    + ": " + expense));
            //remove the expense based on user input of index
            int choice = dataReader.readInt();

            //are you sure you wish to delete the entry?
            //Alpha 1.01 note - found a bug, where you can delete expenses outside the chosen category.
            try {
                Expense toBeRemoved = Environment.expenses.get(username).get(choice);
                if (expensesWithinTheCategory.contains(toBeRemoved)) {
                    ConsolePrinter.printLine("Zamierzasz usunąć wydatek: " + toBeRemoved.toString() + ", " +
                            "Czy chcesz kontynuować? (Y/N)");
                    if (dataReader.readLine().equalsIgnoreCase("Y"))
                        Environment.expenses.get(username).remove(toBeRemoved);
                } else ConsolePrinter.printLine("Brak wydatku o podanym id w podanej kategorii");
            } catch(IndexOutOfBoundsException e){
                ConsolePrinter.printLine("Nieznaleziono wydatku o zadanym id");
            }
        }
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
            ConsolePrinter.printMenu(value.getValue(),value.toString());
        }
    }

    public void userLoginLoop() {
        boolean isCorrectUser = false;
        User user;
        while(!isCorrectUser) {
            user = loginCreateUser();
            if(!Environment.users.contains(user)) {
                ConsolePrinter.printError("Niepoprawny login i/lub hasło");
            }
            else {
                ConsolePrinter.printLine("Witaj, " + user.getUsername());
                isCorrectUser=true;
                Environment.CURRENT_USER = user.getUsername();
            }
        }
        csvFileManager.secondaryDataImport();
    }

    private User loginCreateUser() {
        ConsolePrinter.printLine("Podaj nazwę użytkownika");
        String username = dataReader.readLine();
        ConsolePrinter.printLine("Podaj hasło");
        String password = dataReader.readLine();
        boolean isAdmin = false;
        for (User user : Environment.users) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password))
                isAdmin = user.isAdmin();
        }
        return new User(username,password,isAdmin);
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
        DISPLAYALLEXPENSES(7, "Wyświetl wydatki"),
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
            return description;
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
