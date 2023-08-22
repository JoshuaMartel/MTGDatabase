package org.pheonix;


import org.pheonix.database.QueryHandler;
import org.pheonix.database.DatabaseConnector;
import org.pheonix.exception.handling.JDBCExceptionHandler;
import org.pheonix.ui.InsertCardWindow;

import java.sql.SQLException;
import java.util.Properties;


public class Main {
    public static void main(String[] args) {
        String configFileName ="/local_config_only.properties";

        ReadConfigFile readConfigFile = new ReadConfigFile(configFileName);
        Properties config = readConfigFile.readConfigFile();

        System.out.println(config.values());
        DatabaseConnector dsCon = new DatabaseConnector();
        //new SearchWindow();
        QueryHandler queryHandler;
        JDBCExceptionHandler exceptionHandler = new JDBCExceptionHandler();
        try{
            queryHandler = new QueryHandler(dsCon.getDataSource(config));
            new InsertCardWindow(queryHandler,config.getProperty("imageFolderUri"));
        }catch (SQLException e){
            exceptionHandler.printSQLException(e);
        }

    }
}