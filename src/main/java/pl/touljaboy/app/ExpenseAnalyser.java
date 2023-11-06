package pl.touljaboy.app;


import pl.touljaboy.model.Expense;
import pl.touljaboy.model.ExpenseType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

//This section will be developed to perform basic analytical functions for now, and more complex ones in the future
public class ExpenseAnalyser {
//TODO ADD MORE STATISTICS AND FUNCTIONALITY
    //The following task will surely take more than one day of work, since I've never done anything like it, but it
    //can be a nice code learning session. Later on, it MAY BE USEFUL IN GUI, but I dont know, maybe see if there
    //are any maven dependencies to ease this work for you
    //TODO well, it can be quite challenging, but find a way to plot a console graph and display the
    // graph of expenses within a given category within a given time period.


    public double calculateAverageExpenses(ExpenseType expenseType) {
        //sum the values of a given type
        double sum = Expense.expenses.stream()
                .filter(expense -> expense.getExpenseType().getId()==expenseType.getId())
                .mapToDouble(Expense::getValue)
                .sum();
        //count the values of a given type
        long count = Expense.expenses.stream()
                .filter(expense -> expense.getExpenseType().getId()==expenseType.getId())
                .count();
        //return average
        return sum/count;
    }
}
