package nowicki.piotr.spring_boot_docker.service;

import nowicki.piotr.spring_boot_docker.model.EmailMessage;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RabbitListener(queues = "email-queue")
public class EmailConsumer {

    @Autowired
    private JavaMailSender javaMailSender;

    @RabbitHandler
    public void handleEmailMessage(EmailMessage emailMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nowikpl-97@o2.pl");
        message.setTo(emailMessage.getRecipient());
        message.setSubject(emailMessage.getSubject());
        message.setText(emailMessage.getBody());
        javaMailSender.send(message);
    }
}