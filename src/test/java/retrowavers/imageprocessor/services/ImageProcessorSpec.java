package retrowavers.imageprocessor.services;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.javalite.test.jspec.JSpec.a;

/**
 * Created by misha on 09-Jul-17.
 */
public class ImageProcessorSpec {

    private String filename = "to_process.jpg";
    private Path path;
    private Path src;
    private ImageProcessor processor;

    @Before
    public void setUp() throws IOException {
        src = Paths.get(Constants.IMAGES + "/" + filename);
        path = Paths.get(Constants.IMAGES + "/b_" + filename);
        if (Files.exists(path)) {
            Files.delete(path);
        }
        processor = new ImageProcessor();
    }

    @Test
    public void should_blur_image() {
        a(Files.exists(path)).shouldBeFalse();

        processor.blur(src.toString(), path.toString(), 65);
        a(Files.exists(path)).shouldBeTrue();
    }
}
