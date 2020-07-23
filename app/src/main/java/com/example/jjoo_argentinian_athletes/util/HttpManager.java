package com.example.jjoo_argentinian_athletes.util;

import android.content.Context;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpManager {

    protected byte[] request(HttpThread httpThread) {

        try {
            // Establish HTTP connection
            URL url = new URL(httpThread.url);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod(httpThread.httpMethod.toString());

            if (huc.getRequestMethod() == "GET") {
                huc.connect();
            }

            if (huc.getResponseCode() != 200) {
                throw new IOException("The HTTP connection returned a value different to HTTP 200");
            }

            // Preparing to read the response
            InputStream inputStream = huc.getInputStream();
            int byteRead = 0;
            byte[] buffer = new byte[1024];

            // If the response need to be saved on a file, the return will be the file location
            // but in binary mode
            if (httpThread.cacheOnDisk) {

                String fileName = httpThread.url.substring(httpThread.url.lastIndexOf("/") + 1);
                String filePath = httpThread.context.getFilesDir().getAbsolutePath() + "/" + fileName;

                FileOutputStream fos = httpThread.context.openFileOutput(fileName, Context.MODE_PRIVATE);

                while ((byteRead = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, byteRead);
                }

                fos.flush();
                fos.close();
                inputStream.close();
                huc.disconnect();

                return filePath.getBytes();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((byteRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, byteRead);
            }
            baos.close();
            inputStream.close();
            huc.disconnect();

            return baos.toByteArray();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}