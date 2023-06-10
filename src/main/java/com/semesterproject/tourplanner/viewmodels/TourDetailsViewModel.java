package com.semesterproject.tourplanner.viewmodels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.semesterproject.tourplanner.bl.Logging.LoggerFactory;
import com.semesterproject.tourplanner.bl.Logging.LoggerWrapper;
import com.semesterproject.tourplanner.bl.MapQuest;
import com.semesterproject.tourplanner.bl.TourLogServiceImpl;
import com.semesterproject.tourplanner.bl.TourServiceImpl;
import com.semesterproject.tourplanner.enums.Difficulty;
import com.semesterproject.tourplanner.models.Tour;
import com.semesterproject.tourplanner.models.TourLog;
import javafx.beans.property.*;
import javafx.scene.image.Image;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class TourDetailsViewModel {
    private static final LoggerWrapper logger = LoggerFactory.getLogger(TourDetailsViewModel.class);
    private static TourLogServiceImpl tourLogServiceImpl;
    private static TourServiceImpl tourServiceImpl;
    private Tour tour;
    private volatile boolean isInitValue = false;

    public Tour getTour() {
        return tour;
    }

    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final ObjectProperty<javafx.scene.image.Image> mapImg = new SimpleObjectProperty<>();
    private final DoubleProperty distance = new SimpleDoubleProperty();
    private final IntegerProperty time = new SimpleIntegerProperty();
    private final StringProperty start = new SimpleStringProperty();
    private final StringProperty destination = new SimpleStringProperty();
    private final StringProperty transtype = new SimpleStringProperty();
    private TourLogViewModel tourLogViewModel;
    private final StringProperty maneuvers = new SimpleStringProperty();
    private final StringProperty popularity = new SimpleStringProperty();
    private final StringProperty childFriendliness = new SimpleStringProperty();

    public TourDetailsViewModel() {
        tourLogServiceImpl = new TourLogServiceImpl();
        tourServiceImpl = new TourServiceImpl();
        name.addListener( (arg, oldVal, newVal)->updateTourModel());
    }

    private void selectTourLog(TourLog selectedTourLog) {
        tourLogViewModel.setTourLogToEdit(selectedTourLog);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }
    public StringProperty descriptionProperty() {
        return description;
    }
    public StringProperty startProperty() {
        return start;
    }
    public StringProperty destinationProperty() {
        return destination;
    }
    public StringProperty transtypeProperty() {
        return transtype;
    }
    public ObjectProperty<Image> mapImage(){
        return mapImg;
    }
    public DoubleProperty distanceProperty() {
        return distance;
    }
    public IntegerProperty timeProperty() {
        return time;
    }
    public StringProperty maneuverProperty(){return maneuvers;}
    public StringProperty popularityProperty(){return popularity;}
    public StringProperty childFriendlinessProperty(){return childFriendliness;}


    public void setTourLogViewModel(TourLogViewModel tourLogViewModel) {
        this.tourLogViewModel = tourLogViewModel;
        if(!isInitValue)
            this.tourLogViewModel.addSelectionChangedListener(this::selectTourLog);
    }

    public void setTourModel(Tour tourModel) {
        isInitValue = true;
        if( tourModel ==null ) {
            // select the first in the list
            name.set("");
            return;
        }

        this.tour = tourModel;
        name.setValue( tourModel.getName() );
        description.setValue( tourModel.getDescription() );
        start.setValue( tourModel.getStart() );
        destination.setValue( tourModel.getDestination() );
        transtype.setValue( tourModel.getTransport_type() );
        distance.setValue(tourModel.getDistance());
        time.setValue(tourModel.getTime());
        //set observableTourLogs with data from database
        tourLogViewModel.setParentViewModel(this);
        tourLogViewModel.setTourModel(tour);
        tourLogViewModel.setTourLogs(tourLogServiceImpl.getAll(tour));
        popularity.setValue(String.valueOf(tourLogServiceImpl.getAll(tour).stream().count()));
        childFriendliness.setValue(getDifficulty());

        isInitValue = false;
    }

    private String getDifficulty(){
        if(!tour.getLog().isEmpty()) {
            double avgDifficulty = 0;
            int i = 0;
            for (TourLog tourLog : tour.getLog()) {
                avgDifficulty += Difficulty.valueOf(tourLog.getDifficulty().toUpperCase()).getCode();
                i++;
            }
            avgDifficulty = avgDifficulty / i;
            if(avgDifficulty < 1.5){
                return "easy";
            }else if(avgDifficulty < 2.5){
                return "medium";
            }else{
                return "hard";
            }
        }
        return null;
    }

    public void setTourMap(Tour tourModel) {
        isInitValue = true;
        if( tourModel ==null ) {
            return;
        }

        this.tour = tourModel;
        mapImg.setValue(tourModel.getImage());
        isInitValue = false;
    }

    public void setTourManeuvers(Tour tourModel) {
        try {
            if(tourModel!=null)
            maneuvers.set(listToString(MapQuest.getNarratives(tourModel.getStart(), tourModel.getDestination())));
        } catch (JsonProcessingException e) {
           logger.error(e.getMessage());
        }
    }
    public static String listToString(List<?> list) {
        String result = "";
        for (Object o : list) {
            result += o + "\n";
        }
        byte[] bytes = result.getBytes(StandardCharsets.UTF_8);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public void updateTourModel() {
        if( !isInitValue ) {
            tour.setName(name.get());
            tour.setDescription(description.get());
            tour.setTransport_type(transtype.get());
            tour.setDistance(distance.get());
            tour.setTime(time.get());

            tourServiceImpl.updateTour(tour);
        }
    }
}
