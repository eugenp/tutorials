package com.habuma.spitter.alerts;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.habuma.spitter.domain.Spittle;

public class AlertServiceImpl implements AlertService {
  public void sendSpittleAlert(final Spittle spittle) {
    jmsTemplate.send(
      "spittle.alert.queue",
      new MessageCreator() {      
        public Message createMessage(Session session)
                throws JMSException {
          return session.createObjectMessage(spittle);
        }
      }
    );
  }
  
  @Autowired
  JmsTemplate jmsTemplate;
}
