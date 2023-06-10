package com.semesterproject.tourplanner.bl;

import com.semesterproject.tourplanner.bl.Logging.LoggerFactory;
import com.semesterproject.tourplanner.bl.Logging.LoggerWrapper;
import com.semesterproject.tourplanner.dl.TourDAO;
import com.semesterproject.tourplanner.models.Tour;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

public class TourServiceImpl implements TourService{
    private static TourDAO tourDAO;
    private static TourLogServiceImpl tourLogServiceImpl;
    private static final LoggerWrapper logger = LoggerFactory.getLogger(TourServiceImpl.class);

    public TourServiceImpl() {
        tourDAO = new TourDAO();
        tourLogServiceImpl = new TourLogServiceImpl();
    }
    public TourServiceImpl(TourDAO tourDAO) {
        this.tourDAO = tourDAO;
        tourLogServiceImpl = new TourLogServiceImpl();
    }

    @Override
    public ArrayList<Tour> getAllTours() {
        return tourDAO.getAll(null);
    }

    @Override
    public Tour createTour(Tour tour) throws Exception {
        if(tourDAO.checkUnique(tour.getName())) {
            try {
                MapQuest mapquest = new MapQuest(tour);
                tour.setMap(mapquest);
                tour.setDistance(mapquest.getCalculatedDistance());
                tour.setTime(mapquest.getCalculatedTime());
            }catch (MapException e){
                logger.error(e.getMessage());
                throw e;
            }

            Tour tourDB;
            tourDB = tourDAO.create(tour);

            return tourDB;
        }else{
            throw new Exception("Tour with this name already exists.");
        }
    }

    @Override
    public Tour createImportedTour(Tour tour) throws Exception {
        if(tourDAO.checkUnique(tour.getName())) {
            try {
                MapQuest mapquest = new MapQuest(tour);
                tour.setMap(mapquest);
            }catch (MapException e){
                logger.error(e.getMessage());
                throw e;
            }

            Tour tourDB;
            tourDB = tourDAO.create(tour);

            //logger.info("new Tour with ID: " + tourDB.getId() + " created!");
            return tourDB;
        }else{
            throw new Exception("Tour with this name already exists.");
        }
    }

    @Override
    public Boolean isUnique(String tourname){
        try {
            return tourDAO.checkUnique(tourname);
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    @Override
    public Tour updateTour(Tour tour){
        return tourDAO.update(tour);
    }

    @Override
    public void removeTour(Tour tour){
        tourLogServiceImpl.removeAllTourLogs(tour);
        int deletedTour = tour.getId();
        tourDAO.delete(tour);
        //FileHelper.delFile(getMapImgPath(tour.getName()));
    }

    public void removeTourImg(Tour tour){
        Path filePath = Paths.get(getMapImgPath(tour.getName()));
        FileHelper.delFile(filePath);
    }


    public static String getMapImgPath(String name){
        return ConfigHelper.getIniString(ConfigHelper.getConfigIni(), "map", "path") + name + ".jpg";
    }

}
