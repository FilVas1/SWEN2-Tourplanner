package com.semesterproject.tourplanner.enums;

public enum Difficulty {
    EASY(1),
    MEDIUM(2),
    HARD(3);

    private final int code;

    Difficulty(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
