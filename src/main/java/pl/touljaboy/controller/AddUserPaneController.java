package pl.touljaboy.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pl.touljaboy.app.ExpenseAppManager;
import pl.touljaboy.io.ConsolePrinter;
import pl.touljaboy.model.ExpenseType;

import java.time.LocalDate;

public class AddUserPaneController {

    @FXML
    private Button addButton;

    @FXML
    private Button cancelButton;

    @FXML
    private CheckBox isAdminCheckBox;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameTextField;

    public void initialize() {
        configureButtons();
    }

    private void configureButtons() {
        addButton.setOnAction(action -> {
            try {
                String username = usernameTextField.getText();
                String password = passwordField.getText();
                boolean isAdmin = isAdminCheckBox.isSelected();
                ExpenseAppManager.addNewUser(username, password, isAdmin);

                ((Node) (action.getSource())).getScene().getWindow().hide();
            } catch(NumberFormatException e) {
                ConsolePrinter.showErrorPopup
                        ("You used the wrong datatype!");
            }
        });
        cancelButton.setOnAction(action -> ((Node)(action.getSource())).getScene().getWindow().hide());
    }
}
