package com.qarepo.rabbitmq.service;

import com.qarepo.rabbitmq.api.BannerRPCController;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class BannerRPCService {
    private static final Logger LOGGER = LogManager.getLogger(BannerRPCController.class);
    private static StringWriter sw = new StringWriter();

    public static StringBuffer executeBannerTestService(final String json, final String endpoint) {
        URL url;
        HttpURLConnection conn = null;
        StringBuffer response = null;

        try {
            url = new URL(endpoint);
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
            response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response;
        } catch (IOException e) {
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.log(Level.ERROR, "Exception: " + sw.toString());
        }
        return response;
    }

    public static void main(String[] args) {
        String json = "{\n" +
                "    \"jobNumber\": \"KAY2890928\",\n" +
                "    \"jobName\": \"Gifting Valentines Day Base Sale Personalize\",\n" +
                "    \"previewURL\": \"http://zpreview.ztrac.com/clients/196036cf135fba392e12e6b1c622915d\"\n" +
                "}";
        String endpoint = "http://localhost:8080/runBannerTests";
        String input = BannerRPCService.executeBannerTestService(json, endpoint)
                                       .toString();
        LOGGER.info(" [*] Got '{}' from banner test service.", input);
    }
}
