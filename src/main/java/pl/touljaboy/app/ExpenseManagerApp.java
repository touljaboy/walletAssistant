package pl.touljaboy.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.touljaboy.model.Environment;

import java.util.Objects;

//For now, I plan to test some basic features using the terminal before I move on to making it a GUI app.
public class ExpenseManagerApp extends Application {
    //Application version field
    public static final String APP_VERSION = "\nExpensesManager - 0.8 GUI branch Early Beta";
    public static void main(String[] args) {
        ExpenseAppManager expenseAppManager = new ExpenseAppManager();
        expenseAppManager.initialize();
        launch();
        //alpha console methods:
//        ExpenseAppManager expenseAppManager = new ExpenseAppManager();
//        expenseAppManager.userLoginLoop();
//        expenseAppManager.controlLoop();
        //TODO begin beta - GUI and testing (different branch)
        //TODO Beta needs to have a calendar
        //TODO we need a language pack Polish/English
        //TODO well, GUI in normal beta should be written in HTML - just for flex
        //TODO settings window kinda sux, make it simpler
        //TODO menu settings on top are useless, delete the pane
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane loginPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/loginPane.fxml")));
        Scene scene = new Scene(loginPane);
        stage.setScene(scene);
        stage.setTitle(APP_VERSION);
        stage.setResizable(false);
        stage.show();
    }
}
