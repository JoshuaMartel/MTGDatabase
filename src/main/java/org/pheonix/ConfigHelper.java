package org.pheonix;

import java.io.*;
import java.util.Properties;

public class ConfigHelper {
    String configFileName;
    public ConfigHelper(String fileName){
        configFileName = fileName;
    }
    public Properties readConfigFile(){
        Properties props = new Properties();
        InputStream stream = getClass().getResourceAsStream(configFileName);

        try{
            props.load(stream);
        } catch (IOException e){
            System.out.println("Error reading file: config.properties");
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return props;
    }
}
