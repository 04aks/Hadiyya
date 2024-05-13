package aks;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        String link = "https://api.jikan.moe/v4/anime?q=hyouka";
        URI uri = URI.create(link);
        try {
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";

                while((line = br.readLine()) != null){
                    sb.append(line);
                }

                System.out.println(sb.toString());
            }else{
                System.out.println("fk");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}