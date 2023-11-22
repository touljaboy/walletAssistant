package pl.touljaboy.model;

import pl.touljaboy.io.CSVConvertible;

import java.time.LocalDate;
import java.util.Objects;

//The class is used to represent single expenses and to store an ArrayList of those expenses.
public class Expense implements CSVConvertible {
    /*
    I got pounded by an idea to use a hashmap for
    storing expenses. I realise hashmap wont do, because I need multiple values for one key.
     Found out about Guava MultiMap. E U R E K A ! Implementing in early alpha 0.11
     */
    //Every expense has it's associated user. In the future, when SQL is used, username will be the key value for expense
    private double value;
    private ExpenseType expenseType;
    private LocalDate date;
    private String username;

    //I dont know if expenses need to store username String. Check it out
    //TODO storing the username here is an easy way out, but it is temporary. Need to remove it and use the
    // functionality of ListMultiMap. Learn how to truly access elements of an individual stored arraylist,
    // because somehow it didnt work earlier


    public Expense(double value, ExpenseType expenseType, LocalDate date, String username) {
        this.value = value;
        this.expenseType = expenseType;
        this.date = date;
        this.username = username;
    }

    public double getValue() {
        return value;
    }

    public String getUsername() {
        return username;
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


    @Override
    public String toCSV() {
        return value+","+
                expenseType.toCSV()+","+
                date+","+
                username;
    }

    @Override
    public String toString() {
        return "wartość: " + value +
                ", typ wydatku: " + expenseType.description() +
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
