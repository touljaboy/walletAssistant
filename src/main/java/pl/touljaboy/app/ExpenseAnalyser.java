package pl.touljaboy.app;


import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
import pl.touljaboy.io.ConsolePrinter;
import pl.touljaboy.model.Environment;
import pl.touljaboy.model.Expense;
import pl.touljaboy.model.ExpenseType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    //TODO plot a graph for specific data range (using Date)

    //Okay, this function looks UGLY as HELL right now. As I've said, I will later use JavaFX,
    // but who knows, maybe some of the things I've learned here will be useful!
    //Note from version 0.10 alpha development: I found a bug - values of expenses of the same value
    //were basicly growing every time I ran the graph plotting function. I realised what had happened and frankly
    //I feel ashamed of myself - the answer was simple, it was the static ArrayList expenses. Well, I learned the hard
    //way why a reference to a static ArrayList DOESNT MEAN IT IS BEING CLONED!!! So I refactored the code to ommit
    //this problem and at the same time, I believe this solution is more readible anyway. Probably there is still
    //a better way to do this, but this function is useless in the long run, so why spend so much time on it?
    public void plotAFullGraph() {
        ArrayList<LocalDate> dates = new ArrayList<>();

        for (int i = 0; i < Environment.expenses.size(); i++) {
            dates.add(Environment.expenses.get(i).getDate());
        }

        List<LocalDate> uniqueDates = dates.stream().distinct().toList();
        double values[] = new double[uniqueDates.size()];
        for(int i=0; i<uniqueDates.size(); i++) {
            for(int j=0; j<Environment.expenses.size(); j++) {
                if(Environment.expenses.get(j).getDate().isEqual(uniqueDates.get(i))) {
                    values[i] += Environment.expenses.get(j).getValue();
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

    public double calculateAverageExpenses(ExpenseType expenseType) {
        //sum the values of a given type
        double sum = Environment.expenses.stream()
                .filter(expense -> expense.getExpenseType().id()==expenseType.id())
                .mapToDouble(Expense::getValue)
                .sum();
        //count the values of a given type
        long count = Environment.expenses.stream()
                .filter(expense -> expense.getExpenseType().id()==expenseType.id())
                .count();
        //return average
        return sum/count;
    }

    public void printAverageExpense(ExpenseType expenseType) {
        String averageExpense = String.format("%.2f",calculateAverageExpenses(expenseType));
        ConsolePrinter.printLine
                (expenseType.description() + " avg = " + averageExpense);
    }

}
