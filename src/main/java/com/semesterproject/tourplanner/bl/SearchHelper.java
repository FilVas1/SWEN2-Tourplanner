package com.semesterproject.tourplanner.bl;

import com.semesterproject.tourplanner.models.Tour;
import com.semesterproject.tourplanner.models.TourLog;

import java.util.List;
import java.util.stream.Collectors;

public class SearchHelper {
    TourServiceImpl tourService;
    TourLogServiceImpl tourLogService;
    public List<Tour> findMatchingTours(String searchText) {
        tourService = new TourServiceImpl();
        var tours = tourService.getAllTours();
        if (searchText==null || searchText.isEmpty()) {
            return tours;
        }
        return tours.stream()
                .filter(t->t.getName().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<TourLog> findMatchingTourLogs(String searchText) {
        tourLogService = new TourLogServiceImpl();
        var tourlogs = tourLogService.getAllTourLogs();
        if (searchText==null || searchText.isEmpty()) {
            return tourlogs;
        }
        return tourlogs.stream()
                .filter(t->t.getComment().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
    }

    //
    // Singleton-Pattern for SearchHelper with early-binding
    //
    private static SearchHelper instance = new SearchHelper();

    public static SearchHelper getInstance() { return instance; }
}
