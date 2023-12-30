package pl.touljaboy.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pl.touljaboy.model.Environment;
import pl.touljaboy.model.Expense;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CalendarPaneController {
    LocalDate today;
    LocalDate focus;

    @FXML
    private ListView<Expense> expensesListView;

    @FXML
    private Button nextButton;

    @FXML
    private Button previousButton;
    @FXML
    private Label monthLabel;
    @FXML
    private Label yearLabel;
    @FXML
    private Button backButton;

    public void initialize() {
        focus = LocalDate.now();
        today = LocalDate.now();
        configureButtons();
        configureCalendar();
        configureListView();
    }

    private void configureListView() {
        expensesListView.getItems().clear();
        List<Expense> currMonthExp = Environment.expenses.get(Environment.CURRENT_USER).stream().filter(expense -> {
            int value = expense.getDate().getMonth().getValue();
            int focusValue = focus.getMonth().getValue();
            return value == focusValue;
        }).toList();
        expensesListView.getItems().addAll(currMonthExp);
    }

    private void configureButtons() {
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

        previousButton.setOnAction(action -> {
            focus = focus.minusMonths(1);
            yearLabel.setText(String.valueOf(focus.getYear()));
            monthLabel.setText(String.valueOf(focus.getMonth()));
            configureListView();
        });
        nextButton.setOnAction(action -> {
            focus = focus.plusMonths(1);
            yearLabel.setText(String.valueOf(focus.getYear()));
            monthLabel.setText(String.valueOf(focus.getMonth()));
            configureListView();
        });
    }

    private void configureCalendar() {
        yearLabel.setText(String.valueOf(focus.getYear()));
        monthLabel.setText(String.valueOf(focus.getMonth()));

    }



}
