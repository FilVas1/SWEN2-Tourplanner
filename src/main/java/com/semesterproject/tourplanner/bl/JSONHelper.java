package com.semesterproject.tourplanner.bl;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class JSONHelper {

    private static JsonNode getJsonObj(String json) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(json);
    }


    public static String getStringFromJson(String json, String gets) throws JsonProcessingException {
        return getJsonObj(json).get("route").get(gets).asText();
    }

    public static List<String> getListFromJson(String json) throws JsonProcessingException {
       List<String> narratives = new ArrayList<>();
        JsonFactory jsonFactory = new JsonFactory();
        ObjectMapper objectMapper = new ObjectMapper(jsonFactory);

        JsonNode arrayNode = objectMapper.readTree(json).get("route").get("legs");
        if (arrayNode.isArray()) {
            for (JsonNode jsonNode : arrayNode) {
                JsonNode maneuversNode = jsonNode.get("maneuvers");
                for (JsonNode narNode : maneuversNode) {
                    String narrativeFieldNode = narNode.get("narrative").asText();
                    narratives.add(narrativeFieldNode);
                }
            }
        }
            return narratives;
    }

    public static int getIntFromJson(String json, String gets) throws JsonProcessingException {
        return getJsonObj(json).get("route").get(gets).asInt();
    }

    public static double getDoubleFromJson(String json, String gets) throws JsonProcessingException {
        return getJsonObj(json).get("route").get(gets).asDouble();
    }

}
