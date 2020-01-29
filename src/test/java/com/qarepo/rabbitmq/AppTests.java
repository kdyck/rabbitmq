package com.qarepo.rabbitmq;

import com.qarepo.rabbitmq.message.MessageSender;
import com.qarepo.rabbitmq.service.BannerRPCService;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootTest
class AppTests {
    private static final String PREVIEW_URL = "";
    private static final String ENDPOINT_URL = "";

    @Test
    void contextLoads() {
    }

    @Test
    void getQueue() throws IOException, TimeoutException {
        final String QUEUE_NAME = "banners_rpc_queue";
        String json = "{\n" +
                "    \"jobNumber\": \"KAY2890928\",\n" +
                "    \"jobName\": \"Gifting Valentines Day Base Sale Personalize\",\n" +
                "    \"previewURL\": \"" + PREVIEW_URL + "\"\n" +
                "}";
        MessageSender sender = new MessageSender();
       /* final AMQPConfig config = new AMQPConfig();
        Connection conn = config.connect();
        Channel channel = config.getChannel(conn);
        for (int i = 0; i <= 4; i++) {
            sender.send(channel, QUEUE_NAME, json);
        }
        MessageReceiver receiver = new MessageReceiver();
        receiver.receive(QUEUE_NAME);*/
        // AMQPConfig.close(channel);
		// Assert.assertThat();
    }

    @Test
    public void bannerRPCServiceTest() {
        String json = "{\n" +
                "    \"jobNumber\": \"KAY2890928\",\n" +
                "    \"jobName\": \"Gifting Valentines Day Base Sale Personalize\",\n" +
                "    \"previewURL\": \"" + PREVIEW_URL + "\"\n" +
                "}";
        BannerRPCService service = new BannerRPCService();
        String input = service.executeBannerTestService(json, ENDPOINT_URL)
                                       .toString();
        Assert.assertNotNull(input);
    }
}
