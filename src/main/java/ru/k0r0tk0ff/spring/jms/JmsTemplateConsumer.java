package ru.k0r0tk0ff.spring.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import ru.k0r0tk0ff.spring.db.JmsRepository;

import javax.jms.JMSException;
import javax.jms.TextMessage;


@Component
public class JmsTemplateConsumer {

    private static Logger LOGGER = LoggerFactory.getLogger(JmsTemplateConsumer.class);

    @Autowired
    private JmsRepository jmsRepository;

    @JmsListener(destination = "empty")
    public void receiveMessage(TextMessage textMessage) {
        try {
            jmsRepository.writeMessageToDb(textMessage);
            StringBuilder builder = new StringBuilder();
            builder.append("Received: <body = ");
            builder.append(textMessage.getText());
            builder.append("; header = ");
            builder.append(textMessage.getJMSType());
            builder.append(";>");
            LOGGER.info(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Error in JmsTemplateConsumer class!!", e);
        }
    }
}

