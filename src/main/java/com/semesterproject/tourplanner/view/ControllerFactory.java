package com.semesterproject.tourplanner.view;


import com.semesterproject.tourplanner.viewmodels.*;

public class ControllerFactory {
    private final MainWindowViewModel mainWindowViewModel;
    private final SearchBarViewModel searchBarViewModel;
    private final ToursOverviewModel toursOverviewModel;
    private final TourDetailsViewModel tourDetailsViewModel;
    private final NewTourViewModel newTourViewModel;
    private final NewLogViewModel newLogViewModel;
    private final EditLogViewModel editLogViewModel;
    private final TourLogViewModel tourLogViewModel;

    public ControllerFactory() {
        searchBarViewModel = new SearchBarViewModel();
        toursOverviewModel = new ToursOverviewModel();
        tourDetailsViewModel = new TourDetailsViewModel();
        newTourViewModel = new NewTourViewModel();
        newLogViewModel = new NewLogViewModel();
        editLogViewModel = new EditLogViewModel();
        tourLogViewModel = new TourLogViewModel();

        tourDetailsViewModel.setTourLogViewModel(tourLogViewModel);
        tourLogViewModel.setEditLogViewModel(editLogViewModel);
        mainWindowViewModel = new MainWindowViewModel(searchBarViewModel, toursOverviewModel, tourDetailsViewModel);

    }

    //
    // Factory-Method Pattern
    //
    public Object create(Class<?> controllerClass) {
        if (controllerClass == MainWindowController.class) {
            return new MainWindowController(mainWindowViewModel);
        } else if (controllerClass == SearchBarController.class) {
            return new SearchBarController(searchBarViewModel);
        }else if (controllerClass == ToursOverviewController.class) {
            return new ToursOverviewController(toursOverviewModel);
        }else if (controllerClass == TourDetailsController.class) {
            return new TourDetailsController(tourDetailsViewModel);
        }else if (controllerClass == NewTourController.class) {
            return new NewTourController(newTourViewModel);
        }else if (controllerClass == NewLogController.class) {
            return new NewLogController(newLogViewModel);
        }else if (controllerClass == EditLogController.class) {
            return new EditLogController(editLogViewModel);
        }else if (controllerClass == TourLogsController.class) {
            return new TourLogsController(tourLogViewModel);
        }
        throw new IllegalArgumentException("Unknown controller class: " + controllerClass);
    }


    //
    // Singleton-Pattern with early-binding
    //
    private static ControllerFactory instance = new ControllerFactory();

    public static ControllerFactory getInstance() {
        return instance;
    }

}
