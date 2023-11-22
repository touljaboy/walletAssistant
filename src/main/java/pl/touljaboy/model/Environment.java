package pl.touljaboy.model;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.ArrayListMultimap;
import pl.touljaboy.app.ExpenseAppManager;
import pl.touljaboy.io.ConsolePrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

//Class used to store arrayLists of users and expenseTypes
//AND ALSO SINCE early alpha 0.11 - to store MultiMap of expenses

public class Environment {
    //Predicate to check if expense.getUser equals CURRENT_USER. Do not know if it will function well for now, but
    //TODO make it function well in all cases (see calculate average in expenseanalyser)
    public static final Predicate<Expense> IS_CURRENT_USER = expense -> ExpenseAppManager.CURRENT_USER
            .equals(expense.getUsername());
    public static ListMultimap<String, Expense> expenses = ArrayListMultimap.create();
    public static ArrayList<ExpenseType> expenseTypes = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
    public void addExpense(Expense expense) {
        expenses.put(ExpenseAppManager.CURRENT_USER, expense);
    }

    public void addExpenseType(ExpenseType expenseType) {
        expenseTypes.add(expenseType);
    }
    public void removeExpense(int id) {
        boolean isIdPresentInArrayList =
                expenseTypes.stream().filter(expenseType -> expenseType.id()==id).count()==1;
        if(isIdPresentInArrayList)
            expenseTypes.removeIf(expenseType -> expenseType.id()==id);
        else ConsolePrinter.printError("Nieznaleziono elementu o podanym ID");
    }

    public void addUser(User user) {
        if(users.contains(user))
            ConsolePrinter.printLine("Użytkownik o podanej nazwie już istnieje! ");
        else users.add(user);
    }
}
