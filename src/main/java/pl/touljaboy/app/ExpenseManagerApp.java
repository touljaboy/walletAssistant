package pl.touljaboy.app;

//For now, I plan to test some basic features using the terminal before I move on to making it a GUI app.
public class ExpenseManagerApp {
    //Application version field
    public static final String APP_VERSION = "\nExpensesManager - 0.03 EARLY ALPHA";
    public static void main(String[] args) {
        //for now, I develop the code in the class. I will implement it later to a separate class and use a function
        ExpenseAppManager expenseAppManager = new ExpenseAppManager();
        expenseAppManager.controlLoop();
    }
}
