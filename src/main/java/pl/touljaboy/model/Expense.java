package pl.touljaboy.model;

import pl.touljaboy.io.CSVConvertible;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

//TODO consider creating a class called Environment - the class will store all of the static ArrayLists from
// Users, ExpenseTypes and Expenses. I believe it will look better and clearer (especially when it comes to data import)


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

    @Override
    public String toCSV() {
        return value+","+
                expenseType.toCSV()+","+
                date;
    }

    @Override
    public String toString() {
        return "wartość: " + value +
                ", typ wydatku: " + expenseType.getDescription() +
                ", data: " + date;
    }


    //Expenses are compared by date. I had to use these overriden functions only in one instance so far (when presenting
    //a graphical interpretation). So long it works, it's not stupid!
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return date.isEqual(expense.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
