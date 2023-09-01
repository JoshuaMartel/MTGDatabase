package org.pheonix.ui.main.window;

import org.pheonix.ConfigStore;
import org.pheonix.business.BusinessLogic;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.AdjustmentListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ImagePanel extends JPanel {
    int imageWidth, imageHeight;
    JFrame parentWindow;
    Properties config;
    Map<String, JLabel> imageLabels;

    public ImagePanel(JFrame parent, Properties con, LayoutManager layout) {
        parentWindow = parent;
        config = con;
        imageWidth = Integer.parseInt(config.getProperty(ConfigStore.IMAGE_WIDTH.getValue()));
        imageHeight = Integer.parseInt(config.getProperty(ConfigStore.IMAGE_HEIGHT.getValue()));
        this.setLayout(layout);
        imageLabels = new HashMap<>();
    }

    public void addLabel(String imageName, ImageIcon image, int owned, int available) {
        JLabel label = new JLabel() {
          protected void paintComponent(Graphics graphics) {
              super.paintComponent(graphics);
              graphics.drawImage(image.getImage(), 0,0, null);
              graphics.drawString(
                      "Owned: " + owned + ", available: " + available,
                      10,
                      imageHeight + 30 - 10);
          }
        };

        label.setMinimumSize(new Dimension(imageWidth,imageHeight + 30));
        label.setPreferredSize(new Dimension(imageWidth,imageHeight + 30));
        label.setMaximumSize(new Dimension(imageWidth,imageHeight + 30));
        label.setBorder(new BevelBorder(BevelBorder.RAISED));
        imageLabels.put(imageName, label);

        this.add(label);
    }

    public void removeLabel(String labelName) {
        this.remove(imageLabels.remove(labelName));
    }

    public void hideLabel(String name) {
        imageLabels.get(name).setVisible(false);
    }

    public void revealLabel(String name){
        imageLabels.get(name).setVisible(true);
    }

    public Map<String, JLabel> getImageLabels() {
        return imageLabels;
    }
}
