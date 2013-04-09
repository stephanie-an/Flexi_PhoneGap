package au.com.silverquest.flexigroup.services;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 24/02/13
 * Time: 9:16 AM
 * To change this template use File | Settings | File Templates.
 */
public interface EmailService {

    boolean sendMail(List<String> recipients, String subject, String body);
}
