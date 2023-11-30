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
    public static ListMultimap<String, Expense> expenses = ArrayListMultimap.create();
    public static ArrayList<ExpenseType> expenseTypes = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
    //I declare it like static values, because it looks nicer, it's almost a STATIC FINAL
    public static String CURRENT_USER;
    public void addExpense(String key, Expense expense) {
        expenses.put(key, expense);
    }

    public void addExpenseType(ExpenseType expenseType) {
        expenseTypes.add(expenseType);
    }

    public void addUser(User user) {
        if(users.contains(user))
            ConsolePrinter.printLine("Użytkownik o podanej nazwie już istnieje! ");
        else users.add(user);
    }
    public static boolean getIfCurrAdmin() {
        //currIndex = -1, it will create an error if user is not found (which is impossible anyway), function will
        //return an error
        int currIndex = -1;
        for (int i = 0; i < users.size(); i++) {
            if(CURRENT_USER.equals(users.get(i).getUsername())) {
               currIndex=i;
            }
        }
        return users.get(currIndex).isAdmin();
    }
}
