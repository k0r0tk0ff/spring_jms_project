package ru.k0r0tk0ff.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.k0r0tk0ff.spring.db.JmsRepository;
import ru.k0r0tk0ff.spring.jms.JmsTemplateProducer;

import java.util.concurrent.TimeUnit;

@SpringBootApplication(scanBasePackages = "ru.k0r0tk0ff.spring")
public class Application implements ApplicationRunner {

    private static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    @Autowired
    private JmsRepository jmsRepository;

    @Autowired
    JmsTemplateProducer jmsTemplateProducer;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        final String customJmsType = "custom JMS type";
        for (int i = 0; i < 5; i++){
            TimeUnit.SECONDS.sleep(1);
            jmsTemplateProducer.sendMessage(i, customJmsType);
        }
        LOGGER.info("Waiting for all ActiveMQ JMS Messages to be consumed");
        TimeUnit.SECONDS.sleep(3);
        jmsRepository.showDataInDb();
        System.exit(0);
    }
}
