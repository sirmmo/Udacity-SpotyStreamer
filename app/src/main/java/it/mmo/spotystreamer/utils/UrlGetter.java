package it.mmo.spotystreamer.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by asus on 31/05/2015.
 */
public class UrlGetter {

    private final String url;
    private  String text;
    public UrlGetter(String url) {
        this.url = url;
    }


    public UrlGetter get() throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);

        in.close();

        this.text = response.toString();
        return this;
    }

    public String getText(){
        return this.text;
    }
}
