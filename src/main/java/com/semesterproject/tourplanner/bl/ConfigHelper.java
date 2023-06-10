package com.semesterproject.tourplanner.bl;

import com.semesterproject.tourplanner.bl.Logging.LoggerFactory;
import com.semesterproject.tourplanner.bl.Logging.LoggerWrapper;
import org.ini4j.Wini;
import java.io.File;
import java.io.IOException;

public class ConfigHelper {
    public static final String CONFIG_INI = "config.ini"; //located at Tourplanner folder
    private static final LoggerWrapper logger = LoggerFactory.getLogger(ConfigHelper.class);

    //reads int from config.ini
    public static int getIniInt(String fileName, String sectionName, String optionName){
        Wini ini = null;
        try {
            ini = new Wini(new File(fileName));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        assert ini != null;
        return ini.get(sectionName, optionName, int.class);
    }

    //reads string from config.ini
    public static String getIniString(String fileName, String sectionName, String optionName){
        Wini ini = null;
        try {
            ini = new Wini(new File(fileName));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        assert ini != null;
        return ini.get(sectionName, optionName, String.class);
    }


    //for setting string in config.ini
    public static void setIniString(String filename, String sectionName, String optionName, String value) {
        Wini ini;
        try {
            ini = new Wini(new File(filename));
            ini.put(sectionName, optionName, value);
            ini.store();
        } catch (IOException e) {
           logger.error(e.getMessage());
        }
    }

    //for setting int in config.ini
    public static void setIniInt(String filename, String sectionName, String optionName, int value){
        Wini ini;
        try {
            ini = new Wini(new File(filename));
            ini.put(sectionName, optionName, value);
            ini.store();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    //returns name of standard config file
    @SuppressWarnings("SameReturnValue")
    public static String getConfigIni() {
        return CONFIG_INI;
    }
}
