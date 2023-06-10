package com.semesterproject.tourplanner.dl;

import com.semesterproject.tourplanner.bl.ConfigHelper;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static Database instance = null;
    protected String connectionString;
    protected Connection connection;

    private Database() {
        try {
            this.connectionString = ConfigHelper.getIniString(ConfigHelper.getConfigIni(), "db", "url");
            this.connection = DriverManager.getConnection(connectionString, ConfigHelper.getIniString(ConfigHelper.getConfigIni(), "db", "user"), ConfigHelper.getIniString(ConfigHelper.getConfigIni(), "db", "pw"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Database getInstance() {
        if(instance==null){
            instance= new Database();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}

