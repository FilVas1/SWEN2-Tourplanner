package com.semesterproject.tourplanner.bl;

import com.semesterproject.tourplanner.bl.Logging.LoggerFactory;
import com.semesterproject.tourplanner.bl.Logging.LoggerWrapper;
import com.semesterproject.tourplanner.dl.TourLogDAO;
import com.semesterproject.tourplanner.models.Tour;
import com.semesterproject.tourplanner.models.TourLog;

import java.util.ArrayList;

public class TourLogServiceImpl implements TourLogService{
    private TourLogDAO tourLogDAO;

    public TourLogServiceImpl(TourLogDAO tourLogDAO) {
        this.tourLogDAO = tourLogDAO;
    }
    public TourLogServiceImpl() {
        this.tourLogDAO = new TourLogDAO();
    }

    @Override
    public TourLog createTourLog(TourLog tourLog) {
        var tourLogDB = tourLogDAO.create(tourLog);
        return tourLogDB;
    }

    @Override
    public TourLog updateTourLog(TourLog tourlog){
        //editLogViewModel.setTourLog(tourLogListItems.getSelectionModel().getSelectedItem());
        var tourLogDB = tourLogDAO.update(tourlog);
        return tourLogDB;
    }

    @Override
    public void removeTourLog(TourLog tourLog) {
        tourLogDAO.delete(tourLog);
    }

    @Override
    public void removeAllTourLogs(Tour tour) {
        tourLogDAO.deleteAll(tour);
    }

    @Override
    public ArrayList<TourLog> getAll(Tour tour) {
        return tourLogDAO.getAll(tour.getId());
    }

    @Override
    public ArrayList<TourLog> getAllTourLogs() {
        return tourLogDAO.getAllLogs();
    }

}
