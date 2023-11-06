package pl.touljaboy.app;

import pl.touljaboy.model.Expense;
import pl.touljaboy.model.ExpenseType;

import java.time.LocalDate;

//For now, I plan to test some basic features using the terminal before I move on to making it a GUI app.
//TODO add sample data to the csv files in 0.06 alpha
public class ExpenseManagerApp {
    //Application version field
    public static final String APP_VERSION = "\nExpensesManager - 0.05 EARLY ALPHA";
    public static void main(String[] args) {
        ExpenseAppManager expenseAppManager = new ExpenseAppManager();
        //Sample data for simple dimple testing in alpha, will use better tests in the future, now its
        //small development time

        ExpenseType.addExpenseType(new ExpenseType("CAR", 0));
        Expense expense = new Expense(130.7,ExpenseType.getExpenseTypes().get(0), LocalDate.now());
        Expense expense1 = new Expense(2009.33, ExpenseType.getExpenseTypes().get(0), LocalDate.now());
        Expense.expenses.add(expense);
        Expense.expenses.add(expense1);

        expenseAppManager.controlLoop();
        //TODO after creating the final alpha version, begin the testing process
    }
}
