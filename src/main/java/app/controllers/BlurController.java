package app.controllers;

import app.beans.Image;
import app.services.ImageDownloader;
import app.services.ImageProcessor;
import app.store.Store;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.util.Arrays.asList;
import static spark.Spark.before;
import static spark.Spark.get;
import static org.javalite.common.Convert.toDouble;

/**
 * Created by misha on 10-Jul-17.
 */
public class BlurController {

    @Inject private ImageDownloader downloader;
    @Inject private ImageProcessor processor;
    @Inject private Store store;

    private static Logger logger = LoggerFactory.getLogger(BlurController.class);

    public void init() {
        before("/blur/*/*", (req, res) -> {
            logger.info(req.url());
            logger.info(asList(req.splat()).toString());
        });

        get("/blur/*/*", (req, res) -> {

            String url = req.splat()[1];
            if (!url.startsWith("https") && !url.startsWith("http")) {
                url = "http://" + url;
            }

            String[] parts = url.split("/");
            String filename = parts[parts.length - 1];
            String path = url.split(":/")[1].replace("/" + filename, "");

            String processDestination = path + "/b_" + filename;

            Image image = store.get(processDestination);

            if (image == null) {
                downloader.download(url, path, filename);
                image = processor.blur(path, filename, "b_" + filename, toDouble(req.splat()[0]));
                Files.delete(Paths.get("." + processDestination));
                Files.delete(Paths.get("." + path + "/" + filename));
            }

            HttpServletResponse raw = res.raw();
            raw.getOutputStream().write(image.getBytes());
            raw.getOutputStream().flush();
            raw.getOutputStream().close();
            return req.raw();
        });
    }
}
