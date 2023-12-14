package pl.touljaboy.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;

public class UserPaneController {

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private Button addUserButton;

    @FXML
    private MenuItem addUserMenuItem;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private Button deleteUserButton;

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
    private ListView<?> usersList;

}
