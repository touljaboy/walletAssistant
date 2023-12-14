package pl.touljaboy.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;

//TODO graphs should dynamically change based on user chocie (pie chart, line graph). Alternatively, just display both.
public class ExpensePaneController {

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private MenuItem addUserMenuItem;

    @FXML
    private Button calculateAverageButton;

    @FXML
    private Button changeGraphStyleButton;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private Button deleteEntryButton;

    @FXML
    private MenuItem dirMenuItem;

    @FXML
    private LineChart<?, ?> expenseLineChart;

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

}
