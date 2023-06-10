package com.semesterproject.tourplanner.viewmodels;

import com.semesterproject.tourplanner.models.Tour;

public class NewTour {
    private Tour createTour;

    private static NewTour instance = new NewTour();

    public static NewTour getInstance() {
        return instance;
    }

    private boolean isCancelled;

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public Tour getCreateTour() {
        return createTour;
    }

    public void setCreateTour(Tour createTour) {
        this.createTour = createTour;
    }

}
