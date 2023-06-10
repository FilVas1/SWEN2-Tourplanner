package com.semesterproject.tourplanner.viewmodels;

import com.semesterproject.tourplanner.bl.SearchHelper;
import com.semesterproject.tourplanner.models.Tour;
import com.semesterproject.tourplanner.models.TourLog;

import java.util.ArrayList;
import java.util.List;

public class MainWindowViewModel {
    private SearchBarViewModel searchBarViewModel;
    private ToursOverviewModel toursOverviewModel;
    private TourDetailsViewModel tourDetailsViewModel;

    public MainWindowViewModel(SearchBarViewModel searchBarViewModel, ToursOverviewModel toursOverviewModel, TourDetailsViewModel tourDetailsViewModel) {
        this.searchBarViewModel = searchBarViewModel;
        this.toursOverviewModel = toursOverviewModel;
        this.tourDetailsViewModel = tourDetailsViewModel;

        this.searchBarViewModel.addSearchListener(this::searchTours);
        this.searchBarViewModel.addSearchListener(this::searchTourLogs);

        this.toursOverviewModel.addSelectionChangedListener(this::selectTour);
    }

    private void selectTour(Tour selectedTour){
        tourDetailsViewModel.setTourModel(selectedTour);
        tourDetailsViewModel.setTourMap(selectedTour);
        tourDetailsViewModel.setTourManeuvers(selectedTour);
    }


    private void searchTours(String searchString){
        List<Tour> tours = SearchHelper.getInstance().findMatchingTours(searchString);
        ArrayList<Tour> setList = new ArrayList<>(tours);

        toursOverviewModel.setTours(setList);
    }
    private void searchTourLogs(String searchString){
        var tourlogs = SearchHelper.getInstance().findMatchingTourLogs(searchString);
        ArrayList<TourLog> setList = new ArrayList<>(tourlogs);
        TourLogViewModel tourLogViewModel = new TourLogViewModel();
        tourLogViewModel.setTourModel(new Tour("no tour selected", "-", "-", "-", "-"));
        tourLogViewModel.setTourLogs(setList);
        tourDetailsViewModel.setTourLogViewModel(tourLogViewModel);
    }

}
