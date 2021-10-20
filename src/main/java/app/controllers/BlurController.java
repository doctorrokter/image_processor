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
import static spark.Spark.before;
import static spark.Spark.get;
import static org.javalite.common.Convert.toDouble;

/**
 * Created by misha on 10-Jul-17.
 */
public class BlurController {

    @Inject private ImageDownloader downloader;
    @Inject private ImageProcessor processor;

    private static final Logger logger = LoggerFactory.getLogger(BlurController.class);

    public BlurController() {
        before("/blur/*/*", (req, res) -> {
            logger.info(req.url());
            logger.info(asList(req.splat(), req.queryString()).toString());
        });

        get("/blur/*/*", (req, res) -> {

            String url = req.splat()[1];
            if (!url.startsWith("https") || !url.startsWith("http")) {
                url = "http://" + url;
            }

            if (req.queryString() != null) {
                url += "?" + req.queryString();
            }


            logger.info("Image url::" + url);

            Double radius = toDouble(req.splat()[0]);
            String action = "blur";
            ImageUrlParser.ParsedRemoteImageUrl result = ImageUrlParser.parse(url, action);

            logger.info(result.toString());

            Image image = null;

            downloader.download(url, result.getDownloadDestination(), result.getFilename());
            image = processor.blur(result.getDownloadDestination(), result.getFilename(), radius);
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
