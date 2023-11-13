package pl.touljaboy.app;


import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
import pl.touljaboy.model.Environment;
import pl.touljaboy.model.Expense;
import pl.touljaboy.model.ExpenseType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//This section will be developed to perform basic analytical functions for now, and more complex ones in the future
public class ExpenseAnalyser {
    public static final String GNUPLOT_PATH = "/opt/homebrew/bin/gnuplot";
//TODO ADD MORE STATISTICS AND FUNCTIONALITY
    //The following task will surely take more than one day of work, since I've never done anything like it, but it
    //can be a nice code learning session. Later on, it MAY BE USEFUL IN GUI, but I dont know, maybe see if there
    //are any maven dependencies to ease this work for you

    //TODO well, it can be quite challenging, but find a way to plot a console graph and display the
    // graph of expenses within a given category within a given time period. javaplot!

    //Found a way to plot a graph using javaplot. It prerequires the gnuplot. This function wont be used in the
    //release version anyway, because JavaFX has some graph capabilities. I did it just to test myself and
    //do something different for once.

    //For now, plot a graph of ALL the data from the ArrayList Expense.expenses
    //TODO plot a graph for specific data range (using Date)
    //TODO see if there is a possibility to mark x-axis with dates, so the data presented on the graph makes more sense
    //TODO make it cleaner and more readible -> divide this code into functions

    //Okay, this function looks UGLY as HELL right now, but I promise I will make it better even though I
    //only do it for a hobby. As I've said, I will later use JavaFX, but who knows, maybe some of the things
    //I've learned here will be useful!
    public void plotAFullGraph() {
        ArrayList<Expense> temp = Environment.expenses;
        //date is initialized and used in later loop
        LocalDate date = Environment.expenses.get(0).getDate();

        //This code might look overly complicated for it's task and it truly is!
        //here is a graph of what is acomplished:
        //sort --> add values of elements with same date --> sort elements by date first, then by value, so element
        //with a unique date is always first of a sequence --> make the elements distinct, so only elements with
        //an entire sum of values (expenses) from a given day is left.

        //As I've noted, overly complicated probably, but it works and I assume it's the only solution since
        //I am losing my mind today with this code. Therefore:
        //TODO make it more readable and optimize

        //sorting from "Highest" to "Lowest" date
        temp.sort((o1, o2) -> {
            if(o1.getDate().isAfter(o2.getDate())) {
                return -1;
            } else if(o1.getDate().isBefore(o2.getDate())) {
                return 1;
            } else return Double.compare(o1.getValue(), o2.getValue());
        });

        for (int i = 1; i < temp.size(); i++) {
            if(temp.get(i).getDate().isEqual(date)) {
                temp.get(i).setValue(temp.get(i)
                        .getValue()+temp.get(i-1).getValue());
            }
            date = temp.get(i).getDate();
        }

        //Sorting from "Lowest" to "Highest" date. Don't know why yet, but it only works this way. Probably it
        //has something to do with how I engineered my loops, make it better later, im too tired with this now
        temp.sort((o1, o2) -> {
            if(o1.getDate().isAfter(o2.getDate())) {
                return 1;
            } else if(o1.getDate().isBefore(o2.getDate())) {
                return -1;
            } else return -Double.compare(o1.getValue(), o2.getValue());
        });

        //List of collected "Expense" items. Value of each element becomes the value presented on the graph
        //while every x iteration represents the following days.
        List<Expense> items = temp.stream().distinct().toList();
        double[][] points = new double[items.size()][2];

        //add expenses to double[][] Array, so it can be used with javaplot.
        for (int i = 0; i < items.size(); i++) {
            points[i][0] = i+1;
            points[i][1] = items.get(i).getValue();
        }

            JavaPlot graph = new JavaPlot(GNUPLOT_PATH);
            PlotStyle myPlotStyle = new PlotStyle();

            myPlotStyle.setStyle(Style.LINES);
            myPlotStyle.setLineWidth(1);
            DataSetPlot dataSetPlot = new DataSetPlot(points);
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

}
