package app.store;

import app.beans.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StoreStub implements Store {

    private static final Logger logger = LoggerFactory.getLogger(StoreStub.class);

    public StoreStub() {
        logger.info("Initialize stub for storing images");
    }

    @Override
    public Image get(String path) {
        logger.info("get image::" + path);
        return null;
    }

    @Override
    public void set(Image image) {
        logger.info("store image::" + image.getName());
    }
}
