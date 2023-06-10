package com.semesterproject.tourplanner.view;

import com.semesterproject.tourplanner.DependencyInjection;
import com.semesterproject.tourplanner.bl.MapException;
import com.semesterproject.tourplanner.models.TourLog;
import com.semesterproject.tourplanner.viewmodels.TourLogViewModel;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;

public class TourLogsController {
    @FXML
    private TableView<TourLog> tourLogListItems;
    @FXML
    private TableColumn<TourLog, LocalDate> date;
    @FXML
    private TableColumn<TourLog, Integer> totalTime;
    @FXML
    private TableColumn<TourLog, Integer> distance;
    @FXML
    private TableColumn<TourLog, String> comment;
    @FXML
    private TableColumn<TourLog, String> difficulty;
    @FXML
    private TableColumn<TourLog, String> rating;
    private ObservableList<TourLog> tourlogs  = FXCollections.observableArrayList();
    private TourLogViewModel tourLogViewModel;
    private EditLogController editLogController;

    public TourLogsController(TourLogViewModel tourLogViewModel) {
        this.tourLogViewModel = tourLogViewModel;
    }

    @FXML
    void initialize() {
        Bindings.bindContent(tourlogs, tourLogViewModel.ListProperty());
        date.setCellValueFactory(new PropertyValueFactory<TourLog, LocalDate>("date"));
        totalTime.setCellValueFactory(new PropertyValueFactory<TourLog, Integer>("totalTime"));
        distance.setCellValueFactory(new PropertyValueFactory<TourLog, Integer>("distance"));
        comment.setCellValueFactory(new PropertyValueFactory<TourLog, String>("comment"));
        difficulty.setCellValueFactory(new PropertyValueFactory<TourLog, String>("difficulty"));
        rating.setCellValueFactory(new PropertyValueFactory<TourLog, String>("rating"));
        tourLogListItems.setItems(tourlogs);

        tourLogListItems.setItems(tourLogViewModel.getObservableTourLogs());
        tourLogListItems.getSelectionModel().selectedItemProperty().addListener(tourLogViewModel.getChangeListener());
    }
    @FXML
    public void onButtonAddLog(ActionEvent actionEvent) throws MapException, IOException {
        Parent parent = DependencyInjection.load("CreateLog.fxml", Locale.ENGLISH);  // Locale.GERMANY, Locale.ENGLISH
        Stage stage = new Stage();
        Scene dialogScene = new Scene(parent, 600, 400);
        stage.setScene(dialogScene);
        stage.showAndWait();


        tourLogViewModel.addNewTourLog();
        Button deleteButton = new Button("delete");
        deleteButton.setOnAction(this::onButtonRemoveLog);

        tourLogListItems.getSelectionModel().selectLast();
    }

    @FXML
    public void onButtonRemoveLog(ActionEvent actionEvent) {
        if(tourLogListItems.getSelectionModel().getSelectedItem() != null)
        tourLogViewModel.deleteTourLog(tourLogListItems.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void onButtonEditLog(ActionEvent actionEvent) throws IOException {
        tourLogViewModel.setTourLogToEdit(tourLogListItems.getSelectionModel().getSelectedItem());
        Parent parent = DependencyInjection.load("EditLog.fxml", Locale.ENGLISH);  // Locale.GERMANY, Locale.ENGLISH
        Stage stage = new Stage();
        Scene dialogScene = new Scene(parent, 600, 400);
        stage.setScene(dialogScene);
        stage.showAndWait();


        tourLogViewModel.updateTourLogModel();   //update in observablelist
        //tourLogViewModel.editTourLog(tourLogListItems.getSelectionModel().getSelectedItem());
        Button deleteButton = new Button("delete");
        deleteButton.setOnAction(this::onButtonRemoveLog);

        tourLogListItems.getSelectionModel().selectLast();
    }

}
