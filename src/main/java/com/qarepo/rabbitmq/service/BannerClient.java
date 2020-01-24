package com.qarepo.rabbitmq.service;

import com.qarepo.rabbitmq.model.BannerHTML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class BannerClient {
    private static final Logger LOGGER = LogManager.getLogger(BannerClient.class);

    @Autowired
    ConfigurableApplicationContext ctx;

    @Value("${banners.client.sleep:0}")
    private int sleep;

    public StringBuilder getBannerTestsResponse(BannerHTML banners, String domain, String endPoint) {
        LOGGER.info("Making REST call to the Banner Tests API.");
        StringBuilder response = null;
        String json = banners.toJson();
        URL url;
        HttpURLConnection conn = null;
        try {
            // e.g. domain = http://localhost:8080/ ; endPoint = runBannerTests
            url = new URL(domain + endPoint);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Length", Integer.toString(json.getBytes().length));
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            if (conn.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                LOGGER.info("Delaying ... for " + sleep + "ms");
                Thread.sleep(sleep);
                ctx.close();
                LOGGER.info("Throwing exception so that message will be re-queued.");
                // Note: Typically Application specific exception should be thrown below
                throw new RuntimeException();

                // TODO: RETRY LOGIC
            }
            DataOutputStream fos = new DataOutputStream(conn.getOutputStream());
            fos.writeBytes(json);
            fos.flush();
            fos.close();

            InputStream is = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            response = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            is.close();
        } catch (IOException | InterruptedException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            LOGGER.error("Internal server error occurred in API call. Bypassing message requeue {}", sw.toString());

        } finally {
            if (conn != null)
                conn.disconnect();
            LOGGER.info(" <<< Exiting getBannerTestsResponse() after API call.");
        }
        return response;
    }
}
