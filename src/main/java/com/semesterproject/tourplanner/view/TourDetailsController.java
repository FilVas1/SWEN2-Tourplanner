package com.semesterproject.tourplanner.view;

import com.semesterproject.tourplanner.viewmodels.TourDetailsViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.NumberStringConverter;


public class TourDetailsController {
    @FXML
    public TextField nameTextField;
    @FXML
    public ImageView mapImg;
    @FXML
    public TextField descriptionTextField;
    @FXML
    private final TourDetailsViewModel tourDetailsViewModel;
    public TextField distanceField;
    public TextField timeField;
    public TextField startTextField;
    public TextField destinationTextField;
    public TextField transptypeTextField;
    public AnchorPane anchorPaneDetails;
    public Button editButton;
    public TextArea maneuvers;
    public TextField popularityField;
    public TextField childFriendlinessField;


    public TourDetailsController(TourDetailsViewModel tourDetailsViewModel) {
        this.tourDetailsViewModel = tourDetailsViewModel;
    }

    public TourDetailsViewModel getTourDetailsViewModel() {
        return tourDetailsViewModel;
    }
    NumberStringConverter converter = new NumberStringConverter();

    @FXML
    void initialize() {
        nameTextField.textProperty().bindBidirectional(tourDetailsViewModel.nameProperty());
        descriptionTextField.textProperty().bindBidirectional(tourDetailsViewModel.descriptionProperty());
        startTextField.textProperty().bindBidirectional(tourDetailsViewModel.startProperty());
        destinationTextField.textProperty().bindBidirectional(tourDetailsViewModel.destinationProperty());
        transptypeTextField.textProperty().bindBidirectional(tourDetailsViewModel.transtypeProperty());

        distanceField.textProperty().bindBidirectional(tourDetailsViewModel.distanceProperty(), converter);
        timeField.textProperty().bindBidirectional(tourDetailsViewModel.timeProperty(), converter);
        mapImg.imageProperty().bindBidirectional(tourDetailsViewModel.mapImage());
        maneuvers.textProperty().bindBidirectional(tourDetailsViewModel.maneuverProperty());
        popularityField.textProperty().bindBidirectional(tourDetailsViewModel.popularityProperty());
        childFriendlinessField.textProperty().bindBidirectional(tourDetailsViewModel.childFriendlinessProperty());
    }


    public void editTour() {
        nameTextField.setEditable(true);
        transptypeTextField.setEditable(true);
        descriptionTextField.setEditable(true);
        distanceField.setEditable(true);
        timeField.setEditable(true);

        editButton.setText("save");
        editButton.setOnAction(e->updateTour());
    }

    private void updateTour() {
        tourDetailsViewModel.updateTourModel();

        nameTextField.setEditable(false);
        transptypeTextField.setEditable(false);
        descriptionTextField.setEditable(false);
        distanceField.setEditable(false);
        timeField.setEditable(false);

        editButton.setText("edit");
        editButton.setOnAction(e->editTour());
    }
}
