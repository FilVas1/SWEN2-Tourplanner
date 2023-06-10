package com.semesterproject.tourplanner.bl.Logging;

public interface LoggerWrapper {
    void debug(String message);
    void fatal(String message);
    void error(String message);
    void warn(String message);
    void info(String message);
}
