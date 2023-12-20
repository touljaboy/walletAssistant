package pl.touljaboy.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.touljaboy.io.CsvFileManager;
import pl.touljaboy.model.Environment;
import pl.touljaboy.model.User;

import java.io.IOException;

public class LoginPaneController {
    CsvFileManager csvFileManager = new CsvFileManager();

    @FXML
    private Label landingLabel;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button quitButton;

    @FXML
    private TextField usernameField;

    @FXML
    private Label usernameLabel;
    public void initialize() {
        configureButtons();
    }

    private void configureButtons() {
        loginButton.setOnAction(actionEvent -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            boolean loginSuccessful = false;
                for (int i = 0; i < Environment.users.size(); i++) {
                    if (Environment.users.get(i).getUsername().equals(username) &&
                            Environment.users.get(i).getPassword().equals(password)) {
                        BorderPane mainPane;
                        Environment.CURRENT_USER = username;
                        loginSuccessful = true;
                        csvFileManager.secondaryDataImport();
                        try {
                            //TODO to refactor
                            mainPane = FXMLLoader.load(getClass().getResource("/fxml/mainPane.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(mainPane);
                            stage.setScene(scene);
                            stage.setTitle("BizzPal");
                            stage.setResizable(false);
                            stage.show();
                            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (!loginSuccessful) {
                    landingLabel.setText("Wrong username and/or password! ");
                    landingLabel.setStyle(("-fx-text-fill: red ;"));
                }
        });
        quitButton.setOnAction(action -> {
            Platform.exit();
        });
    }
}

