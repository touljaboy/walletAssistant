package pl.touljaboy.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.touljaboy.app.ExpenseAppManager;
import pl.touljaboy.model.Environment;
import pl.touljaboy.model.Expense;
import pl.touljaboy.model.ExpenseType;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

//TODO graphs should dynamically change based on user chocie (pie chart, line graph). Alternatively, just display both.
public class ExpensePaneController {

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private MenuItem addUserMenuItem;

    @FXML
    private Button calculateAverageButton;
    @FXML
    private Button backButton;

    @FXML
    private Button changeGraphStyleButton;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private Button deleteEntryButton;

    @FXML
    private MenuItem dirMenuItem;

    @FXML
    private LineChart<String, Double> expenseLineChart;

    @FXML
    private MenuItem logOutMenuItem;

    @FXML
    private MenuItem modUserMenuItem;

    @FXML
    private Button newEntryButton;

    @FXML
    private Button newGraphButton;

    @FXML
    private MenuItem newSourceMenuItem;

    @FXML
    private CategoryAxis timeAxis;

    @FXML
    private NumberAxis valueAxis;
    //Items regarding adding an expense

    public void initialize() {
        configureGraph();
        configureButtons();
    }

    //TODO make plotting available for different dates and clean the code to a different function
    //TODO there are numerous things you can do with javafx charts, check it out:
    // https://docs.oracle.com/javafx/2/charts/line-chart.htm
    private void configureGraph() {

        List<Expense> expenses = Environment.expenses.get(Environment.CURRENT_USER).stream().toList();
        List<LocalDate> uniqueDates = expenses
                .stream()
                .map(Expense::getDate)
                .distinct()
                .filter(localDate -> localDate
                        .isAfter(LocalDate.of(2000,12,1))&&
                        localDate.isBefore(LocalDate.of(2023,12,31)))
                .toList();

        double[] values = new double[uniqueDates.size()];
        for(int i=0; i<uniqueDates.size(); i++) {
            for(int j=0; j<Environment.expenses.get(Environment.CURRENT_USER).size(); j++) {
                if(expenses.get(j).getDate().isEqual(uniqueDates.get(i))) {
                    values[i] += expenses.get(j).getValue();
                }
            }
        }

        XYChart.Series<String, Double> series = new XYChart.Series<>();
        for(int i=0; i<values.length; i++) {
            series.getData().add(new XYChart.Data<>(uniqueDates.get(i).toString(),values[i]));
        }
        expenseLineChart.setTitle("Daily Expenses");
        valueAxis.setLabel("Amount spent");
        timeAxis.setLabel("Date");
        series.setName("Total value spent");
        expenseLineChart.setCreateSymbols(false);

        expenseLineChart.getData().addAll(series);


    }


    private void configureButtons() {
        //TODO to refactor
        backButton.setOnAction(action -> {
            try {
                BorderPane mainPane = FXMLLoader.load(getClass().getResource("/fxml/mainPane.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(mainPane);
                stage.setScene(scene);
                stage.setTitle("BizzPal");
                stage.setResizable(false);
                stage.show();
                ((Node)(action.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        newEntryButton.setOnAction(action -> {
            AnchorPane addExpensePane;
            try {
                addExpensePane = FXMLLoader.load(getClass().getResource("/fxml/addExpensePane.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(addExpensePane);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

        deleteEntryButton.setOnAction(action -> {
            AnchorPane deleteExpensePane;
            try {
                deleteExpensePane = FXMLLoader.load(getClass().getResource("/fxml/deleteExpensePane.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(deleteExpensePane);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        calculateAverageButton.setOnAction(actionEvent -> {
            AnchorPane calculateAvg;
            try {
                calculateAvg = FXMLLoader.load(getClass().getResource("/fxml/calculateAveragePane.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(calculateAvg);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }
}
