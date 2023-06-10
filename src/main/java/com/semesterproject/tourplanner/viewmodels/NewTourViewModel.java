package com.semesterproject.tourplanner.viewmodels;

import com.semesterproject.tourplanner.bl.Logging.LoggerFactory;
import com.semesterproject.tourplanner.bl.Logging.LoggerWrapper;
import com.semesterproject.tourplanner.bl.MapException;
import com.semesterproject.tourplanner.bl.MapQuest;
import com.semesterproject.tourplanner.bl.TourServiceImpl;
import com.semesterproject.tourplanner.models.Tour;

public class NewTourViewModel {
    private static TourServiceImpl tourServiceImpl;
    private static final LoggerWrapper logger = LoggerFactory.getLogger(NewTourViewModel.class);


    public NewTourViewModel() {
        tourServiceImpl = new TourServiceImpl();
    }


    public void addTour(Tour tour) throws Exception {
            Tour tourDB = tourServiceImpl.createTour(tour);
            NewTour.getInstance().setCreateTour(tourDB);
    }

    public Boolean isUnique(String tourname) {
        return tourServiceImpl.isUnique(tourname);
    }

    public Boolean validMap(String start){
        try {
            return MapQuest.testLocation(start);
        } catch (MapException e) {
            logger.warn(e.getMessage());
            return false;
        }
    }
}
