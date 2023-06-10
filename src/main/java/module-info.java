module com.semesterproject.tourplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;
    requires java.desktop;
    requires org.apache.logging.log4j;
    requires layout;
    requires kernel;
    requires io;
    requires org.controlsfx.controls;
    requires com.fasterxml.jackson.databind;
    requires org.jetbrains.annotations;
    requires ini4j;

    opens com.semesterproject.tourplanner;
    exports com.semesterproject.tourplanner;
    opens com.semesterproject.tourplanner.view to javafx.fxml, org.controlsfx.controls, javafx.graphics;
    opens com.semesterproject.tourplanner.models to javafx.base, org.controlsfx.controls;
}