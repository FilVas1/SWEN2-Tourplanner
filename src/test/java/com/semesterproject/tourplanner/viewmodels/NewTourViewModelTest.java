package com.semesterproject.tourplanner.viewmodels;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NewTourViewModelTest {
    NewTourViewModel newTourViewModel;

    public NewTourViewModelTest() {
        newTourViewModel=new NewTourViewModel();
    }

    @Test
    void isUniqueTrue() {
        assertEquals(newTourViewModel.isUnique("new"), true);
    }

    @Test
    void validMap() {
        assertEquals(newTourViewModel.validMap("wien"), true);
    }
    @Test
    void validMapInvalid() {
        assertEquals(newTourViewModel.validMap("x"), false);
    }
}