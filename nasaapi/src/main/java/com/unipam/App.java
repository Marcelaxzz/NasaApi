package com.unipam;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class App {
    public static void main(String[] args) {
        try {
           
            String apiUrl = "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY";
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            
            InputStream responseStream = connection.getInputStream();
            String json = new String(responseStream.readAllBytes());
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

         
            String imageUrl = jsonObject.get("url").getAsString();

            System.out.println("URL da imagem: " + imageUrl);

        
            downloadImage(imageUrl, "nasa_image.jpg");

            System.out.println("Imagem salva com sucesso.");

        } catch (IOException e) {
            System.err.println("Erro de I/O: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }

    private static void downloadImage(String imageUrl, String fileName) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection imageConnection = (HttpURLConnection) url.openConnection();
        imageConnection.setRequestMethod("GET");

        try (InputStream in = new BufferedInputStream(imageConnection.getInputStream());
             FileOutputStream out = new FileOutputStream(fileName)) {
            
            byte[] buffer = new byte[1024];
            int count;

            while ((count = in.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, count);
            }
        }
    }
}
