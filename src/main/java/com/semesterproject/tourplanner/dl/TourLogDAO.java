package com.semesterproject.tourplanner.dl;

import com.semesterproject.tourplanner.bl.Logging.LoggerFactory;
import com.semesterproject.tourplanner.bl.Logging.LoggerWrapper;
import com.semesterproject.tourplanner.models.Tour;
import com.semesterproject.tourplanner.models.TourLog;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class TourLogDAO implements DAO<TourLog>{
    private static final LoggerWrapper logger = LoggerFactory.getLogger(TourLogDAO.class);
    PreparedStatement insert, select, delete, update;
    Connection connection ;


    public TourLogDAO() {
        connection = Database.getInstance().getConnection();
         insert = null;
         select = null;
         delete = null;
         update = null;
    }

    @Override
    public Optional<TourLog> get(int id) {
        return Optional.empty();
    }

    @Override
    public ArrayList<TourLog> getAll(Integer tourId) {
        ArrayList<TourLog> allTourLogs= new ArrayList<>();
        try {
            select = null;
            select = connection.prepareStatement("SELECT * FROM tourlog WHERE tour_id = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            select.setInt(1, tourId);
            ResultSet result = select.executeQuery();
            while (result.next()) {
                int id = result.getInt(1);
                int tour_id = result.getInt(2);
                LocalDate date = result.getDate(3).toLocalDate();
                String comment = result.getString(4);
                String difficulty = result.getString(5);
                int total_time = result.getInt(6);
                int rating = result.getInt(7);
                int distance = result.getInt(8);

                TourLog tourLog  = new TourLog(id, tour_id, date, comment, difficulty, total_time, rating, distance);
                allTourLogs.add(tourLog);
            }
            select.close();
            return allTourLogs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<TourLog> getAllLogs() {
        ArrayList<TourLog> allTourLogs= new ArrayList<>();
        try {
            select = null;
            select = connection.prepareStatement("SELECT * FROM tourlog");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet result = select.executeQuery();
            while (result.next()) {
                int id = result.getInt(1);
                int tour_id = result.getInt(2);
                LocalDate date = result.getDate(3).toLocalDate();
                String comment = result.getString(4);
                String difficulty = result.getString(5);
                int total_time = result.getInt(6);
                int rating = result.getInt(7);
                int distance = result.getInt(8);

                TourLog tourLog  = new TourLog(id, tour_id, date, comment, difficulty, total_time, rating, distance);
                allTourLogs.add(tourLog);
            }
            select.close();
            return allTourLogs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public TourLog create(TourLog tourLog) {
        try{
            insert = connection.prepareStatement("""
                INSERT INTO tourlog(tour_id, date, comment, difficulty, total_time, rating, distance)
                VALUES(?,?,?,?,?,?,?) RETURNING id
                """);
            insert.setInt(1, tourLog.getTourId());
            insert.setDate(2,Date.valueOf(tourLog.getDate()));
            insert.setString(3,tourLog.getComment());
            insert.setString(4,tourLog.getDifficulty());
            insert.setInt(5,tourLog.getTotalTime());
            insert.setInt(6,tourLog.getRating());
            insert.setInt(7,tourLog.getDistance());
            ResultSet result = insert.executeQuery();

            if (result.next()) {
                int id = result.getInt(1);
                tourLog.setId(id);
            }
            insert.close();
            return tourLog;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(TourLog tourLog) {
        try{
            delete = null;
            delete = connection.prepareStatement("""
                DELETE FROM tourlog WHERE id = ?
                """);
            delete.setInt(1, tourLog.getId());
            delete.execute();
            delete.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll(Tour tour) {
        try{
            delete = null;
            delete = connection.prepareStatement("""
                DELETE FROM tourlog WHERE tour_id = ?
                """);
            delete.setInt(1, tour.getId());
            delete.execute();
            delete.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public TourLog update(TourLog tourLog) {
        try{
            update = connection.prepareStatement("""
                UPDATE tourlog
                SET date=?, comment=?, difficulty=?, total_time=?, rating=?, distance=?
                WHERE id=?
                """);
            update.setDate(1,Date.valueOf(tourLog.getDate()));
            update.setString(2,tourLog.getComment());
            update.setString(3,tourLog.getDifficulty());
            update.setInt(4,tourLog.getTotalTime());
            update.setInt(5,tourLog.getRating());
            update.setInt(6,tourLog.getDistance());
            update.setInt(7,tourLog.getId());
            update.execute();
            update.close();
            return tourLog;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}
