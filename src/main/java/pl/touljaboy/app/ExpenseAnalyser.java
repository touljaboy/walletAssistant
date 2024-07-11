package pl.touljaboy.app;


import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
import pl.touljaboy.io.ConsolePrinter;
import pl.touljaboy.model.Environment;
import pl.touljaboy.model.Expense;
import pl.touljaboy.model.ExpenseType;
import pl.touljaboy.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

//This section will be developed to perform basic analytical functions for now, and more complex ones in the future
public class ExpenseAnalyser {

    public double calculateAverageExpenses(List<Expense> expenses, ExpenseType expenseType, String username) {
        //sum the values of a given type
        //Predicate checking if username == current user and if expensetype.id == id of expensetype of a given expense
        Predicate<Expense> isExpenseTypeId = expense -> expense.
                getExpenseType().
                id()==expenseType.id();
        double sum = expenses.stream()
                .filter(isExpenseTypeId)
                .mapToDouble(Expense::getValue)
                .sum();
        //count the values of a given type
        long count = expenses.stream()
                .filter(isExpenseTypeId)
                .count();
        //return average
        return sum/count;
    }
    public ArrayList<Expense> getDatePeriodExpenses(LocalDate startDate, LocalDate endDate, String user) {
        ArrayList<Expense> dateMatchExpenses = new ArrayList<>();
        while(startDate.isBefore(endDate)) {
            for (int i = 0; i < Environment.expenses.get(user).size(); i++) {
                if(Environment.expenses.get(user).get(i).getDate().equals(startDate)) {
                    dateMatchExpenses.add(Environment.expenses.get(user).get(i));
                }
            }
            startDate = startDate.plusDays(1);
        }
        return dateMatchExpenses;
    }

}
