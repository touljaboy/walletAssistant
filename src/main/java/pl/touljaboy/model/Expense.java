package pl.touljaboy.model;

import java.util.ArrayList;
import java.util.Date;

//The class is used to represent single expenses and to store an ArrayList of those expenses.
public class Expense {
    /*
    I believe that expenses arrayList will be useful in the future development. I make it public static, so that
    every class in the program can access it for the time being. I might find a better way in future development.
     */
    public static ArrayList<Expense> expenses = new ArrayList<>();
    private double value;
    private ExpenseType expenseType;
    private Date date;

    public Expense(double value, ExpenseType expenseType, Date date) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
