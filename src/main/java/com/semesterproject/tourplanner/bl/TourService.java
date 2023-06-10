package com.semesterproject.tourplanner.bl;

import com.semesterproject.tourplanner.models.Tour;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TourService {
    ArrayList<Tour> getAllTours();

    Tour createTour(Tour tour) throws Exception;

    Tour createImportedTour(Tour tour) throws Exception;

    Boolean isUnique(String tourname);

    Tour updateTour(Tour tour);
    void removeTour(Tour tour);
}
