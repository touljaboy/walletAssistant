package pl.touljaboy.app;

import pl.touljaboy.io.ConsolePrinter;

//For now, I plan to test some basic features using the terminal before I move on to making it a GUI app.
public class ExpenseManagerApp {
    //Application version field
    public static final String APP_VERSION = "\nExpensesManager - 0.13 EARLY ALPHA";
    public static void main(String[] args) {
        ExpenseAppManager expenseAppManager = new ExpenseAppManager();
        expenseAppManager.userLoginLoop();
        expenseAppManager.controlLoop();
        //TODO recently, you have made some changes to the code and it aint so pretty now..Try and fix it please!

        //TODO after implementing the user logging and stuff functionality, we are officially in final alpha
        //TODO after creating the final alpha version, begin the testing process
        //TODO after testing, release the final stable alpha, then begin beta - GUI
    }
}
