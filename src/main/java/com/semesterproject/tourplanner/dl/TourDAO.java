package com.semesterproject.tourplanner.dl;

import com.semesterproject.tourplanner.bl.Logging.LoggerFactory;
import com.semesterproject.tourplanner.bl.Logging.LoggerWrapper;
import com.semesterproject.tourplanner.models.Tour;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class TourDAO implements DAO<Tour>{
    static Connection connection;
    PreparedStatement insert, select, delete, update;
    private static final LoggerWrapper logger = LoggerFactory.getLogger(TourDAO.class);

    public TourDAO() {
        connection = Database.getInstance().getConnection();
        insert = null;
        select = null;
        delete = null;
        update = null;
    }

    @Override
    public Optional<Tour> get(int id) {
        return Optional.empty();
    }


    @Override
    public ArrayList<Tour> getAll(Integer optional) {
        ArrayList<Tour> allTours= new ArrayList<>();
        try {
            select = null;
            select = connection.prepareStatement("SELECT * FROM tour");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        try {
            ResultSet result = select.executeQuery();
            while (result.next()) {
                int id = result.getInt(1);
                String name = result.getString(2);
                String description = result.getString(3);
                String start = result.getString(4);
                String destination = result.getString(5);
                String transportType = result.getString(6);
                int distance = result.getInt(7);
                int time = result.getInt(8);

                Tour tour = new Tour(name, description, start, destination, transportType, distance, time, id);
                allTours.add(tour);
            }
            select.close();
            return allTours;
        } catch (SQLException e) {
           logger.error(e.getMessage());
        }
        return null;
    }


    public Boolean checkUnique(String tourname) throws SQLException {
        try {
            select = null;
            select = connection.prepareStatement("SELECT * FROM tour WHERE name=?");
            select.setString(1, tourname);
                ResultSet result = select.executeQuery();
            return !result.next();
            } catch (SQLException e) {
               logger.warn(e.getMessage());
               throw e;
            }
    }
    @Override
    public Tour create(Tour tour) throws SQLException {
        try{
            insert = connection.prepareStatement("""
                INSERT INTO tour(name, description, start, destination, transport_type, distance, time)
                VALUES(?,?,?,?,?,?,?) RETURNING id
                """);
            insert.setString(1, tour.getName());
            insert.setString(2, tour.getDescription());
            insert.setString(3, tour.getStart());
            insert.setString(4, tour.getDestination());
            insert.setString(5, tour.getTransport_type());
            insert.setDouble(6, tour.getDistance());
            insert.setInt(7, tour.getTime());
            ResultSet result = insert.executeQuery();

            if (result.next()) {
                int id = result.getInt(1);
                tour.setId(id);
            }
            insert.close();
            return tour;
        } catch (SQLException e) {
            e.printStackTrace();
            logger.warn(e.getMessage());
            throw e;
        }
    }

    @Override
    public Tour update(Tour tour) {
        try{
            update = connection.prepareStatement("""
                UPDATE tour 
                SET name=?, description=?, transport_type=?, distance=?, time=?
                WHERE id=?
                """);
            update.setString(1, tour.getName());
            update.setString(2, tour.getDescription());
            update.setString(3, tour.getTransport_type());
            update.setDouble(4, tour.getDistance());
            update.setInt(5, tour.getTime());
            update.setInt(6,tour.getId());
            update.execute();

            update.close();
            return tour;
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(Tour tour) {
        try{
            delete = connection.prepareStatement("""
                DELETE FROM tour WHERE id = ?
                """);
            delete.setInt(1, tour.getId());
            delete.execute();
            delete.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
