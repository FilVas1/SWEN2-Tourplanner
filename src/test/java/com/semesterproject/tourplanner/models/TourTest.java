package com.semesterproject.tourplanner.models;

import com.semesterproject.tourplanner.bl.Logging.LoggerFactory;
import com.semesterproject.tourplanner.bl.Logging.LoggerWrapper;
import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class TourTest {
    private static final LoggerWrapper logger = LoggerFactory.getLogger(TourTest.class);
    private Tour tour;

    @BeforeEach
    void setUp() {
        tour = new Tour("testName", "testDescription", "Wien", "Linz", "testTransType",230,60,0);
    }

    /*@Test
    void setDistanceInvalid() {
        try{
            tour.setDistance(-100);
            fail("negative distance in setDistance() should trigger an exception");
        }catch (IllegalArgumentException e){
            logger.info("properly caught negative value in setDistance() : " + e);
        }catch (Exception e){
            fail("wrong exception thrown for setDistance() with negative value");
        }
    }*/

    @Test
    void setDistance() {
        tour.setDistance(350);
        int expResult = 350;
        assertEquals(expResult, tour.getDistance());
    }


    @Test
    void testToString() {
        String expResult = "testName";
        assertEquals(expResult, tour.toString());
    }
}