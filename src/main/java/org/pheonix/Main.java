package org.pheonix;


import org.pheonix.business.QueryHandler;
import org.pheonix.ui.InsertCardWindow;
import org.pheonix.ui.SearchWindow;

import java.util.List;
import java.util.Properties;
import java.util.Vector;

public class Main {
    public static void main(String[] args) {
        String configFileName ="/local_config_only.properties";

        ReadConfigFile readConfigFile = new ReadConfigFile(configFileName);
        Properties config = readConfigFile.readConfigFile();

        System.out.println(config.values());

        //new SearchWindow();
        QueryHandler queryHandler = new QueryHandler();
        new InsertCardWindow(queryHandler);
    }
}