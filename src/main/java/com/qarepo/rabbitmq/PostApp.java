package com.qarepo.rabbitmq;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostApp {

    public static void main(String[] args) {
        URL url;
        HttpURLConnection conn = null;
        String json = "{\n" +
                "    \"jobNumber\": \"KAY2890928\",\n" +
                "    \"jobName\": \"Gifting Valentines Day Base Sale Personalize\",\n" +
                "    \"previewURL\": \"http://zpreview.ztrac.com/clients/196036cf135fba392e12e6b1c622915d\"\n" +
                "}";
        try {
            url = new URL("http://localhost:8080/runBannerTests");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Length", Integer.toString(json.getBytes().length));
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            DataOutputStream fos = new DataOutputStream(conn.getOutputStream());
            fos.writeBytes(json);
            fos.flush();
            fos.close();

            InputStream is = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine())!= null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            System.out.println(response.toString());
        } catch (IOException e) {

        }
    }
}
