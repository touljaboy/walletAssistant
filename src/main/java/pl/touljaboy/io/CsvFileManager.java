package pl.touljaboy.io;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.io.Files;
import pl.touljaboy.exception.DataExportException;
import pl.touljaboy.exception.DataImportException;
import pl.touljaboy.exception.NoSuchOptionException;
import pl.touljaboy.model.Environment;
import pl.touljaboy.model.Expense;
import pl.touljaboy.model.ExpenseType;
import pl.touljaboy.model.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
    private String key;

    //Class used to importData from a file
    public void secondaryDataImport() {
        Environment environment = new Environment();
        importExpenseTypes(environment);
        importExpenses(environment);
    }

    public Environment initialDataImport() {
        Environment environment = new Environment();
        importUsers(environment);
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
                    .forEach(expense -> {
                        environment.addExpense(key, expense);
                    });
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
        key = expenseStringArray[4];
        return new Expense(value, expenseType, date);
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

    //TODO during export, first value should be the key of the Multimap
    private void exportExpenses() {
        ListMultimap<String, Expense> expenses = Environment.expenses;
        //need to add other userdata to the list, then save it. You might believe it's stupid and you might be just right
        //in thinking "dude, just use separate files for each user, why bother?"

        //Thing is, in the future, when I run the programm as an admin user, I want to have access TO ALL OF ENTERED DATA
        //not just my data --> thus, have access to some really epic statistics from all of the users, not just me.
        //At least, I am trying to achieve just that in a small console developed app without a SERVER storing user data
        //somewhere in Lithuania.

        //TODO read the above paragraph and implemented the functionality that only admin can see everyone's data
        exportToCSV(expenses, EXPENSES_FILENAME);
    }

    private void exportUsers() {
        Collection<User> users = Environment.users;
        exportToCSV(users, USERDATA_FILENAME);
    }

    private <T extends CSVConvertible> void exportToCSV(Collection<T> data, String filename) {
        try (var fileWriter = new FileWriter(filename, false);
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

    //Function used to export a Map type data structure to CSV.
    private <T extends CSVConvertible> void exportToCSV(Collection<T> data, String filename, String key) {
        try (var fileWriter = new FileWriter(filename, true);
             var bufferedWriter = new BufferedWriter(fileWriter))
        {
            for (T element : data) {
                bufferedWriter.write(element.toCSV());
                //Okay, now see, the above method and current method are virtually the same, except for the
                // line below and also the first line before the for loop and also that append is true here.
                //TODO try to find a way to avoid this brutal code repetition
                bufferedWriter.write(","+key);
                bufferedWriter.newLine();
            }

        } catch (IOException e) {
            throw new DataExportException("BŁĄD ZAPISU DO PLIKU: " + filename);
        }
    }

    //In case of my program, I only have a multimap holding Collection type objects,
    // so the code below will export it to CSV. Maybe there will be more such multimaps, so make a function
    private <T extends CSVConvertible> void exportToCSV(Multimap<String,T> data, String filename) {
        Multiset<String> tempKeys = data.keys();
        ArrayList<String> keys = new ArrayList<>(tempKeys);
        //list of distinct keys
        List<String> list = keys.stream().distinct().toList();
        clearFileContent(filename);
        for (int i = list.size()-1; i>=0; i--) {
            String tempKey=list.get(i);
            exportToCSV(data.get(tempKey), filename, tempKey);
        }
    }

    private void clearFileContent(String filename) {
        File file = new File(filename);
        byte[] empty = new byte[0];
        try {
            Files.write(empty, file);
        } catch (IOException e) {
            ConsolePrinter.printError("NIEZNALEZIONO PLIKU: " + filename);
        }
    }
}
