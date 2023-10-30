package pl.touljaboy.app;

import pl.touljaboy.model.Expense;
import pl.touljaboy.model.ExpenseType;

import java.time.LocalDate;

//For now, I plan to test some basic features using the terminal before I move on to making it a GUI app.
public class ExpenseManagerApp {
    //Application version field
    public static final String APP_VERSION = "\nExpensesManager - 0.04 EARLY ALPHA";
    public static void main(String[] args) {
        ExpenseAppManager expenseAppManager = new ExpenseAppManager();
        expenseAppManager.controlLoop();
        //TODO after creating the final alpha version, begin the testing process

//          I wanted to test the functionality of calculating average expenses. Maybe it will be useful later, so I
        // leave it here for now.
        /*
        ExpenseType.addExpenseType(new ExpenseType("CAR", 0));
        Expense expense = new Expense(130.7,ExpenseType.getExpenseTypes().get(0), LocalDate.now());
        Expense expense1 = new Expense(2009.33, ExpenseType.getExpenseTypes().get(0), LocalDate.now());
        Expense.expenses.add(expense);
        Expense.expenses.add(expense1);
         */

    }
}
