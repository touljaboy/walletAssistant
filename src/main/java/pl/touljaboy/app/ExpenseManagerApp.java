package pl.touljaboy.app;

//For now, I plan to test some basic features using the terminal before I move on to making it a GUI app.
public class ExpenseManagerApp {
    //Application version field
    public static final String APP_VERSION = "\nExpensesManager - 1.04 ALPHA";
    public static void main(String[] args) {
        ExpenseAppManager expenseAppManager = new ExpenseAppManager();
        expenseAppManager.userLoginLoop();
        expenseAppManager.controlLoop();
        //TODO recently, you have made some changes to the code and it aint so pretty now..Try and fix it please!
        //TODO release the final stable alpha, then begin beta - GUI and testing
        //TODO Beta needs to have a calendar
    }
}
