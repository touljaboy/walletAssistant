package pl.touljaboy.app;

import pl.touljaboy.io.ConsolePrinter;

//For now, I plan to test some basic features using the terminal before I move on to making it a GUI app.
public class ExpenseManagerApp {
    //Application version field
    public static final String APP_VERSION = "\nExpensesManager - 0.12 EARLY ALPHA";
    public static void main(String[] args) {
        ExpenseAppManager expenseAppManager = new ExpenseAppManager();

        expenseAppManager.userLoginLoop();
        expenseAppManager.controlLoop();
        //TODO after implementing the user logging and stuff functionality, we are officially in final alpha
        //TODO after creating the final alpha version, begin the testing process
        //TODO after testing, release the final stable alpha, then begin beta - GUI
    }
}
