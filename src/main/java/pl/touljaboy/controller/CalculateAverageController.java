package pl.touljaboy.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import pl.touljaboy.app.ExpenseAnalyser;
import pl.touljaboy.model.Environment;
import pl.touljaboy.model.Expense;
import pl.touljaboy.model.ExpenseType;

import java.time.LocalDate;
import java.util.List;

public class CalculateAverageController {
    ExpenseAnalyser expenseAnalyser = new ExpenseAnalyser();

    @FXML
    private Button calculateButton;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ChoiceBox<ExpenseType> expenseTypeChoiceBox;

    @FXML
    private DatePicker startDatePicker;
    @FXML
    private Button backButton;
    @FXML
    private Text averageText;

    public void initialize() {
        configureButtons();
        configureChoiceBox();
    }

    private void configureChoiceBox() {
        expenseTypeChoiceBox.getItems().addAll(Environment.expenseTypes);
    }

    private void configureButtons() {
        backButton.setOnAction(action -> {
            ((Node)(action.getSource())).getScene().getWindow().hide();
        });
        calculateButton.setOnAction(action -> {
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();
            List<Expense> datePeriodExpenses = Environment.expenses.get(Environment.CURRENT_USER)
                    .stream()
                    .filter(expense -> expense.getDate().isBefore(endDate) && expense.getDate().isAfter(startDate))
                    .toList();
            ExpenseType expenseType = expenseTypeChoiceBox.getValue();
            double averageExpenses = expenseAnalyser.calculateAverageExpenses
                    (datePeriodExpenses, expenseType, Environment.CURRENT_USER);
            String avgFormat = String.format("%8.2f",averageExpenses);
            averageText.setText(avgFormat);
        });
    }
}
