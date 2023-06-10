package com.semesterproject.tourplanner.bl;

import com.semesterproject.tourplanner.bl.Logging.LoggerFactory;
import com.semesterproject.tourplanner.bl.Logging.LoggerWrapper;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileHelper {
    private static final LoggerWrapper logger = LoggerFactory.getLogger(FileHelper.class);
    public static void openDefault(String f){
        //reference to a file
        File file = new File(f);
        try {
            Desktop desktop = Desktop.getDesktop();
            //open a file using the default program for the file type
            //launches a default program to open the file
            desktop.open(file);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public static void saveImage(Image image, String type, File file) throws IOException {
        ImageIO.write(ImageHelper.toBufferedImage(image), type, file);
    }

    public static void delFile(Path pathToBeDeleted){
        try {
            Files.delete(pathToBeDeleted);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
