package org.pheonix;


import org.pheonix.ui.SearchWindow;

import javax.swing.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {

        ReadConfigFile readConfigFile = new ReadConfigFile();
        Properties config = readConfigFile.readConfigFile();

        System.out.println(config.values());

        new SearchWindow();
    }
}