package com.semesterproject.tourplanner.viewmodels;

import com.semesterproject.tourplanner.models.Tour;
import com.semesterproject.tourplanner.models.TourLog;

public class NewTourLog {
    private TourLog createTourLog;

    private static NewTourLog instance = new NewTourLog();

    public static NewTourLog getInstance() {
        return instance;
    }

    private boolean isCancelled;

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public TourLog getCreateTourLog() {
        return createTourLog;
    }

    public void setCreateTourLog(TourLog createTourLog) {
        this.createTourLog = createTourLog;
    }

}
