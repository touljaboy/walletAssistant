package pl.touljaboy.app;

import pl.touljaboy.model.Expense;
import pl.touljaboy.model.ExpenseType;

import java.time.LocalDate;

//For now, I plan to test some basic features using the terminal before I move on to making it a GUI app.
public class ExpenseManagerApp {
    //Application version field
    public static final String APP_VERSION = "\nExpensesManager - 0.10 EARLY ALPHA";
    public static void main(String[] args) {
        ExpenseAppManager expenseAppManager = new ExpenseAppManager();
        //Sample data for simple dimple testing in alpha, will use better tests in the future, now its
        //small development time

        expenseAppManager.controlLoop();

        //TODO after implementing the user logging and stuff functionality, we are officially in final alpha
        //TODO after creating the final alpha version, begin the testing process
        //TODO after testing, release the final stable alpha, then begin beta - GUI
    }
}
