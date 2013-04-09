package au.com.silverquest.flexigroup.services;

import au.com.silverquest.flexigroup.model.repository.ConfigurationRepository;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 23/02/13
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("emailService")
public class EmailServiceImpl implements EmailService {

    final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Inject
    private ConfigurationRepository configurationRepository;

    @Inject
    private String awsEmailAccessKey;

    @Inject
    private String awsEmailSecretKey;

    @Inject
    private String flexiEmailSender;

    public boolean sendMail(List<String> recipients, String subject, String bodyTxt) {
        boolean emailSent = false;

        Destination destination = new Destination(recipients);

        Content subjContent = new Content().withData(subject);
        Message msg = new Message().withSubject(subjContent);
        Content htmlContent = new Content().withData(bodyTxt);
        Body body = new Body().withHtml(htmlContent);
        msg.setBody(body);

        SendEmailRequest request = new SendEmailRequest(flexiEmailSender, destination, msg);

        AWSCredentials credentials = new BasicAWSCredentials(awsEmailAccessKey, awsEmailSecretKey);
        AmazonSimpleEmailServiceClient sesClient = new AmazonSimpleEmailServiceClient(credentials);

        try {
            SendEmailResult result = sesClient.sendEmail(request);
            log.info("Sent email: " + result);
            // TODO Check with API if any result is a success result or not.
            emailSent = true;
        } catch (AmazonServiceException se) {
            log.error("Amazon Service exception thrown, email not sent: " + se.getLocalizedMessage());
        } catch (AmazonClientException ce) {
            // Oh for JDK7
            log.error("Amazon Client exception thrown, email not sent: " + ce.getLocalizedMessage());
        }

        return emailSent;
    }
}
