package com.semesterproject.tourplanner.bl;

import com.semesterproject.tourplanner.dl.TourLogDAO;
import com.semesterproject.tourplanner.models.Tour;
import com.semesterproject.tourplanner.models.TourLog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

@ExtendWith(MockitoExtension.class)
class TourLogServiceImplTest {
    // System under Test (SuT)
    private TourLogService tourLogService;

    // Mocks
    @Mock
    private TourLogDAO tourLogDAO;

    @BeforeEach
    void setUp() {
        this.tourLogService = new TourLogServiceImpl(tourLogDAO);
    }


    @Test
    void createTourLog() {
        TourLog expected = createTourLogMock();
        Mockito.when(tourLogDAO.create(expected))
                .thenReturn(expected);
        TourLog actual = tourLogService.createTourLog(expected);
        assertReflectionEquals(expected, actual);
    }

    @Test
    void updateTourLog() {
        TourLog expected = createTourLogMock();
        Mockito.when(tourLogDAO.update(expected))
                .thenReturn(expected);
        TourLog actual = tourLogService.updateTourLog(expected);
        assertReflectionEquals(expected, actual);
    }

    @Test
    void removeTourLog() {
        TourLog expected = createTourLogMock();
        tourLogService.removeTourLog(expected);
        Mockito.verify(tourLogDAO).delete(expected);
    }

    @Test
    void removeAllTourLogs() {
        Tour tour = createTourMock();
        tourLogService.removeAllTourLogs(tour);
        Mockito.verify(tourLogDAO).deleteAll(tour);
    }

    @Test
    void getAll() {
        List<TourLog> allLogs = createTourLogListMock();
        Tour tour = createTourMock();
        Mockito.when(tourLogDAO.getAll(tour.getId()))
                .thenReturn(new ArrayList<>(allLogs));
        List<TourLog> expected = new ArrayList<>(allLogs);
        List<TourLog> actual = tourLogService.getAll(tour);
        assertReflectionEquals(expected, actual);
    }

    @Test
    void getAllTourLogs() {
        TourLog tourLog = createTourLogMock();
        Tour tour = createTourMock();
        Mockito.when(tourLogDAO.getAllLogs())
                .thenReturn(new ArrayList<>(Collections.singleton(tourLog)));
        List<TourLog> expected = new ArrayList<>(Collections.singleton(tourLog));
        List<TourLog> actual = tourLogService.getAllTourLogs();
        assertReflectionEquals(expected, actual);
    }


    private TourLog createTourLogMock() {
        return new TourLog(1, LocalDate.parse("2022-05-08"),"comment", "easy",300, 10, 100 );
    }
    private List<TourLog> createTourLogListMock() {
        List<TourLog> allLogs= new ArrayList<>();
        TourLog log1 = new TourLog(1, LocalDate.parse("2022-05-08"),"comment 1", "easy",300, 10, 100 );
        TourLog log2 = new TourLog(1, LocalDate.parse("2022-04-10"),"comment 2", "medium",50, 7, 33 );
        allLogs.add(log1);
        allLogs.add(log2);
        return allLogs;
    }
    private Tour createTourMock() {
        return new Tour("name", "description", "wien", "linz","transport type", 100,100,1);
    }
}