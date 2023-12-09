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
    public static final String GNUPLOT_PATH = "/opt/homebrew/bin/gnuplot";
    Environment environment = new Environment();
    //The following task will surely take more than one day of work, since I've never done anything like it, but it
    //can be a nice code learning session. Later on, it MAY BE USEFUL IN GUI, but I dont know, maybe see if there
    //are any maven dependencies to ease this work for you

    //Found a way to plot a graph using javaplot. It prerequires the gnuplot. This function wont be used in the
    //release version anyway, because JavaFX has some graph capabilities. I did it just to test myself and
    //do something different for once.

    //For now, plot a graph of ALL the data from the ArrayList Expense.expenses
    //Okay, this function looks UGLY as HELL right now. As I've said, I will later use JavaFX,
    // but who knows, maybe some of the things I've learned here will be useful!
    //Note from version 0.10 alpha development: I found a bug - values of expenses of the same value
    //were basicly growing every time I ran the graph plotting function. I realised what had happened and frankly
    //I feel ashamed of myself - the answer was simple, it was the static ArrayList expenses. Well, I learned the hard
    //way why a reference to a static ArrayList DOESNT MEAN IT IS BEING CLONED!!! So I refactored the code to ommit
    //this problem and at the same time, I believe this solution is more readible anyway. Probably there is still
    //a better way to do this, but this function is useless in the long run, so why spend so much time on it?

    //TODO in GUI version, this function needs to have dates on x label or mark points with dates so data makes sense
    public void plotAFullGraph(LocalDate startDate, LocalDate endDate, String user) {
        List<Expense> expenses = Environment.expenses.get(user).stream().toList();
        List<LocalDate> uniqueDates = expenses
                .stream()
                .map(Expense::getDate)
                .distinct()
                .filter(localDate -> localDate.isAfter(startDate)&&localDate.isBefore(endDate))
                .toList();

        double[] values = new double[uniqueDates.size()];
        for(int i=0; i<uniqueDates.size(); i++) {
            for(int j=0; j<Environment.expenses.get(user).size(); j++) {
                if(expenses.get(j).getDate().isEqual(uniqueDates.get(i))) {
                    values[i] += expenses.get(j).getValue();
                }
            }
        }

        //Overly complicated probably, but it works and I assume it's the only solution since
        //I am losing my mind today with this code. Therefore:
        //you know what, this function wont be in the final version and I did it mostly for fun so:
        //dont do anything, spend your time more usefuly, thats the motto for today.

        double[][] points = new double[values.length][2];

        //add expenses to double[][] Array, so it can be used with javaplot.
        for (int i = 0; i < values.length; i++) {
            points[i][0] = i+1;
            points[i][1] = values[i];
        }

            JavaPlot graph = new JavaPlot(GNUPLOT_PATH);
            graph.set("xlabel", "'Dzień'");
            graph.set("ylabel", "'Kwota'");

            PlotStyle myPlotStyle = new PlotStyle();
            myPlotStyle.setStyle(Style.LINES);
            myPlotStyle.setLineWidth(1);

            DataSetPlot dataSetPlot = new DataSetPlot(points);
            dataSetPlot.setTitle("Średnie wydatki");
            dataSetPlot.setPlotStyle(myPlotStyle);

            graph.addPlot(dataSetPlot);
            graph.newGraph();
            graph.plot();

        }

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

    public void printAverageExpense(List<Expense> expenses, ExpenseType expenseType, String username) {
        String averageExpense = String
                .format("%-15s avg = %.2f",expenseType.description()
                        ,calculateAverageExpenses(expenses, expenseType, username));
        ConsolePrinter.printLine(averageExpense);
    }
    public void printAverageExpensesForUser(List<Expense> expenses, String username) {
            ConsolePrinter.printLine("Średnie wydatki użytkownika: "+ username);
            for (ExpenseType expenseType : Environment.expenseTypes) {
                printAverageExpense(expenses,expenseType,username);
            }
    }
    public void printDateAverageExpensesPerUsername(LocalDate startDate, LocalDate endDate, String user) {
        ArrayList<Expense> dateMatchExpenses = getDatePeriodExpenses(startDate,endDate,user);
        printAverageExpensesForUser(dateMatchExpenses,user);
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
