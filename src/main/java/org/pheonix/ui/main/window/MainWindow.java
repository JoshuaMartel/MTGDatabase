package org.pheonix.ui.main.window;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.pheonix.ConfigStore;
import org.pheonix.business.BusinessLogic;
import org.pheonix.ui.InsertCardWindow;
import org.pheonix.ui.SearchWindow;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;
import java.util.*;
import java.util.List;

public class MainWindow {
    int imageWidth, imageHeight;
    int currentImages, maxImages;
    BusinessLogic logicHandler;
    Properties config;
    JFrame frame;
    GroupLayout bottomLayout;
    JTabbedPane tabbedPane;
    JSplitPane outerSplitPane, innerTopSplitPane;
    JScrollPane imageScrollPane;
    ImagePanel rightImagePane;
    JPanel bottomPane, leftFilterPane;
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
        Dimension frameSize = new Dimension(1000,800);
        int filterWidth = (int)(frameSize.width * 0.1);

        leftFilterPane.setVisible(true);
        leftFilterPane.setPreferredSize(new Dimension(filterWidth, frameSize.height));
        innerTopSplitPane.setResizeWeight(0.3);
        innerTopSplitPane.setLeftComponent(leftFilterPane);
        imageScrollPane = new JScrollPane(rightImagePane);
        imageScrollPane.getVerticalScrollBar().setUnitIncrement(20);
        imageScrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        imageScrollPane.getVerticalScrollBar().addAdjustmentListener(new ImageScrollbarListener());
        innerTopSplitPane.setRightComponent(imageScrollPane);

        outerSplitPane.setResizeWeight(1.0);
        outerSplitPane.setBottomComponent(bottomPane);
        outerSplitPane.setTopComponent(innerTopSplitPane);

        ImageLoader imageLoader = new ImageLoader();
        imageLoader.execute();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(outerSplitPane, BorderLayout.CENTER);
        frame.pack();
        frame.setSize(frameSize);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void init(BusinessLogic businessLogic){
        logicHandler = businessLogic;
        config = businessLogic.getConfig();
        imageWidth = Integer.parseInt(config.getProperty(ConfigStore.IMAGE_WIDTH.getValue()));
        imageHeight = Integer.parseInt(config.getProperty(ConfigStore.IMAGE_HEIGHT.getValue()));
        Dimension screenResolution = Toolkit.getDefaultToolkit().getScreenSize();

        final int IMAGES_PER_ROW = screenResolution.width / imageWidth;
        final int ROWS = 4;

        currentImages = 0;
        maxImages = IMAGES_PER_ROW * ROWS;
        System.out.println("maxImages: " + maxImages);

        frame = new JFrame();
        labels = new Vector<>();
        bottomPane = new JPanel();
        bottomPane.setMinimumSize(new Dimension(320,70));
        bottomPane.setPreferredSize(new Dimension(320,70));
        leftFilterPane = new FilterPanel(frame);
        rightImagePane = new ImagePanel(frame ,config,new GridLayout(0, IMAGES_PER_ROW, 10,10));
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

    public class ImageLoader extends SwingWorker<Vector<ImageIcon>, ImmutablePair<String, ImageIcon>> {
        @Override
        protected Vector<ImageIcon> doInBackground() throws Exception {
            Vector<File> files = logicHandler.loadImageFiles();
            Vector<ImageIcon> images = new Vector<>();
            Map<String, JLabel> imageMap = rightImagePane.getImageLabels();

            for(File file : files) {
                if(!imageMap.containsKey(file.getName())){
                    ImageIcon image = logicHandler.loadAndConvertImage(file.getPath());
                    images.add(image);
                    publish(new ImmutablePair<>(file.getName(), image));
                    currentImages++;
                }
                if(currentImages >= maxImages){
                    break;
                }
            }
            System.out.println("Number of images: " + images.size());
            return images;
        }

        @Override
        protected void process(List<ImmutablePair<String, ImageIcon>> images) {
            ImageIcon imageIcon = images.get(images.size() -1).getRight();
            String name = images.get(images.size() - 1).getLeft();
            rightImagePane.addLabel(name, imageIcon);
            /*JLabel labelImage = new JLabel(imageIcon);
            labelImage.setSize(imageWidth,imageHeight);
            //labelImage.setMaximumSize(new Dimension(imageWidth,imageHeight));
            labelImage.setBorder(new BevelBorder(BevelBorder.RAISED));
            labels.add(labelImage);
            rightImagePane.add(labelImage);*/

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

    private class ImageScrollbarListener implements AdjustmentListener {
        private static final float THRESHOLD = 0.9f;
        @Override
        public void adjustmentValueChanged(AdjustmentEvent e) {
            //System.out.println("AdjustmentEvent Heard.");
            if(e.getValueIsAdjusting()){
                return;
            }
            int type = e.getAdjustmentType();
            System.out.println(type);
            switch(type) {
                case AdjustmentEvent.BLOCK_DECREMENT:
                case AdjustmentEvent.TRACK:
                case AdjustmentEvent.UNIT_DECREMENT:
                    //System.out.println("Heard decrement.");
                    int max = imageScrollPane.getVerticalScrollBar().getMaximum() - imageScrollPane.getVerticalScrollBar().getVisibleAmount();
                    int current = imageScrollPane.getVerticalScrollBar().getValue();
                    float fraction = (float) current / (float) max;
                    // If scroll bar is at the bottom and images have finished loading
                    /*System.out.println("current, max, currentImages, maxImages: " +
                            current + ", " +
                            max + ", " +
                            currentImages + ", " +
                            maxImages);*/
                    if(fraction > THRESHOLD && currentImages >= maxImages){
                        //System.out.println("loading more images...");
                        maxImages *= 2;
                        ImageLoader loader = new ImageLoader();
                        loader.execute();
                    }
                    break;
            }
        }
    }

}
