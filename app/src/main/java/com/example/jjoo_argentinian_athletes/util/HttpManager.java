package com.example.jjoo_argentinian_athletes.util;

import android.net.Uri;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum HttpManagerValidMethod {
    GET
}

public class HttpManager {

    /**
     * Return a Byte Array
     * @param urlString
     * @param httpMethod
     * @return
     */
    protected byte[] request(String urlString, HttpManagerValidMethod httpMethod, Uri.Builder postParam){

        try {
            // Establish HTTP connection
            URL url = new URL(urlString);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod(httpMethod.toString());

            switch (huc.getRequestMethod()) {
                case "GET":
                    huc.connect();
                    break;
                case "POST":
                    huc.setDoOutput(true);
                    String query = postParam.build().getEncodedQuery();
                    OutputStream os = huc.getOutputStream();
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    bw.write(query);
                    bw.flush();
                    bw.close();
            }

            if (huc.getResponseCode() != 200){
                throw new IOException("The HTTP connection returned a value different to HTTP 200");
            }

            // Read server data response
            InputStream is = huc.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int amountByteRead = 0;
            while ((amountByteRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, amountByteRead);
            }
            is.close();

            return baos.toByteArray();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}