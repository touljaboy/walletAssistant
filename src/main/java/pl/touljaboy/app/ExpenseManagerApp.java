package pl.touljaboy.app;

//For now, I plan to test some basic features using the terminal before I move on to making it a GUI app.
public class ExpenseManagerApp {
    //Application version field
    public static final String APP_VERSION = "\nExpensesManager - 0.2 GUI branch Early Beta";
    public static void main(String[] args) {
        ExpenseAppManager expenseAppManager = new ExpenseAppManager();
        expenseAppManager.userLoginLoop();
        expenseAppManager.controlLoop();
        //TODO divide the manePane into separate panes
        //TODO begin beta - GUI and testing (different branch)
        //TODO Beta needs to have a calendar
    }
}
