package au.com.silverquest.flexigroup.services;

import au.com.silverquest.flexigroup.model.entity.Quote;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

@Service("flexiEmailGenerator")
public class FlexiEmailGeneratorImpl implements FlexiEmailGenerator {

    // TODO Move this to a Config or something.
    private final static String email = "<html>\n" +
            "<head>\n" +
            "\n" +
            "<meta http-equiv=\"content-type\" content=\"text/html; charset=ISO-8859-1\">\n" +
            "</head>\n" +
            "<body text=\"#000000\" bgcolor=\"#FFFFFF\">\n" +
            "<p><font face=\"Calibri\">" +
            "<img src=\"${imgPath}\" v:shapes=\"Picture_x0020_1\" width=\"222\" height=\"76\">" +
            "</font></p>\n" +
            "<p><font face=\"Calibri\"><b>QUOTE COMPLETED</b></font></p>\n" +
            "<p><font face=\"Calibri\">Dear FlexiCommercial Team,</font></p>\n" +
            "<p><font face=\"Calibri\">This is a notification that a quote has been " +
            "completed via the FlexiCommercial Mobile Application.<br>\n" +
            "</font></p>\n" +
            "<p><font face=\"Calibri\"><u><b>Quote Details<br>\n" +
            "</b></u></font></p>\n" +
            "<br>\n" +
            "<table width=\"100%\" border=\"1\" cellpadding=\"2\" cellspacing=\"2\">\n" +
            "<tbody>\n" +
            "<tr>\n" +
            "<td valign=\"top\"><b>Customer:</b> ${quote.customer.name}<br>\n" +
            "<b>Email:</b> ${quote.customer.email}<br>\n" +
            "<b>Phone:</b> ${quote.customer.phone}<br>\n" +
            "<b>Comments:</b> ${quote.customer.comment}<br>\n" +
            "<b>Product:</b> ${quote.product.name}<br>\n" +
            "<b>Term:</b> ${quote.term}<br>\n" +
            "<b>Finance Amount:</b> $${quote.amount}<br>\n" +
            "<b>Equipment Type:</b> ${quote.equipment.name}<br>\n" +
            "<b>Tax Bracket:</b> ${quote.tax.description}<br>\n" +
            "<br>\n" +
            "<b>Monthly Payment: $${quote.monthlypayment}</b><br>\n" +
            "Monthly Payment: $${quote.monthlypaymentaftertax} (After Tax^)<br>\n" +
            "<b>WeeklyPayment: $${quote.weeklypayment}</b><br>\n" +
            "Weekly Payment: $${quote.weeklypaymentaftertax} (After Tax^)<br>\n" +
            "<br>\n" +
            "<b>Salesperson:</b> ${quote.salesStaff.firstname} ${quote.salesStaff.lastname}<br>\n" +
            "<b>Email:</b> ${quote.salesStaff.email}<br>\n" +
            "<b>Phone:</b> ${quote.salesStaff.phone}<br>\n" +
            "<b>Dealer:</b> ${quote.salesStaff.dealer.name}<br>\n" +
            "<br>\n" +
            "<i>Submitted to Flexirent on ${dateTime}</i><br>\n" +
            "</td>\n" +
            "</tr>\n" +
            "</tbody>\n" +
            "</table>\n" +
            "<p><font face=\"Calibri\"><br>\n" +
            "Sent by FlexiCommercial Mobile Application<u><b><br>\n" +
            "</b></u></font></p>\n" +
            "</body>\n" +
            "</html>";

    public String generateEmailBody(Quote quote, Date date, String imagePath) throws Exception {
        Velocity.init();
        VelocityContext context = new VelocityContext();
        context.put("quote", quote);
        context.put("dateTime", formatDate(quote.getSubmitdate()));
        context.put("imgPath", imagePath);

        StringWriter writer = new StringWriter();
        Velocity.evaluate(context, writer, "TemplateName", email);

        return writer.toString();
    }

    private static String formatDate(Date date) {
        String pattern = "dd/MM/yyyy HH:mm";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static void sendEmail() {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.host", "email-smtp.us-east-1.amazonaws.com");
        props.setProperty("mail.user", "your_ses_user");
        props.setProperty("mail.password", "your_ses_pwd");

        //props.setProperty("mail.smtp.starttls.enable", "true");

        Session mailSession = Session.getDefaultInstance(props, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                String username = "your_ses_user";
                String password = "your_ses_pwd";
                return new PasswordAuthentication(username, password);
            }
        });
    }
}