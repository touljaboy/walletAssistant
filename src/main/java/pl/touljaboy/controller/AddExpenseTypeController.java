package pl.touljaboy.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.touljaboy.app.ExpenseAppManager;
import pl.touljaboy.io.ConsolePrinter;

public class AddExpenseTypeController {

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private Label warningLabel;

    public void initialize() {
        configureButtons();
        configureLabel();
    }

    private void configureLabel() {
        if(descriptionTextField.getText().length()>15) {
            warningLabel.setStyle(("-fx-text-fill: red ;"));
            warningLabel.setText("Category description too long! ");
        }
    }

    private void configureButtons() {
        addButton.setOnAction(action -> {
            try {
                String description = descriptionTextField.getText();
                ExpenseAppManager.addNewExpenseType(description);
                ((Node)(action.getSource())).getScene().getWindow().hide();
            } catch(NumberFormatException e) {
                ConsolePrinter.showErrorPopup
                        ("You used the wrong datatype!");
            }
        });
        cancelButton.setOnAction(action -> ((Node)(action.getSource())).getScene().getWindow().hide());
    }
}
