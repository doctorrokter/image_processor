package app.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by misha on 09-Jul-17.
 */
public class ImageDownloader {

    private final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36";
    private static final Logger logger = LoggerFactory.getLogger(ImageDownloader.class);


    public void download(String url, String destination, String filename) {
        try {
            logger.info("Try to download an image: " + url);

            if (!Files.exists(Paths.get("./" + destination))) {
                Files.createDirectories(Paths.get("./" + destination));
            }

            if (Files.exists(Paths.get("./" + destination + "/" + filename))) {
                return;
            }

            URL urlObj = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) urlObj.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", userAgent);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.connect();

            Files.copy(conn.getInputStream(), Paths.get("./" + destination + "/" + filename));
            conn.disconnect();
        } catch (Exception e) {
            logger.error("Error downloading an image", e);
        }
    }
}
