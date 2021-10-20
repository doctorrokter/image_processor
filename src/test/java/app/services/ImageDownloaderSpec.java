package app.services;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.javalite.test.jspec.JSpec.a;

/**
 * Created by misha on 09-Jul-17.
 */
public class ImageDownloaderSpec {

    private String filename = "c99d5d4a4951c230ded9d9ffec2c757f85593814.jpg";
    private Path path;

    @BeforeAll
    public void setUp() throws IOException {
        path = Paths.get(Constants.IMAGES + "/" + filename);
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }

    @Test
    public void should_download_image() {
        a(Files.exists(path)).shouldBeFalse();

        ImageDownloader id = new ImageDownloader();
        id.download("http://retrowave.ru/artwork/c99d5d4a4951c230ded9d9ffec2c757f85593814.jpg", Constants.IMAGES, "c99d5d4a4951c230ded9d9ffec2c757f85593814.jpg");

        a(Files.exists(path)).shouldBeTrue();

        id.download("https://retrowave.ru/artwork/c99d5d4a4951c230ded9d9ffec2c757f85593814.jpg", Constants.IMAGES, "c99d5d4a4951c230ded9d9ffec2c757f85593814.jpg");
        a(Files.exists(path)).shouldBeTrue();
    }
}
