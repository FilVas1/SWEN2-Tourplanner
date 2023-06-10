package com.semesterproject.tourplanner.viewmodels;

import com.semesterproject.tourplanner.bl.TourLogServiceImpl;
import com.semesterproject.tourplanner.models.Tour;
import com.semesterproject.tourplanner.models.TourLog;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.Date;

public class EditLogViewModel {
    private TourLogServiceImpl tourLogServiceImpl;
    private TourLog tourLog;
    private volatile boolean isInitValue = false;
    private final StringProperty difficulty = new SimpleStringProperty();
    private final IntegerProperty rating = new SimpleIntegerProperty();
    private final IntegerProperty distance = new SimpleIntegerProperty();
    private final IntegerProperty totalTime = new SimpleIntegerProperty();
    private final StringProperty comment = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> date = new SimpleObjectProperty<LocalDate>();

    public EditLogViewModel() {
        tourLogServiceImpl = new TourLogServiceImpl();
    }

    public StringProperty difficultyProperty() {
        return difficulty;
    }
    public IntegerProperty ratingProperty() {
        return rating;
    }
    public IntegerProperty distanceProperty() {
        return distance;
    }
    public IntegerProperty timeProperty() {
        return totalTime;
    }
    public StringProperty commentProperty(){return comment;}
    public final ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void updateLog(){
        if( !isInitValue ) {
            tourLog.setDate(date.get());
            tourLog.setComment(comment.get());
            tourLog.setDifficulty(difficulty.get());
            tourLog.setTotalTime(totalTime.get());
            tourLog.setRating(rating.get());
            tourLog.setDistance(distance.get());

            TourLog tourLogDB = tourLogServiceImpl.updateTourLog(tourLog);
            NewTourLog.getInstance().setCreateTourLog(tourLogDB);
        }
    }

    public void setTourLog(TourLog tourLog) {
        isInitValue = true;
        if(tourLog!=null) {
            this.tourLog = tourLog;
            difficulty.setValue(tourLog.getDifficulty());
            rating.setValue(tourLog.getRating());
            distance.setValue(tourLog.getDistance());
            comment.setValue(tourLog.getComment());
            date.setValue(tourLog.getDate());
            totalTime.setValue(tourLog.getTotalTime());
            isInitValue = false;
        }
    }
}
