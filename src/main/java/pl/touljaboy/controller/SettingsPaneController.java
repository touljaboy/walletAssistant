package pl.touljaboy.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsPaneController {

    @FXML
    private MenuButton CurrencyMenuButton;

    @FXML
    private MenuItem DollarMenuItem;

    @FXML
    private MenuItem EnglishMenuItem;

    @FXML
    private MenuItem EuroMenuItem;

    @FXML
    private MenuButton LanguageMenuButton;

    @FXML
    private MenuItem PLNMenuItem;

    @FXML
    private MenuItem PolishMenuItem;

    @FXML
    private Button applyButton;

    @FXML
    private Button backButton;

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
    }
}
