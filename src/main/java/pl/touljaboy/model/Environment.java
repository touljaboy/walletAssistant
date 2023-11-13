package pl.touljaboy.model;

import pl.touljaboy.io.ConsolePrinter;

import java.util.ArrayList;
import java.util.List;

//Class used to store arrayLists of users, expenses and expenseTypes

public class Environment {
    public static ArrayList<Expense> expenses = new ArrayList<>();
    public static ArrayList<ExpenseType> expenseTypes = new ArrayList<>();
    public static List<User> users = new ArrayList<>();
    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public void addExpenseType(ExpenseType expenseType) {
        expenseTypes.add(expenseType);
    }
    public void removeExpense(int id) {
        //Well, I am pretty tired and I thought I will check if an element is present in arraylist this way
        //probably there is a very easy way to do this, but I need to get off the computer ASAP
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
