package com.semesterproject.tourplanner.bl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

public class HTTPHelper {

    //get image from url
    public static java.awt.Image httpGetImage(String url) throws IOException {
        URL imgUrl = new URL(url);
        return ImageIO.read(imgUrl);
    }


    public static String httpGetJsonString(String url) throws IOException {
        HttpURLConnection connection = null;
        try {
            var realUrl = new URL(url);
            connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            StringBuilder stringBuilder;

            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                stringBuilder = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append(System.lineSeparator());
                }
            }
            return stringBuilder.toString();
        } finally {
            assert connection != null;
            connection.disconnect();
        }
    }
}
