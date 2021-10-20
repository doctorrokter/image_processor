package app.store;

import app.beans.Image;
import app.controllers.BlurController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StoreStub implements Store {

    private static final Logger logger = LoggerFactory.getLogger(BlurController.class);

    public StoreStub() {
        logger.debug("Initialize stub for storing images");
    }

    @Override
    public Image get(String path) {
        logger.debug("get image::" + path);
        return null;
    }

    @Override
    public void set(Image image) {
        logger.debug("store image::" + image.getName());
    }
}
