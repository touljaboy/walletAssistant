package pl.touljaboy.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.touljaboy.model.Environment;

import java.io.IOException;

public class EditUserPaneController {

    @FXML
    private Button applyButton;

    @FXML
    private Button cancelButton;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private TextField usernameTextField;
    @FXML
    private Label editingUsernameLabel;

    public void initialize() {
        configureLabels();
        configureButtons();
    }
    private void configureButtons() {
        applyButton.setOnAction(action -> {
            int indexOfUser = Environment.users.indexOf(UserPaneController.selectedUser);

            String username = usernameTextField.getText();
            String password = passwordTextField.getText();
            Environment.users.get(indexOfUser).setUsername(username);
            Environment.users.get(indexOfUser).setPassword(password);
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
        cancelButton.setOnAction(action -> {
            //TODO code repetition
            BorderPane usersPane;
            try {
                usersPane = FXMLLoader.load(getClass().getResource("/fxml/userPane.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(usersPane);
                stage.setScene(scene);
                stage.setTitle("Users");
                stage.setResizable(false);
                stage.show();
                ((Node)(action.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void configureLabels() {
        editingUsernameLabel.setText("Currently editing: "+UserPaneController.selectedUser.getUsername());
    }
}
