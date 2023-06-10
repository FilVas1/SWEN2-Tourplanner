package com.semesterproject.tourplanner.bl;

import com.semesterproject.tourplanner.dl.TourDAO;
import com.semesterproject.tourplanner.models.Tour;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ExtendWith(MockitoExtension.class)
class TourServiceImplTest {
   // private static final LoggerWrapper logger = LoggerFactory.getLogger(TourServiceImplTest.class);
    // System under Test (SuT)
    private TourService tourService;

    // Mocks
    @Mock
    private TourDAO tourDAO;

    @Mock
    private TourDAO tourDAO2;   //because name of tour must be unique

    @BeforeEach
    void setUp() {
        this.tourService = new TourServiceImpl(tourDAO);
    }

    @AfterEach
    void reset_mocks() {
        Mockito.reset(tourDAO);
    }

    @Test
    void getAllTours() {
        Tour tour = createTourMock();
        Mockito.when(tourDAO.getAll(null))
                .thenReturn(new ArrayList<>(Collections.singleton(tour)));
        List<Tour> expected = new ArrayList<>(Collections.singleton(tour));
        List<Tour> actual = tourService.getAllTours();
        assertReflectionEquals(expected, actual);
    }

    @Test
    void createTour() throws SQLException {
        Tour expected = createTourMock();
        Mockito.when(tourDAO.create(expected))
                .thenReturn(expected);

        Exception exception = assertThrows(Exception.class, () -> {
            tourService.createTour(expected);
        });

        String expectedMessage = "Tour with this name already exists.";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void createImportedTour() throws Exception {
        Tour expected = createTourMock();
        Mockito.when(tourDAO.create(expected))
                .thenReturn(expected);

        Exception exception = assertThrows(Exception.class, () -> {
            tourService.createImportedTour(expected);
        });

        String expectedMessage = "Tour with this name already exists.";
        String actualMessage = exception.getMessage();

        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void isUnique() throws SQLException {
        Tour tour = createTourMock();
        Boolean expected = tourDAO.checkUnique(tour.getName());
        Boolean actual = tourService.isUnique(tour.getName());
        assertReflectionEquals(expected, actual);
    }

    @Test
    void updateTour() {
        Tour expected = createTourMock();
        Mockito.when(tourDAO.update(expected))
                .thenReturn(expected);
        Tour actual = tourService.updateTour(expected);
        assertReflectionEquals(expected, actual);
    }

    @Test
    void removeTour() {
        Tour expected = createTourMock();
        tourService.removeTour(expected);
        Mockito.verify(tourDAO).delete(expected);
    }


    @Test
    void getMapImgPath() {
        Tour tour = createTourMock();
        String expected = TourServiceImpl.getMapImgPath(tour.getName());
        String actual = ConfigHelper.getIniString(ConfigHelper.getConfigIni(), "map", "path") + "mock.jpg";
        assertReflectionEquals(expected, actual);
    }

    private Tour createTourMock() {
        return new Tour("mock", "description", "wien", "linz","transport type", 100,100);
    }
}