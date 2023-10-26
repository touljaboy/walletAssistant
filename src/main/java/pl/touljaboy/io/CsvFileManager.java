package pl.touljaboy.io;

import pl.touljaboy.exception.DataExportException;
import pl.touljaboy.exception.DataImportException;
import pl.touljaboy.model.Expense;
import pl.touljaboy.model.ExpenseType;
import pl.touljaboy.model.User;

import java.io.*;
import java.util.Collection;
import java.util.Date;

//It is a temporary app while using the CSV file type to store the application status. In future releases,
//I will definetely implement a MySQL type database (well, its the sql language I've learned and it works with
//java so why not)
public class CsvFileManager {
    private static final String APPDATA_FILENAME = "walletAssistant.csv";
    private static final String USERDATA_FILENAME = "users.csv";

    //Class used to importData from a file
    public void importData() {
        Expense expense = new Expense();
        importUsers(expense);
        importExpenses(expense);
    }

    private void importExpenses(Expense expense) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(APPDATA_FILENAME));
            bufferedReader
                    .lines()
                    .map(this::createExpenseFromString)
                    .forEach(Expense::addExpense);
        } catch (FileNotFoundException e) {
            throw new DataImportException("NIE ZNALEZIONO PLIKU: " +APPDATA_FILENAME);
        }
    }

    private void importUsers(Expense expense) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(USERDATA_FILENAME));
            bufferedReader.lines()
                    .map(this::createUserFromString)
                    .forEach(User::addUser);
        } catch (FileNotFoundException e) {
            throw new DataImportException("NIE ZNALEZIONO PLIKU: " + USERDATA_FILENAME);
        }
    }

    private Expense createExpenseFromString(String line) {
        String[] expenseStringArray = line.split(",");
        double value = Double.parseDouble(expenseStringArray[0]);
        ExpenseType expenseType = ExpenseType.valueOf(expenseStringArray[1]);
        String date = expenseStringArray[2];
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
        exportExpenses();
    }

    private void exportExpenses() {
        Collection<Expense> expenses = Expense.getExpenses();
        exportToCSV(expenses, APPDATA_FILENAME);
    }

    private void exportUsers() {
        Collection<User> users = User.getUsers();
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
