package com.semesterproject.tourplanner.models;

import com.semesterproject.tourplanner.bl.Logging.LoggerFactory;
import com.semesterproject.tourplanner.bl.Logging.LoggerWrapper;
import com.semesterproject.tourplanner.bl.MapQuest;
import com.semesterproject.tourplanner.bl.TourServiceImpl;
import com.semesterproject.tourplanner.dl.TourDAO;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Tour {
    private static final LoggerWrapper logger = LoggerFactory.getLogger(Tour.class);
    private String name;
    private String description;
    private String start;
    private String destination;
    private String transport_type;
    private double distance;
    private int time;
    private MapQuest map;
    private int id;
    private Image image;

    private ArrayList<TourLog> log;

    public Tour(String name, String description, String start, String destination, String transport_type) {
        this.name = name;
        this.description = description;
        this.start = start;
        this.destination = destination;
        this.transport_type = transport_type;
        this.log = new ArrayList<>();
    }

    public Tour(String name, String description, String start, String destination, String transport_type, int distance, int time) {
        this.name = name;
        this.description = description;
        this.start = start;
        this.destination = destination;
        this.transport_type = transport_type;
        this.distance = distance;
        this.time = time;
    }

    public Tour(String name, String description, String start, String destination, String transport_type, double distance, int time, int id) {
        this.name = name;
        this.description = description;
        this.start = start;
        this.destination = destination;
        this.transport_type = transport_type;
        this.distance = distance;
        this.time = time;
        this.id = id;
    }

    public Image getImage() {
        String filename = TourServiceImpl.getMapImgPath(name);
        Image image = null;
        try {
            InputStream stream = new ByteArrayInputStream(Files.readAllBytes(Path.of(filename)));
            //InputStream stream = new FileInputStream(filename);
            image = new Image(stream);
        } catch (IOException e) {
            logger.warn(String.valueOf(e));
        }
        return image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MapQuest getMap() {
        return map;
    }

    public void setMap(MapQuest map) {
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTransport_type() {
        return transport_type;
    }

    public void setTransport_type(String transport_type) {
        this.transport_type = transport_type;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
            this.distance = distance;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public TourLog getLog(int id){
        AtomicReference<TourLog> returnLog = new AtomicReference<>();
        this.log.forEach(s -> {
            if(s.getId() == id){
                returnLog.set(s);
            }
        });
        return returnLog.get();
    }

    public ArrayList<TourLog> getLog() {
        return log;
    }

    public void setLog(ArrayList<TourLog> log) {
        this.log = log;
    }

    @Override
    public String toString() {
        return name;
    }
}
