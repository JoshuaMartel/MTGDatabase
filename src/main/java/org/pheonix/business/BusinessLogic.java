package org.pheonix.business;


import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.pheonix.ConfigStore;
import org.pheonix.database.QueryHandler;
import org.pheonix.models.Card;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.sql.PreparedStatement;
import java.util.*;

import static org.opencv.imgproc.Imgproc.INTER_AREA;
import static org.pheonix.ConfigStore.IMAGE_FOLDER_URL;

public class BusinessLogic {
    QueryHandler queryHandler;
    Properties config;
    int image_width, image_height;

    public BusinessLogic(QueryHandler qh, Properties con) {
        queryHandler = qh;
        config = con;
        image_width = Integer.parseInt(config.getProperty(ConfigStore.IMAGE_WIDTH.getValue()));
        image_height = Integer.parseInt(config.getProperty(ConfigStore.IMAGE_HEIGHT.getValue()));

    }

    public Vector<String> getTags() {
        return  queryHandler.getTags();
    }

    public void insertCard(Card newCard){
        queryHandler.insertCard(newCard);
    }

    public Vector<Card> loadCards(){
        throw new UnsupportedOperationException("");
    }

    public Vector<Card> retrieveSearchResults(PreparedStatement statement){
        throw new UnsupportedOperationException("");
    }

    public Vector<File> loadImageFiles() {
        final File imagesFolder = new File(config.getProperty(IMAGE_FOLDER_URL.getValue()));
        final Vector<File> files = new Vector<>();
        Collections.addAll(files, imagesFolder.listFiles());
        return files;
    }

    public ImageIcon loadAndConvertImage(String path) {
        Mat matrix = Imgcodecs.imread(path);
        Mat resizeImage = new Mat();
        Imgproc.resize(matrix, resizeImage, new Size(image_width, image_height), 0.0 ,0.0, INTER_AREA );
        return new ImageIcon(Objects.requireNonNull(toBufferedImage(resizeImage)));
    }

    public Vector<ImageIcon> loadImages() {
        List<String> imageTypes = List.of(new String[]{"jpg", "jpeg", "gif", "png", "psd", "ai", "eps", "pdf"});

        final File imagesFolder = new File(config.getProperty(IMAGE_FOLDER_URL.getValue()));
        final Vector<File> files = new Vector<>();
        Collections.addAll(files, imagesFolder.listFiles());

        Vector<ImageIcon> images = new Vector<>();

        for (File file: files) {
            String fileName = file.getName();

            if(fileName.contains(".")){ //This should be always the case, but just incase
                String [] split = file.getName().split("\\.");
                String fileType = split[split.length - 1];
                //System.out.println("File type: " + fileType);
                if(imageTypes.contains(fileType.toLowerCase())){

                    Mat matrix = Imgcodecs.imread(file.getPath());
                    Mat resizeImage = new Mat();
                    Imgproc.resize(matrix, resizeImage, new Size(image_width, image_height), 0.0 ,0.0, INTER_AREA );
                    images.add(new ImageIcon(toBufferedImage(resizeImage)));

                }
            }
        }

        return images;
    }

    public Properties getConfig(){
        return config;
    }
    public void setConfig(Properties config) {
        this.config = config;
    }

    // This is not my code below
    private BufferedImage toBufferedImage(Mat matrix) {
        if(!matrix.empty()) {
            int type = BufferedImage.TYPE_BYTE_GRAY;
            if(matrix.channels() > 1) {
                type = BufferedImage.TYPE_3BYTE_BGR;
            }
            int bufferSize = matrix.channels() * matrix.cols() * matrix.rows();
            byte [] b = new byte[bufferSize];
            matrix.get(0,0,b); // get all pixels
            BufferedImage image = new BufferedImage(matrix.cols(), matrix.rows(), type);
            final byte [] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
            System.arraycopy(b,0,targetPixels,0,b.length);
            return image;
        }
        return null;
    }
}
