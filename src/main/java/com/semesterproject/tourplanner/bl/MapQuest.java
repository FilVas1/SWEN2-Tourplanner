package com.semesterproject.tourplanner.bl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.semesterproject.tourplanner.bl.Logging.LoggerFactory;
import com.semesterproject.tourplanner.bl.Logging.LoggerWrapper;
import com.semesterproject.tourplanner.models.Tour;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MapQuest {
    private static final LoggerWrapper logger = LoggerFactory.getLogger(MapQuest.class);
    private Tour tour;
    private int calculatedTime;
    private double calculatedDistance;
    private String sessionID;

    public MapQuest(Tour tour) throws MapException {
        this.tour = tour;
        try {
            getDirections(tour.getStart(), tour.getDestination());
            Image map = getMap(ConfigHelper.getIniString(ConfigHelper.getConfigIni(), "map", "size"));
            Image mapPdf = getMap(ConfigHelper.getIniString(ConfigHelper.getConfigIni(), "map", "sizePdf"));
            String file = ConfigHelper.getIniString(ConfigHelper.getConfigIni(), "map", "path") + tour.getName() + ".jpg";
            String filePdf = ConfigHelper.getIniString(ConfigHelper.getConfigIni(), "map", "path") + tour.getName() + "_pdf.pdf";
            FileHelper.saveImage(map, "jpg", new File(file));
            FileHelper.saveImage(mapPdf, "pdf", new File(filePdf));
            if (ConfigHelper.getIniInt(ConfigHelper.getConfigIni(), "settings", "openmap") == 1) {
                FileHelper.openDefault(file);
            }
        } catch (IOException e) {
            throw new MapException(e.getMessage());
        }
    }

    private void getDirections(String start, String destination) throws MapException {
        try {
            String json = HTTPHelper.httpGetJsonString("https://www.mapquestapi.com/directions/v2/route?key="+ ConfigHelper.getIniString(ConfigHelper.getConfigIni(), "map", "key")+"&from="+start+"&to="+destination+"&outFormat=json&ambiguities=ignore&routeType=fastest&doReverseGeocode=false&enhancedNarrative=false&avoidTimedConditions=false");
            this.calculatedDistance = JSONHelper.getDoubleFromJson(json, "distance");
            this.calculatedTime = timeInMinutes(JSONHelper.getStringFromJson(json, "formattedTime"));
            logger.info("time: " + this.calculatedTime);
            logger.info("distance: " + this.calculatedDistance);

            this.sessionID = JSONHelper.getStringFromJson(json, "sessionId");
        }catch (IOException e){
            throw new MapException(e.getMessage());
        }
    }

    public static List<String> getNarratives(String start, String destination) throws JsonProcessingException {
        String json = null;
        try {
            json = HTTPHelper.httpGetJsonString("https://www.mapquestapi.com/directions/v2/route?key="+ ConfigHelper.getIniString(ConfigHelper.getConfigIni(), "map", "key")+"&from="+start+"&to="+destination+"&outFormat=json&ambiguities=ignore&routeType=fastest&doReverseGeocode=false&enhancedNarrative=false&avoidTimedConditions=false");
        } catch (IOException e) {
            logger.error(String.valueOf(new MapException(e.getMessage())));
        }

        return JSONHelper.getListFromJson(json);
    }

    private Image getMap(String size) throws MapException {
        try {
            return HTTPHelper.httpGetImage("https://www.mapquestapi.com/staticmap/v5/map?session="+this.sessionID+"&size="+size+"&key="+ConfigHelper.getIniString(ConfigHelper.getConfigIni(), "map", "key"));
        }catch (IOException e){
            throw new MapException(e.getMessage());
        }
    }

    private int timeInMinutes(String time){
        int minutes = 0;
        String[] result = time.split(":");
        minutes += Double.parseDouble(result[0]) * 60;
        minutes += Double.parseDouble(result[1]);
        minutes += Double.parseDouble(result[2]) / 60;
        return minutes;
    }

    public int getCalculatedTime() {
        return calculatedTime;
    }

    public double getCalculatedDistance() {
        return calculatedDistance;
    }

    public static Boolean testLocation(String start) throws MapException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = HTTPHelper.httpGetJsonString("https://www.mapquestapi.com/geocoding/v1/address?maxMatches=4&key=" + ConfigHelper.getIniString(ConfigHelper.getConfigIni(), "map", "key") + "&location=" + start);
            Map<String, Object> responseMap = objectMapper.readValue(json, Map.class);
            List<Object> results = (List<Object>) responseMap.get("results");
            return !results.isEmpty();
        }catch (IOException e){
            throw new MapException(e.getMessage());
        }
    }

}


