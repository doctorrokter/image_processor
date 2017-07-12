import app.controllers.BlurController;
import com.google.inject.Guice;
import com.google.inject.Injector;
import app.services.ImageProcessorModule;

import static spark.Spark.get;
import static spark.Spark.port;

/**
 * Created by misha on 08-Jul-17.
 */
public class Main {

    public static void main(String[] args) {
        port(Integer.valueOf(System.getenv("PORT")));

        Injector injector = Guice.createInjector(new ImageProcessorModule());

        get("/", (req, res) -> "Image Processor");

        BlurController blurController = new BlurController();
        injector.injectMembers(blurController);
        blurController.init();

    }
}
