package app.services;

import app.store.Dropbox;
import app.store.Store;
import app.store.StoreStub;
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

        String persistImages = System.getenv("PERSIST_IMAGES");
        if (persistImages != null && Boolean.getBoolean(persistImages)) {
            String storeProvider = System.getenv("STORAGE_PROVIDER");
            if (storeProvider != null && storeProvider.equals("DROPBOX")) {
                bind(Store.class).toProvider(() -> new Dropbox(System.getenv("DROPBOX_ACCESS_TOKEN"))).asEagerSingleton();
            } else {
                bind(Store.class).toProvider(StoreStub::new).asEagerSingleton();
            }
        } else {
            bind(Store.class).toProvider(StoreStub::new).asEagerSingleton();
        }

    }
}
