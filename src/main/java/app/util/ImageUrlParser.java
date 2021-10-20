package app.util;

public class ImageUrlParser {

    public static class ParsedRemoteImageUrl {
        private String filename;
        private String downloadDestination;
        private String processDestination;

        public String getProcessDestination() {
            return processDestination;
        }

        public void setProcessDestination(String processDestination) {
            this.processDestination = processDestination;
        }

        public String getDownloadDestination() {
            return downloadDestination;
        }

        public void setDownloadDestination(String downloadDestination) {
            this.downloadDestination = downloadDestination;
        }

        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        @Override
        public String toString() {
            return "{ filename:" + filename + ", download_destination:" + downloadDestination + ", process_destination:" + processDestination + " }";
        }
    }

    public static ParsedRemoteImageUrl parse(String url, String action) {
        ParsedRemoteImageUrl result = new ParsedRemoteImageUrl();

        String[] parts = url.split("/");
        String filename = parts[parts.length - 1].split("\\?")[0];
        String downloadDestination = "download";

        result.setFilename(filename);
        result.setDownloadDestination(downloadDestination);
        result.setProcessDestination(action);

        return result;
    }

    public static String normalizeUrl(String url) {
        String newUrl = url;

        if (!newUrl.startsWith("https") || !newUrl.startsWith("http")) {
            newUrl = "http://" + newUrl;
        } else {
            String[] parts = newUrl.split(":/");
            String protocol = parts[0];
            String path = parts[1];
            if (path.startsWith("/")) {
                path += path.replace("/", "");
            }

            newUrl = protocol + "://" + path;
        }

        return newUrl;
    }
}
