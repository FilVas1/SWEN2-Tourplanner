package com.semesterproject.tourplanner.viewmodels;

import com.semesterproject.tourplanner.bl.TourLogServiceImpl;
import com.semesterproject.tourplanner.models.Tour;
import com.semesterproject.tourplanner.models.TourLog;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TourLogViewModel {
    private static TourLogServiceImpl tourLogServiceImpl;
    private volatile boolean isInitValue = false;
    private Tour tour;
    private EditLogViewModel editLogViewModel;

    public TourLogViewModel() {
        tourLogServiceImpl = new TourLogServiceImpl();
        //observableTourLogs.addListener( (arg, oldVal, newVal)->updateTourLogModel());
    }

    final ListProperty<TourLog> tourlogs = new SimpleListProperty<>(FXCollections.observableArrayList());
    private List<TourLogViewModel.SelectionChangedListener> listeners = new ArrayList<>();
    private static ObservableList<TourLog> observableTourLogs = FXCollections.observableArrayList();


    public interface SelectionChangedListener {
        void changeLogSelection(TourLog tourlog);
    }

    public ObservableList<TourLog> getObservableTourLogs() {
        return observableTourLogs;
    }
    public ChangeListener<TourLog> getChangeListener() {
        return (observableValue, oldValue, newValue) -> notifyListeners(newValue);
    }
    private void notifyListeners(TourLog newValue) {
        for ( var listener : listeners ) {
            listener.changeLogSelection(newValue);
        }
    }
    public ObservableList<TourLog> ListProperty(){
        return tourlogs;
    }
    public List<TourLogViewModel.SelectionChangedListener> getListeners() {
        return listeners;
    }
    public void addSelectionChangedListener(TourLogViewModel.SelectionChangedListener listener) {
        listeners.add(listener);
    }

    private TourDetailsViewModel tourDetailsViewModel;
    public void setParentViewModel(TourDetailsViewModel tourDetailsViewModel) {
        this.tourDetailsViewModel = tourDetailsViewModel;
    }

    public void setTourModel(Tour tourModel) {
        isInitValue = true;
        tourlogs.setValue(observableTourLogs);
        //this.tour=tourDetailsViewModel.getTour();
        this.tour=tourModel;
    }


    public void setTourLogs(ArrayList<TourLog> logItems) {
        observableTourLogs.clear();
        observableTourLogs.addAll(logItems);
        tour.setLog(logItems);
    }

    public void setEditLogViewModel(EditLogViewModel editLogViewModel) {
        this.editLogViewModel = editLogViewModel;
    }

    public void addNewTourLog() throws IOException {
        TourLog tourLog = NewTourLog.getInstance().getCreateTourLog();

        if(!NewTourLog.getInstance().isCancelled()) {
            tourLog.setTourId(tour.getId());
            TourLog tourLogDB = tourLogServiceImpl.createTourLog(tourLog);
            observableTourLogs.add(tourLogDB);
        }
    }

    public void setTourLogToEdit(TourLog tourLog){
        editLogViewModel.setTourLog(tourLog);
    }

    public void updateTourLogModel() {
        TourLog tourLog = NewTourLog.getInstance().getCreateTourLog();
        if(!NewTourLog.getInstance().isCancelled()) {
            observableTourLogs.remove(tourLog);
            observableTourLogs.add(tourLog);
        }
    }

    public void deleteTourLog(TourLog tourLog) {
        tourLogServiceImpl.removeTourLog(tourLog);
        observableTourLogs.remove(tourLog);
    }
}
