package app.store;

import app.beans.Image;

/**
 * Created by misha on 10-Jul-17.
 */
public interface Store {

    Image get(String path);

    void set(Image image);
}
