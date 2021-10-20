package app.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.javalite.test.jspec.JSpec.a;

@DisplayName("Image Url Parser")
public class ImageUrlParserSpec {

    @Nested
    @DisplayName("Normalize image URL")
    class NormalizeImageUrl {

        String url = "retrowave.ru/artwork/c99d5d4a4951c230ded9d9ffec2c757f85593814.jpg";
        String url2 = "https:/retrowave.ru/artwork/c99d5d4a4951c230ded9d9ffec2c757f85593814.jpg";
        String url3 = "https://retrowave.ru/artwork/c99d5d4a4951c230ded9d9ffec2c757f85593814.jpg";

        @Test
        @DisplayName("should normalize if no protocol")
        public void should_normalize_url_if_no_protocol() {
            String newUrl = ImageUrlParser.normalizeUrl(url);
            a(newUrl).shouldBeEqual("http://" + url);
        }

        @Test
        @DisplayName("should normalize if broken protocol")
        public void should_normalize_if_broken_protocol() {
            String newUrl = ImageUrlParser.normalizeUrl(url2);
            a(newUrl).shouldBeEqual("https://" + url);
        }

        @Test
        @DisplayName("should return url as is")
        public void should_return_url_as_is() {
            String newUrl = ImageUrlParser.normalizeUrl(url3);
            a(newUrl).shouldBeEqual(url3);
        }
    }
}
