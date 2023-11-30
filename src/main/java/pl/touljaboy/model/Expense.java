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


    @Override
    public String toCSV() {
        return value+","+
                expenseType.toCSV()+","+
                date;
    }


    //TODO I believe that each String format should be editable by the user in future release
    @Override
    public String toString() {
        String format = String.format
                ("|wartość: %10.2f | kategoria: %-15s | data: %s|", value, expenseType.description(), date);
        return format;
    }
}
