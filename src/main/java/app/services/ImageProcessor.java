package app.services;

import app.beans.Image;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.javalite.common.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by misha on 09-Jul-17.
 */
public class ImageProcessor {

    private ConvertCmd convert;
    private static final Logger logger = LoggerFactory.getLogger(ImageProcessor.class);


    public ImageProcessor() {
        if (System.getProperty("os.name").toLowerCase().contains("windows")) {
            if (!Util.blank(System.getenv("MAGICK_HOME"))) {
                ProcessStarter.setGlobalSearchPath(System.getenv("MAGICK_HOME"));
            } else {
                throw new RuntimeException("MAGICK_HOME var should be specified!");
            }
        }
        convert = new ConvertCmd();
    }

    public Image blur(String path, String filename, double radius) {
        String newFilename = "blur_" + filename;

        try {
            logger.info("Blur image: " + path);
            IMOperation operation = new IMOperation();
            operation.addImage("./" + path + "/" + filename);
            operation.blur(0d, radius);
            operation.addImage("./" + path + "/" + newFilename);
            convert.run(operation);
            return new Image(path, newFilename, Files.readAllBytes(Paths.get("./" + path + "/" + newFilename)));
        } catch (Exception e) {
            logger.error("Error blurring image", e);
        }

        return null;
    }

    public Image resize(String path, String filename, int width, int height) {
        String newFilename = "resize_" + width + "_" + height + "_" + filename;

        try {
            logger.info("Resize image: " + path);
            IMOperation operation = new IMOperation();
            operation.addImage("./" + path + "/" + filename);
            operation.resize(width, height);
            operation.addImage("./" + path + "/" + newFilename);
            convert.run(operation);
            return new Image(path, newFilename, Files.readAllBytes(Paths.get("./" + path + "/" + newFilename)));
        } catch (Exception e) {
            logger.error("Error blurring image", e);
        }

        return null;
    }
}
