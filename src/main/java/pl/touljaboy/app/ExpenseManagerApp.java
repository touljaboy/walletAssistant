package pl.touljaboy.app;

import pl.touljaboy.model.Expense;
import pl.touljaboy.model.ExpenseType;

import java.time.LocalDate;

//For now, I plan to test some basic features using the terminal before I move on to making it a GUI app.
//TODO add sample data to the csv files in 0.07 alpha
public class ExpenseManagerApp {
    //Application version field
    public static final String APP_VERSION = "\nExpensesManager - 0.06 EARLY ALPHA";
    public static void main(String[] args) {
        ExpenseAppManager expenseAppManager = new ExpenseAppManager();
        //Sample data for simple dimple testing in alpha, will use better tests in the future, now its
        //small development time

        ExpenseType.addExpenseType(new ExpenseType("CAR", 0));
        ExpenseType.addExpenseType(new ExpenseType("POWER", 1));
        Expense expense = new Expense(130.7,ExpenseType.expenseTypes.get(0), LocalDate.of(2023,11,7));
        Expense expense1 = new Expense(2009.33, ExpenseType.expenseTypes.get(0), LocalDate.of(2023,11,7));
        Expense expense2 = new Expense
                (1444.22, ExpenseType.expenseTypes.get(1), LocalDate.of(2022,10,3));
        Expense expense3 = new Expense
                (1744, ExpenseType.expenseTypes.get(1), LocalDate.of(2022,10,3));
        Expense expense4 = new Expense
                (5200, ExpenseType.expenseTypes.get(0), LocalDate.of(2013,6,12));
        Expense.expenses.add(expense);
        Expense.expenses.add(expense1);
        Expense.expenses.add(expense2);
        Expense.expenses.add(expense3);
        Expense.expenses.add(expense4);


        expenseAppManager.controlLoop();
        //TODO after creating the final alpha version, begin the testing process
    }
}
