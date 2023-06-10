package com.semesterproject.tourplanner.view;

import com.semesterproject.tourplanner.viewmodels.EditLogViewModel;
import com.semesterproject.tourplanner.viewmodels.NewTourLog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class EditLogController {
    @FXML
    public TextField difficultyField;
    @FXML
    public Button saveButton;
    @FXML
    public TextField ratingField;
    @FXML
    public TextField commentField;
    @FXML
    public TextField timeField;
    @FXML
    public DatePicker dateField;
    @FXML
    public TextField distanceField;

    //private final TourDetailsViewModel tourDetailsViewModel;
    private final EditLogViewModel editLogViewModel;

    public EditLogController(EditLogViewModel editLogViewModel) {
        this.editLogViewModel = editLogViewModel;
    }

    public EditLogViewModel getEditLogViewModel() {
        return editLogViewModel;
    }
    NumberStringConverter converter = new NumberStringConverter();

    @FXML
    void initialize() {
        //editLogViewModel.setTourLog();
        difficultyField.textProperty().bindBidirectional(editLogViewModel.difficultyProperty());
        commentField.textProperty().bindBidirectional(editLogViewModel.commentProperty());
        ratingField.textProperty().bindBidirectional(editLogViewModel.ratingProperty(), converter);
        distanceField.textProperty().bindBidirectional(editLogViewModel.distanceProperty(), converter);
       /* distanceField.textProperty().bind(Bindings.createStringBinding(
                () -> Integer.toString(editLogViewModel.distanceProperty().get()),
                editLogViewModel.distanceProperty()));*/
        dateField.valueProperty().bindBidirectional(editLogViewModel.dateProperty());
        timeField.textProperty().bindBidirectional(editLogViewModel.timeProperty(), converter);
    }

    public void submit(ActionEvent actionEvent) {
        editLogViewModel.updateLog();
       // TourLog tourLog = new TourLog(dateField.getValue(),commentField.getText(),difficultyField.getText(),Integer.parseInt(timeField.getText()),Integer.parseInt(ratingField.getText()),Integer.parseInt(distanceField.getText()));
       // NewTourLog.getInstance().setCreateTourLog(tourLog);
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
