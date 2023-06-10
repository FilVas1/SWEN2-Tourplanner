package com.semesterproject.tourplanner.viewmodels;

import com.semesterproject.tourplanner.models.Tour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TourDetailsViewModelTest {
    TourDetailsViewModel tourDetailsViewModel;
    private Tour tour, tourUpdated;

    @BeforeEach
    void setUp(){
        tourDetailsViewModel = new TourDetailsViewModel();
        tour = new Tour("testName", "description", "wien", "linz","transport type", 100,100,0);
        tourUpdated = new Tour("testName", "updated description", "wien", "linz","transport type", 100,100,0);
        tourDetailsViewModel.setTourLogViewModel(new TourLogViewModel());
    }

    @Test
    void setTourModel() {
        tourDetailsViewModel.setTourModel(tour);
        assertEquals(tourDetailsViewModel.startProperty().get(), tour.getStart());
    }

    @Test
    void updateTourModel() {
        tourDetailsViewModel.setTourModel(tour);
        tourDetailsViewModel.nameProperty().set(tourUpdated.getName());
        tourDetailsViewModel.descriptionProperty().set(tourUpdated.getDescription());
        tourDetailsViewModel.transtypeProperty().set(tourUpdated.getTransport_type());
        tourDetailsViewModel.distanceProperty().set(tourUpdated.getDistance());
        tourDetailsViewModel.timeProperty().set(tourUpdated.getTime());

        tourDetailsViewModel.updateTourModel();
        assertEquals(tourDetailsViewModel.getTour().getDescription(), tourUpdated.getDescription());
    }

    @Test
    void updateTourModelOldChanged() {
        String oldDescription = tour.getDescription();
        tourDetailsViewModel.setTourModel(tour);
        tourDetailsViewModel.nameProperty().set(tourUpdated.getName());
        tourDetailsViewModel.descriptionProperty().set(tourUpdated.getDescription());
        tourDetailsViewModel.transtypeProperty().set(tourUpdated.getTransport_type());
        tourDetailsViewModel.distanceProperty().set(tourUpdated.getDistance());
        tourDetailsViewModel.timeProperty().set(tourUpdated.getTime());

        tourDetailsViewModel.updateTourModel();
        assertNotEquals(tourDetailsViewModel.getTour().getDescription(), oldDescription);
    }
}