package com.semesterproject.tourplanner;

import com.semesterproject.tourplanner.models.Tour;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class Controller {
    @FXML
    private Label welcomeText;

    public Controller(ArrayList<Tour> allTours) {

    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}