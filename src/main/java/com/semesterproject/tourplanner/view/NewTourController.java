package com.semesterproject.tourplanner.view;

import com.semesterproject.tourplanner.bl.Logging.LoggerFactory;
import com.semesterproject.tourplanner.bl.Logging.LoggerWrapper;
import com.semesterproject.tourplanner.bl.TourServiceImpl;
import com.semesterproject.tourplanner.models.Tour;
import com.semesterproject.tourplanner.viewmodels.NewTour;
import com.semesterproject.tourplanner.viewmodels.NewTourViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.validation.*;

import java.util.ArrayList;
import java.util.List;

public class NewTourController {
    private static final LoggerWrapper logger = LoggerFactory.getLogger(NewTourController.class);
    private final NewTourViewModel newTourViewModel;
    @FXML
    public Button cancelButton;
    @FXML
    public TextField transtype;
    @FXML
    public TextField description;
    public AnchorPane createAnchorPane;
    @FXML
    private TextField tourname;
    @FXML
    private TextField start;
    @FXML
    private TextField destination;
    @FXML
    private Button createButton;
    private ValidationSupport support;

    private List<String> onSubmitValidationErrors = new ArrayList<>();

    public NewTourController(NewTourViewModel newTourViewModel) {
        this.newTourViewModel = newTourViewModel;
        TourServiceImpl tourServiceImpl = new TourServiceImpl();
    }


    @FXML
    void initialize() {
        this.support = new ValidationSupport();
        createButton.disableProperty().bind(support.invalidProperty());
        Validator<String> emptyField = Validator.createEmptyValidator("Text required");
        Validator<String> uniqueName = Validator.createPredicateValidator(
                newTourViewModel::isUnique
                , "Tour with this name already exists");

        support.registerValidator(tourname, Validator.combine(emptyField, uniqueName));
        support.registerValidator(start, emptyField);
        support.registerValidator(destination, emptyField);

    }

    public void submit(ActionEvent actionEvent) {
        boolean isFormValid = isFormValid();
        if(!isFormValid) return;

        Tour tour = new Tour(tourname.getText(), description.getText(), start.getText(), destination.getText(), transtype.getText());

        NewTour.getInstance().setCancelled(false);
        try {
            newTourViewModel.addTour(tour);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void cancel(ActionEvent actionEvent) {
        NewTour.getInstance().setCancelled(true);

        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private boolean isFormValid() {
        onSubmitValidationErrors.clear();

        boolean isStartValid = newTourViewModel.validMap(start.getText());
        if (!isStartValid) onSubmitValidationErrors.add("Invalid Start location");

        boolean isDestinationValid = newTourViewModel.validMap(destination.getText());
        if (!isDestinationValid) onSubmitValidationErrors.add("Invalid Destination location");

        if (onSubmitValidationErrors.isEmpty()) return true;

        StringBuilder errorsString = new StringBuilder();
        for (String e : onSubmitValidationErrors) {
            errorsString.append(e).append("\n");
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validation error");
        alert.setHeaderText("You have to solve the following errors before submitting the form");
        alert.setContentText(errorsString.toString());
        alert.show();

        return false;
    }

}
