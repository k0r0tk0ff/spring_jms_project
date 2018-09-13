package ru.k0r0tk0ff.spring.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Component
public class JmsTemplateProducer {

@Autowired
JmsTemplate jmsTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsTemplateProducer.class);

    public void sendMessage(int i, String type) {
        final int j = i;
        jmsTemplate.send(
                session -> {
                    TextMessage message = session.createTextMessage();
                    message.setText("Text " + j);
                    message.setJMSType(type);
                    StringBuilder builder = new StringBuilder();
                    builder.append("Sent: <body = ");
                    builder.append(message.getText());
                    builder.append("; header = ");
                    builder.append(message.getJMSType());
                    builder.append(";>");
                    LOGGER.info(builder.toString());
                    return message;
                }
        );
    }
}
