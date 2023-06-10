package com.semesterproject.tourplanner.bl.Logging;


public class LoggerFactory {
    public static LoggerWrapper getLogger(Class<?> clazz){
        var logger = new LoggerWrapperImpl();
        logger.initialize(clazz);
        return logger;
    }
}
