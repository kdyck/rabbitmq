package com.qarepo.rabbitmq.springboot;

/*
public class BannerMessagePublisher {
    private static final Logger LOGGER = LogManager.getLogger(BannerMessagePublisher.class);

    @Autowired
    private Queue queue;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        LOGGER.info("Sending Banners...");
        BannerHTMLMessage banners = new BannerHTMLMessage();
        this.rabbitTemplate.setMessageConverter(new RabbitConfig().producerJackson2MessageConverter());
        this.rabbitTemplate.convertAndSend(RabbitConfig.BANNERS_EXCHANGE, RabbitConfig.BANNERS_QUEUE, banners);
        LOGGER.info("[x] Sent [ {} ]", banners);
    }
}
*/
