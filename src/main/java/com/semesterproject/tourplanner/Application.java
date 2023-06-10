package com.semesterproject.tourplanner;

import com.semesterproject.tourplanner.bl.Logging.LoggerFactory;
import com.semesterproject.tourplanner.bl.Logging.LoggerWrapper;
import com.semesterproject.tourplanner.bl.ReportHelper;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;

public class Application extends javafx.application.Application {
    private static final LoggerWrapper logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        /*logger.debug("This is a debug message.");
        logger.fatal("This is a fatal message.");
        logger.warn("This is a warning message.");
        logger.error("This is an error message.");*/

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = DependencyInjection.load("MainWindow.fxml", Locale.ENGLISH);  // Locale.GERMANY, Locale.ENGLISH

        Scene scene = new Scene(root, 850, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Tour Planner");
        primaryStage.show();
    }
}
