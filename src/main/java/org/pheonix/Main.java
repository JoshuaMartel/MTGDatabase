package org.pheonix;


import org.opencv.core.Core;
import org.pheonix.business.BusinessLogic;
import org.pheonix.database.QueryHandler;
import org.pheonix.database.DatabaseConnector;
import org.pheonix.exception.JDBCExceptionHandler;
import org.pheonix.ui.MainWindow;

import java.sql.SQLException;
import java.util.Properties;


public class Main {
    public static void main(String[] args) {
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        String configFileName ="/local_config_only.properties";

        ConfigHelper configHelper = new ConfigHelper(configFileName);
        Properties config = configHelper.readConfigFile();

        //System.out.println(config.values());
        DatabaseConnector dsCon = new DatabaseConnector();
        //new SearchWindow();
        QueryHandler queryHandler;
        JDBCExceptionHandler exceptionHandler = new JDBCExceptionHandler();
        try{
            queryHandler = new QueryHandler(dsCon.getDataSource(config));

            BusinessLogic logic = new BusinessLogic(queryHandler, config);
            //new InsertCardWindow(logic,config.getProperty("imageFolderUri"));
            new MainWindow(logic);
        }catch (SQLException e){
            exceptionHandler.printSQLException(e);
        }

    }
}