package pl.touljaboy.io;

import pl.touljaboy.exception.DataExportException;
import pl.touljaboy.exception.DataImportException;
import pl.touljaboy.exception.NoSuchOptionException;
import pl.touljaboy.model.Expense;
import pl.touljaboy.model.ExpenseType;
import pl.touljaboy.model.User;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;

//It is a temporary app while using the CSV file type to store the application status. In future releases,
//I will definetely implement a MySQL type database (well, its the sql language I've learned and it works with
//java so why not)
public class CsvFileManager {
    private static final String EXPENSES_FILENAME = "walletAssistant.csv";
    private static final String USERDATA_FILENAME = "users.csv";
    private static final String EXPENSETYPES_FILENAME = "expenseTypes.csv";

    //Class used to importData from a file
    public void importData() {
        importUsers();
        importExpenseTypes();
        importExpenses();
    }

    private void importExpenseTypes() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(EXPENSETYPES_FILENAME));
            bufferedReader
                    .lines()
                    .map(this::createExpenseTypeFromString)
                    .forEach(ExpenseType::addExpenseType);
        } catch (FileNotFoundException e) {
            throw new DataImportException("NIEZNALEZIONO PLIKU: " + EXPENSETYPES_FILENAME);
        }
    }

    private ExpenseType createExpenseTypeFromString(String line) {
        String[] expenseTypesString = line.split(",");
        int id = Integer.parseInt(expenseTypesString[1]);
        String desc = expenseTypesString[0];
        return new ExpenseType(desc, id);
    }

    private void importExpenses() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(EXPENSES_FILENAME));
            bufferedReader
                    .lines()
                    .map(this::createExpenseFromString)
                    .forEach(Expense::addExpense);
        } catch (FileNotFoundException e) {
            throw new DataImportException("NIEZNALEZIONO PLIKU: " + EXPENSES_FILENAME);
        }
    }

    private void importUsers() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(USERDATA_FILENAME));
            bufferedReader.lines()
                    .map(this::createUserFromString)
                    .forEach(User::addUser);
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
        LocalDate date = LocalDate.parse(expenseStringArray[2]); //TODO TEST IT LATER
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
        Collection<ExpenseType> expenseTypes = ExpenseType.expenseTypes;
        exportToCSV(expenseTypes, EXPENSETYPES_FILENAME);
    }

    private void exportExpenses() {
        Collection<Expense> expenses = Expense.expenses;
        exportToCSV(expenses, EXPENSES_FILENAME);
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
