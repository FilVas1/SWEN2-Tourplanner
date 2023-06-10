package com.semesterproject.tourplanner.bl;

import com.semesterproject.tourplanner.models.Tour;
import com.semesterproject.tourplanner.models.TourLog;

import java.util.ArrayList;
import java.util.List;

public interface TourLogService {
    TourLog createTourLog(TourLog tourLog);
    TourLog updateTourLog(TourLog tourLog);
    void removeTourLog(TourLog tourLog);
    void removeAllTourLogs(Tour tour);
    List<TourLog> getAll(Tour tour);

    ArrayList<TourLog> getAllTourLogs();
}
