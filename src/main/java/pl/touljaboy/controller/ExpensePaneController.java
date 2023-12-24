package pl.touljaboy.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.touljaboy.app.ExpenseAppManager;
import pl.touljaboy.model.Environment;
import pl.touljaboy.model.ExpenseType;

import java.io.IOException;
import java.time.LocalDate;

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
    //Items regarding adding an expense

    public void initialize() {
        configureButtons();
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

    }
}
