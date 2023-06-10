package com.semesterproject.tourplanner.view;

import com.semesterproject.tourplanner.models.TourLog;
import com.semesterproject.tourplanner.viewmodels.NewLogViewModel;
import com.semesterproject.tourplanner.viewmodels.NewTourLog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

public class NewLogController {
    @FXML
    public Button createButton;
    @FXML
    public TextField ratingField;
    @FXML
    public TextField commentField;
    @FXML
    public TextField timeField;
    @FXML
    public DatePicker dateField;

    private final NewLogViewModel newLogViewModel;
    @FXML
    public TextField distanceField;
    @FXML
    public ComboBox difficultyCombo;

    public NewLogController(NewLogViewModel newLogViewModel) {
        this.newLogViewModel = newLogViewModel;
    }

    @FXML
    void initialize() {
        ValidationSupport support = new ValidationSupport();
        createButton.disableProperty().bind(support.invalidProperty());

        Validator<String> emptyField = Validator.createEmptyValidator("Input required");
        Validator<String> notANumber = Validator.createPredicateValidator(
                newLogViewModel::notANumber,
                "Number required");
        Validator<String> negativeValue = Validator.createPredicateValidator(
                value ->  newLogViewModel.StringToInt(value) > 0.0,
                "Positive number is required");

        support.registerValidator(dateField, Validator.createEmptyValidator("Please select a date"));
        support.registerValidator(distanceField, Validator.combine(emptyField, notANumber, negativeValue));
        support.registerValidator(timeField, Validator.combine(emptyField, notANumber, negativeValue));
    }

    public void submit(ActionEvent actionEvent) {
        String diff = (String) difficultyCombo.getSelectionModel().getSelectedItem();

        TourLog tourLog = new TourLog(dateField.getValue(),commentField.getText(), diff,Integer.parseInt(timeField.getText()),Integer.parseInt(ratingField.getText()),Integer.parseInt(distanceField.getText()));
        NewTourLog.getInstance().setCreateTourLog(tourLog);
        NewTourLog.getInstance().setCancelled(false);

        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void cancel(ActionEvent actionEvent) {
        NewTourLog.getInstance().setCancelled(true);

        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
