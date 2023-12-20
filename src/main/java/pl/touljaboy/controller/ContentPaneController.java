package pl.touljaboy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ContentPaneController {

    @FXML
    private Button calendarButton;

    @FXML
    private Button expenseTypesButton;

    @FXML
    private Button expensesButton;

    @FXML
    private Button plotButton;

    @FXML
    private Button settingsButton;

    @FXML
    private Button usersButton;

    @FXML
    private Label welcomeLabel;

    public Button getCalendarButton() {
        return calendarButton;
    }

    public Button getExpenseTypesButton() {
        return expenseTypesButton;
    }

    public Button getExpensesButton() {
        return expensesButton;
    }

    public Button getPlotButton() {
        return plotButton;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }

    public Button getUsersButton() {
        return usersButton;
    }

    public Label getWelcomeLabel() {
        return welcomeLabel;
    }
}
