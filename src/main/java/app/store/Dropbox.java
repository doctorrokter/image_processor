package app.store;

import app.beans.Image;
import org.javalite.http.Get;
import org.javalite.http.Http;
import org.javalite.http.Post;

import java.util.Map;

import static org.javalite.common.Collections.map;
import static org.javalite.common.JsonHelper.toJsonString;
import static org.javalite.common.JsonHelper.toMap;

/**
 * Created by misha on 10-Jul-17.
 */
public class Dropbox implements Store {

    private final String API_ENDPOINT = "https://api.dropboxapi.com/2";
    private final String API_CONTENT_ENDPOINT = "https://content.dropboxapi.com/2";
    private final String AUTH_HEADER = "Authorization";
    private final String CONTENT_TYPE_HEADER = "Content-Type";
    private final String DROPBOX_API_ARG = "Dropbox-API-Arg";

    private String accessToken;

    public Dropbox(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public Image get(String path) {
        String body = toJsonString(map("path", path));
        Get get = Http.get(API_CONTENT_ENDPOINT + "/files/download");
        get.header(AUTH_HEADER, authHeaderValue());
        get.header(DROPBOX_API_ARG, body);

        try {
            byte[] bytes = get.bytes();
            String[] parts = path.split("/");
            String filename = parts[parts.length - 1];
            String imgDir = path.replace("/" + filename, "");
            return new Image(imgDir, filename, bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void set(Image image) {
        createFolder(image.getDir());
        upload(image.getDir(), image.getName(), image.getBytes());
    }

    private Map createFolder(String path) {
        String body = toJsonString(map("path", path, "autorename", false));
        Post post = Http.post(API_ENDPOINT + "/files/create_folder_v2", body.getBytes());
        post.header(AUTH_HEADER, authHeaderValue());
        post.header(CONTENT_TYPE_HEADER, "application/json");
        return toMap(post.text());
    }

    private Map upload(String dir, String filename, byte[] content) {
        String body = toJsonString(map("path", dir + "/" + filename, "autorename", false, "mute", false, "mode", "add"));
        Post post = Http.post(API_CONTENT_ENDPOINT + "/files/upload", content);
        post.header(AUTH_HEADER, authHeaderValue());
        post.header(DROPBOX_API_ARG, body);
        post.header(CONTENT_TYPE_HEADER, "application/octet-stream");

        String text = post.text();
        return toMap(text);
    }

    private String authHeaderValue() {
        return "Bearer " + accessToken;
    }
}
