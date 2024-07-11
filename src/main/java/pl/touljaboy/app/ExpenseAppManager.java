package pl.touljaboy.app;

import javafx.scene.control.Alert;
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

    CsvFileManager csvFileManager = new CsvFileManager();
    static Environment environment;

    //constructor for using the app in the terminal. Data is imported from file in constructor.
    public ExpenseAppManager() {

    }

    public void initialize() {
        try {
            environment = csvFileManager.initialDataImport();
            ConsolePrinter.printLine("Import użytkowników zakończony sukcesem");

        } catch (DataImportException e) {
            ConsolePrinter.printError(e.getMessage());
            environment = new Environment();
            ConsolePrinter.printLine("Zainicjowano nową bazę danych");
        }
    }


    //This function speaks for itself - it reads information from appUser and creates a new Expense and
    //adds it to the expenses ArrayList
    //UPDATED in BETA GUI 0.6
    public static void addNewExpense(Double value,ExpenseType expenseType,LocalDate localDate) {
            environment.addExpense(Environment.CURRENT_USER,new Expense(value,expenseType,localDate));
    }
    public static void addNewUser(String username, String password, boolean isAdmin) {
            environment.addUser(new User(username,password,isAdmin));
    }
    public static void addNewExpenseType(String desc) {
        int id = Environment.expenseTypes.size();
        if(desc.length()<=15)
            Environment.expenseTypes.add(new ExpenseType(desc, id));
        else ConsolePrinter.showErrorPopup
                ("Niepoprawna długość opisu kategorii (max 15 znaków, podano" + desc.length() + " znaków");
    }
}
