module walletAssistant {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javaplot;
    requires com.google.common;

    opens pl.touljaboy.controller to javafx.fxml;
    opens pl.touljaboy.app to javafx.base;
    exports pl.touljaboy.app to javafx.graphics;
}