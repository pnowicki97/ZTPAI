package nowicki.piotr.spring_boot_docker.service;

import nowicki.piotr.spring_boot_docker.model.EmailMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendEmailMessage(EmailMessage emailMessage) {
        rabbitTemplate.convertAndSend("email-exchange", "email-routing-key", emailMessage);
    }
}