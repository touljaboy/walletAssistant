package pl.touljaboy.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import pl.touljaboy.app.ExpenseAppManager;
import pl.touljaboy.model.Environment;
import pl.touljaboy.model.User;

import java.io.IOException;

public class UserPaneController {
    public static User selectedUser;

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private Button addUserButton;

    @FXML
    private MenuItem addUserMenuItem;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private Button backButton;

    @FXML
    private MenuItem dirMenuItem;

    @FXML
    private Button editUserButton;

    @FXML
    private MenuItem logOutMenuItem;

    @FXML
    private MenuItem modUserMenuItem;

    @FXML
    private MenuItem newSourceMenuItem;

    @FXML
    private ListView<User> usersList;


    public void initialize() {
        configureButtons();
        configureViewList();
    }

    private void configureViewList() {
        usersList.getItems().addAll(Environment.users);
        usersList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observableValue, User user, User t1) {
                selectedUser = usersList.getSelectionModel().getSelectedItem();
            }
        });
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

        addUserButton.setOnAction(action -> {
            try {
                AnchorPane addUserPane = FXMLLoader.load(getClass().getResource("/fxml/addUserPane.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(addUserPane);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        editUserButton.setOnAction(action -> {
            try {
                AnchorPane editUserPane = FXMLLoader.load(getClass().getResource("/fxml/editUserPane.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(editUserPane);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                ((Node)(action.getSource())).getScene().getWindow().hide();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
