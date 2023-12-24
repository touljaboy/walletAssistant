package pl.touljaboy.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import pl.touljaboy.model.Environment;
import pl.touljaboy.model.Expense;

import java.util.List;

public class DeleteExpensePaneController {

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ListView<Expense> expensesList;


    public void initialize() {
        configureListView();
        configureButtons();
    }

    private void configureButtons() {
        backButton.setOnAction(action -> {
            ((Node)(action.getSource())).getScene().getWindow().hide();
        });
        deleteButton.setOnAction(action -> {
            List<Expense> expensesToBeDeleted = expensesList.getSelectionModel().getSelectedItems().stream().toList();
            Environment.expenses.get(Environment.CURRENT_USER).removeAll(expensesToBeDeleted);
            expensesList.getItems().removeAll(expensesToBeDeleted);
        });
    }

    private void configureListView() {
        expensesList.getItems().addAll(Environment.expenses.get(Environment.CURRENT_USER));
    }
}
