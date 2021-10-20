package app.controllers;

import app.beans.Image;
import app.services.ImageDownloader;
import app.services.ImageProcessor;
import app.util.ImageUrlParser;
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

            logger.debug("Image url::" + url);

            int width = toInteger(req.splat()[0]);
            int height = toInteger(req.splat()[1]);

            String action = "resize";
            ImageUrlParser.ParsedRemoteImageUrl result = ImageUrlParser.parse(url, action);

            logger.debug(result.toString());

            Image image = null;

            downloader.download(url, result.getDownloadDestination(), result.getFilename());
            image = processor.resize(result.getDownloadDestination(), result.getFilename(), width, height);
            Files.delete(Paths.get("." + result.getProcessDestination()));
            Files.delete(Paths.get("." + result.getDownloadDestination()));

            HttpServletResponse raw = res.raw();
            raw.getOutputStream().write(image.getBytes());
            raw.getOutputStream().flush();
            raw.getOutputStream().close();
            return req.raw();
        });
    }
}
