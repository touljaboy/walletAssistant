package pl.touljaboy.model;

import pl.touljaboy.io.CSVConvertible;

import java.time.LocalDate;
import java.util.ArrayList;

//The class is used to represent single expenses and to store an ArrayList of those expenses.
public class Expense implements CSVConvertible {
    /*
    I believe that expenses arrayList will be useful in the future development. I make it public static, so that
    every class in the program can access it for the time being. I might find a better way in future development.
     */
    public static ArrayList<Expense> expenses = new ArrayList<>();
    private double value;
    private ExpenseType expenseType;
    private LocalDate date;

    public Expense(double value, ExpenseType expenseType, LocalDate date) {
        this.value = value;
        this.expenseType = expenseType;
        this.date = date;
    }
    public Expense() {

    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public ExpenseType getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(ExpenseType expenseType) {
        this.expenseType = expenseType;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public static void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public static ArrayList<Expense> getExpenses() {
        return expenses;
    }

    @Override
    public String toCSV() {
        return value+","+
                expenseType+","+
                date;
    }
}
