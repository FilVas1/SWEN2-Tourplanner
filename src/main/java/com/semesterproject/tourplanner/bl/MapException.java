package com.semesterproject.tourplanner.bl;

public class MapException extends Exception {
    public MapException(String message){
        super("Map Error: -> "+message);
    }
}
