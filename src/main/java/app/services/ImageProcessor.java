package app.services;

import app.beans.Image;
import app.store.Store;
import com.google.inject.Inject;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.javalite.common.Util;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by misha on 09-Jul-17.
 */
public class ImageProcessor {

    @Inject private Store store;
    private ConvertCmd convert;

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

    public Image blur(String path, String filename, String newFilename, double radius) {
        try {
            IMOperation operation = new IMOperation();
            operation.addImage("./" + path + "/" + filename);
            operation.blur(0d, radius);
            operation.addImage("./" + path + "/" + newFilename);
            convert.run(operation);
            Image image = new Image(path, newFilename, Files.readAllBytes(Paths.get("./" + path + "/" + newFilename)));
            store.set(image);
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Image resize(String path, String filename, String newFilename, int width, int height) {
        try {
            IMOperation operation = new IMOperation();
            operation.addImage("./" + path + "/" + filename);
            operation.resize(width, height);
            operation.addImage("./" + path + "/" + newFilename);
            convert.run(operation);
            Image image = new Image(path, newFilename, Files.readAllBytes(Paths.get("./" + path + "/" + newFilename)));
            store.set(image);
            return image;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
