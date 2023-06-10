package com.semesterproject.tourplanner.bl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.semesterproject.tourplanner.bl.Logging.LoggerFactory;
import com.semesterproject.tourplanner.bl.Logging.LoggerWrapper;
import com.semesterproject.tourplanner.models.Tour;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapQuestTest {
    private static final LoggerWrapper logger = LoggerFactory.getLogger(MapQuestTest.class);
    MapQuest mapQuest;
    Tour tour;

    @BeforeEach
    void setUp() {
        tour = new Tour("testName", "testDescription", "Wien", "Linz", "testTransType");
        try {
            this.mapQuest = new MapQuest(tour);
        } catch (MapException e) {
            logger.error(e.getMessage());
        }
    }


    @Test
    void getNarratives() throws JsonProcessingException {
        mapQuest.getNarratives("wien", "horn");
    }

    @Test
    void getCalculatedTime() {
        assertEquals(mapQuest.getCalculatedDistance(),113.913);
    }

    @Test
   void getCalculatedDistance() {
        assertEquals(mapQuest.getCalculatedTime(),105);
    }


    @Test
    void testLocation() {
        try {
            assertEquals(mapQuest.testLocation("linz"),true);
        } catch (MapException e) {
           logger.fatal(e.getMessage());
        }
    }
}