package org.pheonix;

import java.io.*;
import java.util.Properties;

public class ReadConfigFile {
    public Properties readConfigFile(){
        Properties props = new Properties();
        InputStream stream = getClass().getResourceAsStream("/config.properties");

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
