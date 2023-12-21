package pl.touljaboy.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ExpenseTypePaneController {

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private MenuItem addUserMenuItem;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private Button deleteCategoryButton;

    @FXML
    private MenuItem dirMenuItem;

    @FXML
    private ListView<?> expenseTypesList;

    @FXML
    private MenuItem logOutMenuItem;

    @FXML
    private MenuItem modUserMenuItem;

    @FXML
    private Button newCategoryButton;
    @FXML
    private Button backButton;

    @FXML
    private MenuItem newSourceMenuItem;

    public void initialize() {
        configureButtons();
    }

    private void configureButtons() {
        //TODO code regarding window opening is prone to repetition, so create a different controller class,
        // call it window controller or something like that
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
        newCategoryButton.setOnAction(action ->  {
            AnchorPane addExpenseTypePane;
            try {
                addExpenseTypePane = FXMLLoader.load(getClass().getResource("/fxml/addExpenseTypePane.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(addExpenseTypePane);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
