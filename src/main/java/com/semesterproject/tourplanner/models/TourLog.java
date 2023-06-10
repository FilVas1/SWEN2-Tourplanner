package com.semesterproject.tourplanner.models;

import java.time.LocalDate;

public class TourLog {
    private int id;
    private int tourId;
    private LocalDate date;
    private String comment;
    private String difficulty;
    private int totalTime;
    private int rating;
    private int distance;

    public TourLog(int id, int tourId, LocalDate date, String comment, String difficulty, int totalTime, int rating, int distance) {
        this.id = id;
        this.tourId = tourId;
        this.date = date;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTime = totalTime;
        this.rating = rating;
        this.distance = distance;
    }

    public TourLog( int tourId, LocalDate date, String comment, String difficulty, int totalTime, int rating, int distance) {
        this.tourId = tourId;
        this.date = date;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTime = totalTime;
        this.rating = rating;
        this.distance = distance;
    }

    public TourLog(LocalDate date, String comment, String difficulty, int totalTime, int rating, int distance) {
        this.date = date;
        this.comment = comment;
        this.difficulty = difficulty;
        this.totalTime = totalTime;
        this.rating = rating;
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return comment;
    }
}
