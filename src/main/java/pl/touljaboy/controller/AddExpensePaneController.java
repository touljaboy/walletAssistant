package pl.touljaboy.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import pl.touljaboy.app.ExpenseAppManager;
import pl.touljaboy.io.ConsolePrinter;
import pl.touljaboy.model.Environment;
import pl.touljaboy.model.ExpenseType;

import java.time.LocalDate;

public class AddExpensePaneController {
    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private ChoiceBox<ExpenseType> categoryChoiceBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField valueTextField;

    public void initialize() {
        configureButtons();
        configureChoicePickers();
    }

    private void configureButtons() {
        //Adding new expense
        addButton.setOnAction(action -> {
            try {
                double value = Double.parseDouble(valueTextField.getText());
                ExpenseType expenseType = categoryChoiceBox.getValue();
                LocalDate localDate = datePicker.getValue();
                ExpenseAppManager.addNewExpense(value, expenseType, localDate);

                ((Node) (action.getSource())).getScene().getWindow().hide();
            } catch(NumberFormatException e) {
                ConsolePrinter.showErrorPopup
                        ("You used the wrong datatype! While separating value, you should use a dot '.'");
            }
        });
        cancelButton.setOnAction(action -> ((Node)(action.getSource())).getScene().getWindow().hide());
    }

    private void configureChoicePickers() {
        categoryChoiceBox.getItems().addAll(Environment.expenseTypes);
    }
}
