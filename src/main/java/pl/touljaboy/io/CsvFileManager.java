package pl.touljaboy.io;

import pl.touljaboy.app.ExpenseAppManager;
import pl.touljaboy.exception.DataExportException;
import pl.touljaboy.exception.DataImportException;
import pl.touljaboy.exception.NoSuchOptionException;
import pl.touljaboy.model.Environment;
import pl.touljaboy.model.Expense;
import pl.touljaboy.model.ExpenseType;
import pl.touljaboy.model.User;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

//It is a temporary app while using the CSV file type to store the application status. In future releases,
//I will definetely implement a MySQL type database (well, its the sql language I've learned and it works with
//java so why not)
public class CsvFileManager {
    Environment environment;
    private static final String EXPENSES_FILENAME = "walletAssistant.csv";
    private static final String USERDATA_FILENAME = "users.csv";
    private static final String EXPENSETYPES_FILENAME = "expenseTypes.csv";

    //Class used to importData from a file
    public Environment importData() {
        Environment environment = new Environment();
        importUsers(environment);
        importExpenseTypes(environment);
        importExpenses(environment);
        return environment;
    }

    private void importExpenseTypes(Environment environment) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(EXPENSETYPES_FILENAME));
            bufferedReader
                    .lines()
                    .map(this::createExpenseTypeFromString)
                    .forEach(environment::addExpenseType);
        } catch (FileNotFoundException e) {
            throw new DataImportException("NIEZNALEZIONO PLIKU: " + EXPENSETYPES_FILENAME);
        }
    }

    private ExpenseType createExpenseTypeFromString(String line) {
        String[] expenseTypesString = line.split(",");
        int id = Integer.parseInt(expenseTypesString[0]);
        String desc = expenseTypesString[1];
        return new ExpenseType(desc, id);
    }

    private void importExpenses(Environment environment) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(EXPENSES_FILENAME));
            bufferedReader
                    .lines()
                    .map(this::createExpenseFromString)
                    .forEach(environment::addExpense);
        } catch (FileNotFoundException e) {
            throw new DataImportException("NIEZNALEZIONO PLIKU: " + EXPENSES_FILENAME);
        }
    }

    private void importUsers(Environment environment) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(USERDATA_FILENAME));
            bufferedReader.lines()
                    .map(this::createUserFromString)
                    .forEach(environment::addUser);
        } catch (FileNotFoundException e) {
            throw new DataImportException("NIEZNALEZIONO PLIKU: " + USERDATA_FILENAME);
        }
    }

    private Expense createExpenseFromString(String line) {
        String[] expenseStringArray = line.split(",");
        double value = Double.parseDouble(expenseStringArray[0]);
        ExpenseType expenseType = null;
        try {
            expenseType = ExpenseType.createFromInt(Integer.parseInt(expenseStringArray[1]));
        } catch (NoSuchOptionException e) {
            ConsolePrinter.printError("BŁĄD! Próba wczytania opcji o nieistniejącym id - czy na pewno" +
                    "expenseTypes zostały wczytane poprawnie?");
        }
        LocalDate date = LocalDate.parse(expenseStringArray[3]);
        String username = expenseStringArray[4];
        return new Expense(value, expenseType, date, username);
    }
    private User createUserFromString(String line) {
        String[] usersStringArray = line.split(",");
        String username = usersStringArray[0];
        String password = usersStringArray[1];
        boolean isAdmin = Boolean.parseBoolean(usersStringArray[2]);
        return new User(username,password,isAdmin);
    }

    //class used to store the current application data into a .csv file
    public void exportData() {
        exportUsers();
        exportExpenseTypes();
        exportExpenses();
    }

    private void exportExpenseTypes() {
        Collection<ExpenseType> expenseTypes = Environment.expenseTypes;
        exportToCSV(expenseTypes, EXPENSETYPES_FILENAME);
    }

    private void exportExpenses() {
        Collection<Expense> expenses = new java.util.ArrayList<>(Environment.expenses.values().stream().toList());

        //need to add other userdata to the list, then save it. You might believe it's stupid and you might be just right
        //in thinking "dude, just use separate files for each user, why bother?"

        //Thing is, in the future, when I run the programm as an admin user, I want to have access TO ALL OF ENTERED DATA
        //not just my data --> thus, have access to some really epic statistics from all of the users, not just me.
        //At least, I am trying to achieve just that in a small console developed app without a SERVER storing user data
        //somewhere in Lithuania.

        //TODO read the above paragraph and implemented the functionality that only admin can see everyone's data
        for (User user : Environment.users) {
            if(!user.getUsername().equals(ExpenseAppManager.CURRENT_USER)) {
                expenses.addAll(Environment.expenses.get(user.getUsername()));
            }
        }
        exportToCSV(expenses, EXPENSES_FILENAME);
    }

    private void exportUsers() {
        Collection<User> users = Environment.users;
        exportToCSV(users, USERDATA_FILENAME);
    }

    private <T extends CSVConvertible> void exportToCSV(Collection<T> data, String filename) {
        try (var fileWriter = new FileWriter(filename);
            var bufferedWriter = new BufferedWriter(fileWriter))
        {
            for (T element : data) {
                bufferedWriter.write(element.toCSV());
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            throw new DataExportException("BŁĄD ZAPISU DO PLIKU: " + filename);
        }

    }
}
