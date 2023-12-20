package pl.touljaboy.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.touljaboy.app.ExpenseManagerApp;
import pl.touljaboy.exception.DataExportException;
import pl.touljaboy.io.ConsolePrinter;
import pl.touljaboy.io.CsvFileManager;

import java.io.IOException;

public class MainPaneController {
    CsvFileManager csvFileManager = new CsvFileManager();
    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private MenuItem addUserMenuItem;

    @FXML
    private Button calendarButton;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem dirMenuItem;

    @FXML
    private Button expenseTypesButton;

    @FXML
    private Button expensesButton;

    @FXML
    private MenuItem logOutMenuItem;

    @FXML
    private AnchorPane menuPane;

    @FXML
    private MenuItem modUserMenuItem;

    @FXML
    private MenuItem newSourceMenuItem;

    @FXML
    private Button quitButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button usersButton;

    @FXML
    private Label welcomeLabel;
    public void initialize() {
        configureButtons();
    }

    private void configureButtons() {
        //TODO to refactor

        expensesButton.setOnAction(action -> {
            try {
                BorderPane expensesPane = FXMLLoader.load(getClass().getResource("/fxml/expensesPane.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(expensesPane);
                stage.setScene(scene);
                stage.setTitle("Expenses");
                stage.show();
                ((Node)(action.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        quitButton.setOnAction(action -> {
            try {
                csvFileManager.exportData();
                ConsolePrinter.printLine("Export danych do pliku zakończony powodzeniem");
                Platform.exit();
            } catch (DataExportException e) {
                ConsolePrinter.printError(e.getMessage());
            }
        });
        expenseTypesButton.setOnAction(action -> {
            BorderPane expensesPane;
            try {
                expensesPane = FXMLLoader.load(getClass().getResource("/fxml/expenseTypePane.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(expensesPane);
                stage.setScene(scene);
                stage.setTitle("ExpenseTypes");
                stage.setResizable(false);
                stage.show();
                ((Node)(action.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        settingsButton.setOnAction(action -> {
            AnchorPane settingsPane;
            try {
                settingsPane = FXMLLoader.load(getClass().getResource("/fxml/settingsPane.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(settingsPane);
                stage.setScene(scene);
                stage.setTitle("Settings");
                stage.setResizable(false);
                stage.show();
                ((Node)(action.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        usersButton.setOnAction(action -> {
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
}
