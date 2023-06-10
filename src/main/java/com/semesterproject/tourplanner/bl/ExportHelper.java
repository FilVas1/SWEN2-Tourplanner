package com.semesterproject.tourplanner.bl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.semesterproject.tourplanner.models.Tour;
import com.semesterproject.tourplanner.models.TourLog;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class ExportHelper {
    static TourServiceImpl tourService = new TourServiceImpl();
    static TourLogServiceImpl tourLogService = new TourLogServiceImpl();

    public static void exportTour(Tour tour) throws FileNotFoundException {
        String file = chooseFile("export");
        PrintWriter out = new PrintWriter(file+".txt");

        out.println(tour.getName());
        out.println(tour.getDescription());
        out.println(tour.getStart());
        out.println(tour.getDestination());
        out.println(tour.getTransport_type());
        out.println(tour.getDistance());
        out.println(tour.getTime());
        out.println("\n");

        for (TourLog tourLog : tour.getLog()) {
            out.println(tourLog.getDate());
            out.println(tourLog.getComment());
            out.println(tourLog.getDifficulty());
            out.println(tourLog.getTotalTime());
            out.println(tourLog.getRating());
            out.println(tourLog.getDistance());
            out.println("\n");
        }

        out.close();
    }

    public static String chooseFile(String button){
        JFileChooser chooser = new JFileChooser();
        int returnValue = chooser.showDialog(null, button);
        //check if clicked
        if(returnValue == JFileChooser.APPROVE_OPTION)
        {
            return chooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    public static Tour importTour() throws Exception {
        String file = chooseFile("import");
        assert file != null;
        FileReader fr = new FileReader(file);
        Scanner inFile = new Scanner(fr);

        String name = inFile.nextLine();
        String description = inFile.nextLine();
        String start = inFile.nextLine();
        String destination = inFile.nextLine();
        String transportType = inFile.nextLine();
        double distance = Double.parseDouble(inFile.nextLine());
        int time = Integer.parseInt(inFile.nextLine());
        Tour importedTour = new Tour(name, description, start, destination, transportType, (int) distance, time);
        Tour tourDB = tourService.createImportedTour(importedTour);

        ArrayList<TourLog> importedLogs = new ArrayList<>();
        while (inFile.hasNext())
        {
            inFile.nextLine();
            inFile.nextLine();
            LocalDate date = LocalDate.parse(inFile.nextLine());
            String comment = inFile.nextLine();
            String difficulty = inFile.nextLine();
            int totalTime = Integer.parseInt(inFile.nextLine());
            int rating = Integer.parseInt(inFile.nextLine());
            int logDistance = Integer.parseInt(inFile.nextLine());
            TourLog importedTourLog =  new TourLog(tourDB.getId(), date, comment, difficulty, totalTime, rating, logDistance);
            tourLogService.createTourLog(importedTourLog);
            importedLogs.add(importedTourLog);
        }

        importedTour.setLog(importedLogs);

        inFile.close();

        return importedTour;
    }

    public static void downloadManeuver(Tour tour) throws FileNotFoundException, JsonProcessingException {
        String file = chooseFile("download");
        PrintWriter out = new PrintWriter(file + ".txt");

        for (String line : MapQuest.getNarratives(tour.getStart(), tour.getDestination())) {
            System.out.println(line);
            out.println(line);
        }
        out.close();
    }
}
