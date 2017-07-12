package app.beans;

/**
 * Created by misha on 10-Jul-17.
 */
public class Image {

    private String dir;
    private String name;
    private byte[] bytes;

    public Image(String dir, String name) {
        this.dir = dir;
        this.name = name;
        this.bytes = new byte[]{};
    }

    public Image(String dir, String name, byte[] bytes) {
        this.dir = dir;
        this.name = name;
        this.bytes = bytes;
    }

    public String getDir() {
        return dir;
    }

    public Image setDir(String dir) {
        this.dir = dir;
        return this;
    }

    public String getName() {
        return name;
    }

    public Image setName(String name) {
        this.name = name;
        return this;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public Image setBytes(byte[] bytes) {
        this.bytes = bytes;
        return this;
    }
}
