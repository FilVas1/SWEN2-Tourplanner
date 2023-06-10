package com.semesterproject.tourplanner.bl.Logging;

import com.semesterproject.tourplanner.bl.Logging.InitializedState;
import com.semesterproject.tourplanner.bl.Logging.LoggerStateBase;
import com.semesterproject.tourplanner.bl.Logging.LoggerWrapper;
import com.semesterproject.tourplanner.bl.Logging.UninitializedState;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class LoggerWrapperImpl implements LoggerWrapper {
    private Logger logger;
    private LoggerStateBase state = new UninitializedState();

    @Override
    public void debug(String message) {
        this.state.debug(message);
    }

    @Override
    public void fatal(String message) {
        this.state.fatal(message);
    }

    @Override
    public void error(String message) {
        this.state.error(message);
    }

    @Override
    public void warn(String message) {
        this.state.warn(message);
    }

    @Override
    public void info(String message) {
        this.state.info(message);
    }

    public void initialize(Class<?> clazz) {
        this.state = new InitializedState(LogManager.getLogger(clazz.getName()));
    }
}
