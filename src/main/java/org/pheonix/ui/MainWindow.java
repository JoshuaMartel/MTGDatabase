package org.pheonix.ui;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.pheonix.ConfigStore;
import org.pheonix.business.BusinessLogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.*;
import java.util.List;

public class MainWindow {
    int imageWidth, imageHeight;
    BusinessLogic logicHandler;
    Properties config;
    JFrame frame;
    GroupLayout bottomLayout;
    JTabbedPane tabbedPane;
    JSplitPane outerSplitPane, innerTopSplitPane;
    JPanel bottomPane, leftFilterPane, rightImagePane;
    JButton exit, search, insertCard, filter;
    Vector<ImageIcon> imageIcons;
    Vector<JLabel> labels;

    public MainWindow(BusinessLogic businessLogic){
        /**
         * Note: elements on the same line are split between two sides.
         * The pane layering from outside to inside is as follows:
         *
         * outerSplitPane(vertical split)
         *
         * bottom pane, innerTopSplitPane(horizontal split)
         *
         * *innerTopSplitPane*
         * -> leftFilterPane, rightImagePane
         */
        init(businessLogic);
        createBottomLayout();

        leftFilterPane.setVisible(false);
        innerTopSplitPane.setResizeWeight(0.5);
        innerTopSplitPane.setLeftComponent(leftFilterPane);
        innerTopSplitPane.setRightComponent(new JScrollPane(rightImagePane));

        outerSplitPane.setResizeWeight(1.0);
        outerSplitPane.setBottomComponent(bottomPane);
        outerSplitPane.setTopComponent(innerTopSplitPane);

        ImageLoader imageLoader = new ImageLoader();
        imageLoader.execute();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(outerSplitPane, BorderLayout.CENTER);
        frame.pack();
        frame.setSize(new Dimension(800,600));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void init(BusinessLogic businessLogic){
        logicHandler = businessLogic;
        config = businessLogic.getConfig();
        imageWidth = Integer.parseInt(config.getProperty(ConfigStore.IMAGE_WIDTH.getValue()));
        imageHeight = Integer.parseInt(config.getProperty(ConfigStore.IMAGE_HEIGHT.getValue()));

        frame = new JFrame();
        labels = new Vector<>();

        bottomPane = new JPanel();
        bottomPane.setMinimumSize(new Dimension(320,70));
        bottomPane.setPreferredSize(new Dimension(320,70));
        //leftFilterPane = new JPanel(new FlowLayout());
        leftFilterPane = new FilterPanel(frame);
        Dimension screenResolution = Toolkit.getDefaultToolkit().getScreenSize();
        rightImagePane = new JPanel(new GridLayout(0, screenResolution.width / imageWidth, 10,10));

        outerSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

        innerTopSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        // tabbedPane = new JTabbedPane();
    }

    private void createBottomLayout() {
        bottomLayout = new GroupLayout(bottomPane);
        bottomLayout.setAutoCreateGaps(true);
        bottomLayout.setAutoCreateContainerGaps(true);

        Vector<JButton> buttons = instantiateButtons();
        GroupLayout.SequentialGroup leftSeqHorButtons = bottomLayout.createSequentialGroup();
        GroupLayout.ParallelGroup leftParVertButtons = bottomLayout.createParallelGroup(GroupLayout.Alignment.BASELINE);
        for (JButton button: buttons){
            button.setPreferredSize(new Dimension(100,60));
            leftSeqHorButtons.addComponent(button);
            leftParVertButtons.addComponent(button);
        }
        bottomLayout.setHorizontalGroup(leftSeqHorButtons);
        bottomLayout.setVerticalGroup(leftParVertButtons);
    }

    private Vector<JButton> instantiateButtons(){
        exit = new JButton("Exit");
        search = new JButton("Search");
        insertCard = new JButton("Add Card");
        filter = new JButton("filter");

        exit.addActionListener(e -> System.exit(0));

        search.addActionListener(e -> new SearchWindow());

        insertCard.addActionListener(e -> new InsertCardWindow(logicHandler, ""));

        filter.addActionListener(e -> {
            leftFilterPane.setVisible(!leftFilterPane.isVisible());
            SwingUtilities.updateComponentTreeUI(frame);

        });

        Vector<JButton> buttons = new Vector<>(3);
        buttons.add(exit);
        buttons.add(search);
        buttons.add(insertCard);
        buttons.add(filter);
        return buttons;
    }

    public class ImageLoader extends SwingWorker<Vector<ImageIcon>, ImageIcon> {
        @Override
        protected Vector<ImageIcon> doInBackground() throws Exception {
            Vector<File> files = logicHandler.loadImageFiles();
            Vector<ImageIcon> images = new Vector<>();
            for(File file : files) {
                ImageIcon image = logicHandler.loadAndConvertImage(file.getPath());
                images.add(image);
                publish(image);
            }
            System.out.println("Number of images: " + images.size());
            return images;
        }

        @Override
        protected void process(List<ImageIcon> images) {
            ImageIcon imageIcon = images.get(images.size() -1);
            JLabel labelImage = new JLabel(imageIcon);
            labelImage.setSize(imageWidth,imageHeight);
            labels.add(labelImage);
            rightImagePane.add(labelImage);
            SwingUtilities.updateComponentTreeUI(frame);
        }

        @Override
        protected void done(){
            try {
                imageIcons = get();
            } catch (InterruptedException ignore) {}
            catch (java.util.concurrent.ExecutionException e) {
                String why = null;
                Throwable cause = e.getCause();
                if (cause != null) {
                    why = cause.getMessage();
                } else {
                    why = e.getMessage();
                }
                System.err.println("Error retrieving images: " + why);
            }
        }
    }

}
