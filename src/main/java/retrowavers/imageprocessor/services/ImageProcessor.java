package retrowavers.imageprocessor.services;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.javalite.common.Util;

/**
 * Created by misha on 09-Jul-17.
 */
public class ImageProcessor {

    private ConvertCmd convert;

    public ImageProcessor() {
        if (System.getProperty("os.name").toLowerCase().contains("windows") && !Util.blank(System.getenv("MAGICK_HOME"))) {
            ProcessStarter.setGlobalSearchPath(System.getenv("MAGICK_HOME"));
        } else {
            throw new RuntimeException("MAGICK_HOME var should be specified!");
        }
        convert = new ConvertCmd();
    }

    public void blur(String src, String destination, double radius) {
        try {
            IMOperation operation = new IMOperation();
            operation.addImage(src);
            operation.blur(0d, radius);
            operation.addImage(destination);
            convert.run(operation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
