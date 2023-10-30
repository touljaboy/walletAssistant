package pl.touljaboy.app;


import pl.touljaboy.model.Expense;
import pl.touljaboy.model.ExpenseType;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

//This section will be developed to perform basic analytical functions for now, and more complex ones in the future
public class ExpenseAnalyser {
//TODO ADD MORE STATISTICS AND FUNCTIONALITY
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
