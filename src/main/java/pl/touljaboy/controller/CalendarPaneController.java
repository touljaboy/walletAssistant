package pl.touljaboy.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class CalendarPaneController {
    LocalDate today;
    LocalDate focus;

    @FXML
    private FlowPane calendarFlowPane;

    @FXML
    private Button nextButton;

    @FXML
    private Button previousButton;
    @FXML
    private Label monthLabel;
    @FXML
    private Label yearLabel;
    @FXML
    private Button backButton;

    public void initialize() {
        focus = LocalDate.now();
        today = LocalDate.now();
        configureButtons();
        configureCalendar();
    }

    private void configureButtons() {
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

    private void configureCalendar() {
        yearLabel.setText(String.valueOf(focus.getYear()));
        monthLabel.setText(String.valueOf(focus.getMonth()));

        double calendarWidth = calendarFlowPane.getWidth();
        double calendarHeight = calendarFlowPane.getHeight();
        double strokeWidth = 1;
        double horizontalSpacing = calendarFlowPane.getHgap();
        double verticalSpacing = calendarFlowPane.getVgap();
    }


}
