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
import static org.javalite.common.Convert.toInteger;
import static spark.Spark.before;
import static spark.Spark.get;

public class ResizeController {

    @Inject
    private ImageDownloader downloader;
    @Inject private ImageProcessor processor;
    @Inject private Store store;

    private static Logger logger = LoggerFactory.getLogger(ResizeController.class);

    public ResizeController() {
        before("/resize/*/*/*", (req, res) -> {
            logger.info(req.url());
            logger.info(asList(req.splat()).toString());
        });

        get("/resize/*/*/*", (req, res) -> {

            String url = req.splat()[2];
            if (!url.startsWith("https") || !url.startsWith("http")) {
                url = "http://" + url;
            }

            String[] parts = url.split("/");
            String filename = parts[parts.length - 1];
            String path = url.split(":/")[1].replace("/" + filename, "");

            int width = toInteger(req.splat()[0]);
            int height = toInteger(req.splat()[1]);
            String newFilename = "resize_" + width + "_" + height + "_" + filename;
            String processDestination = path + "/" + newFilename;

            Image image = store.get(processDestination);

            if (image == null) {
                logger.info("Image not found, will create a new one: " + processDestination);
                downloader.download(url, path, filename);
                image = processor.resize(path, filename, newFilename, width, height);
                Files.delete(Paths.get("." + processDestination));
                Files.delete(Paths.get("." + path + "/" + filename));
                store.set(image);
            } else {
                logger.info("Image found in store: " + processDestination);
            }

            HttpServletResponse raw = res.raw();
            raw.getOutputStream().write(image.getBytes());
            raw.getOutputStream().flush();
            raw.getOutputStream().close();
            return req.raw();
        });
    }
}
