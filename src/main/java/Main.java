import org.im4java.process.ProcessStarter;
import retrowavers.imageprocessor.services.Constants;
import retrowavers.imageprocessor.services.ImageDownloader;
import retrowavers.imageprocessor.services.ImageProcessor;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.javalite.common.Convert.toDouble;
import static spark.Spark.get;
import static spark.Spark.port;

/**
 * Created by misha on 08-Jul-17.
 */
public class Main {

    public static void main(String[] args) {
        port(Integer.valueOf(System.getenv("PORT")));

        ImageDownloader downloader = new ImageDownloader();
        ImageProcessor processor = new ImageProcessor();

        get("/", (req, res) -> "Retrowavers image processor tool");

        get("/blur/*/*", (req, res) -> {

            String url = req.splat()[1];
            if (!url.startsWith("https") && !url.startsWith("http")) {
                url = "http://" + url;
            }

            String[] parts = url.split("/");
            String filename = parts[parts.length - 1];
            Path downLoadDestination = Paths.get(Constants.IMAGES + "/" + filename);
            Path processDestination = Paths.get(Constants.IMAGES + "/b_" + filename);

            byte[] bytes;

            if (!Files.exists(processDestination, LinkOption.NOFOLLOW_LINKS)) {
                downloader.download(url, Constants.IMAGES, filename);
                processor.blur(downLoadDestination.toString(), processDestination.toString(), toDouble(req.splat()[0]));
            }

            bytes = Files.readAllBytes(processDestination);
            HttpServletResponse raw = res.raw();

            raw.getOutputStream().write(bytes);
            raw.getOutputStream().flush();
            raw.getOutputStream().close();

            return req.raw();
        });

    }
}
