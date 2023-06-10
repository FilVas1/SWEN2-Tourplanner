package com.semesterproject.tourplanner.viewmodels;

import com.semesterproject.tourplanner.bl.TourLogServiceImpl;
import com.semesterproject.tourplanner.models.TourLog;

public class NewLogViewModel {
    private static TourLogServiceImpl tourLogServiceImpl;
    private static TourLog newTourLog;

    public Boolean notANumber(String value) {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public Integer StringToInt(String value){
        if(notANumber(value)){
            return Integer.parseInt(value);
        }
        return 0;
    }
}
