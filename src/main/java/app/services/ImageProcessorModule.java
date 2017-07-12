package app.services;

import app.store.Dropbox;
import app.store.Store;
import com.google.inject.AbstractModule;

import static org.javalite.app_config.AppConfig.p;

/**
 * Created by misha on 10-Jul-17.
 */
public class ImageProcessorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ImageProcessor.class).asEagerSingleton();
        bind(ImageDownloader.class).asEagerSingleton();
        bind(Store.class).toProvider(() -> new Dropbox(p("dropbox.access.token"))).asEagerSingleton();
    }
}
