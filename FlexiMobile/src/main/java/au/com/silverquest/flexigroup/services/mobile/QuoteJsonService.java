package au.com.silverquest.flexigroup.services.mobile;

/**
 * Created with IntelliJ IDEA.
 * User: coreyb
 * Date: 22/02/13
 * Time: 9:18 PM
 * To change this template use File | Settings | File Templates.
 */

import au.com.silverquest.flexigroup.model.entity.*;
import au.com.silverquest.flexigroup.model.repository.ConfigurationRepository;
import au.com.silverquest.flexigroup.model.repository.QuoteRepository;
import au.com.silverquest.flexigroup.services.EmailService;
import au.com.silverquest.flexigroup.services.FlexiEmailGenerator;
import au.com.silverquest.flexigroup.util.SubEntityFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.*;

@Controller
public class QuoteJsonService {

    public static final String FLEXI_EMAIL_BANNER_PATH = "/resources/images/FlexiEmailBanner.png";

    final Logger log = LoggerFactory.getLogger(QuoteJsonService.class);

    @Inject
    private QuoteRepository quoteRepository;

    @Inject
    private SubEntityFinder entityFinder;

    @Inject
    private ConfigurationRepository configurationRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private FlexiEmailGenerator emailGenerator;

    // TODO Delete these once it's working...
    @RequestMapping(value = "/quote", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Quote> getQuotes() {
        log.info("getQuotes() service call received");
        List<Quote> quotes = quoteRepository.getAll();
        log.debug("Returning results: " + quotes.size());
        return quotes;
    }

    @RequestMapping(value = "/quote/{uuid}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public Quote getQuoteByUuid(@PathVariable UUID uuid) {
        log.info("getQuoteByUuid() service call received for: " + uuid);
        Quote quote = quoteRepository.findByUuid(uuid);
        if (quote != null) {
            log.debug("Returning result Quote Id: " + quote.getId());
        } else {
            log.debug("No Quote found!");
        }
        return quote;
    }

    @Transactional
    @RequestMapping(value = "/quote", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public Quote addQuote(@RequestBody Quote quote) {
        log.info("addQuote() service call received");

        // Find or create all the nested elements
        quote.setCustomer(entityFinder.findCustomer(quote));
        quote.setEquipment(entityFinder.findEquipment(quote));
        quote.setProduct(entityFinder.findProduct(quote));
        quote.setSalesStaff(entityFinder.findSalesStaff(quote));
        quote.setStatuscode(entityFinder.findStatusCode(quote));
        quote.setTax(entityFinder.findTax(quote));

        Quote qResult;
        try {
            qResult = quoteRepository.save(quote);
        } catch (Exception e) {
            // Why are we trying to create something that already exists? Perhaps a PUT would be better
            log.error("Error trying to create non-unique quote: " + quote.getUuid());
            qResult = null;
        }
        log.debug("Saved quote id: " + qResult.getId());

        SendEmail(qResult);

        return qResult;
    }

    @Transactional
    @RequestMapping(value = "/quote", method = RequestMethod.PUT, headers = "Accept=application/json")
    @ResponseBody
    public Quote upsertQuote(@RequestBody Quote quote) {
        log.debug("upsertQuote() service call received, forwarding to addQuote");
        return addQuote(quote);
    }

    @Transactional
    @RequestMapping(value = "/quote/{uuid}", method = RequestMethod.PUT, headers = "Accept=application/json")
    @ResponseBody
    public Quote updateQuote(@RequestBody Quote quote, @PathVariable UUID uuid) {
        log.info("updateQuote() service call received for: " + uuid);
        Quote qResult = quoteRepository.findByUuid(uuid);

        // Find or Create any nested elements.
        Product product = entityFinder.findProduct(quote);
        Equipment equipment = entityFinder.findEquipment(quote);
        StatusCodes status = entityFinder.findStatusCode(quote);
        Tax tax = entityFinder.findTax(quote);

        Customer customer = entityFinder.findCustomer(quote);
        SalesStaff staff = entityFinder.findSalesStaff(quote);

        if (qResult != null) {
            log.debug("Found existing Quote: " + quote.getId());

            qResult.setTerm(quote.getTerm());
            qResult.setAmount(quote.getAmount());
            qResult.setMonthlypayment(quote.getMonthlypayment());
            qResult.setMonthlypaymentaftertax(quote.getMonthlypaymentaftertax());
            qResult.setWeeklypayment(quote.getWeeklypayment());
            qResult.setWeeklypaymentaftertax(quote.getWeeklypaymentaftertax());
            qResult.setResidual(quote.getResidual());
            qResult.setSubmitdate(quote.getSubmitdate());

            // Link all the nested elements
            qResult.setCustomer(customer);
            qResult.setEquipment(equipment);
            qResult.setProduct(product);
            qResult.setSalesStaff(staff);
            qResult.setStatuscode(status);
            qResult.setTax(tax);
        } else {
            log.debug("Brand new record... saving it as is!");
            // Link all the nested elements
            quote.setCustomer(customer);
            quote.setEquipment(equipment);
            quote.setProduct(product);
            quote.setSalesStaff(staff);
            quote.setStatuscode(status);
            quote.setTax(tax);
            qResult = quoteRepository.save(quote);
        }
        log.debug("Returning result Id: " + qResult.getId());

        SendEmail(qResult);

        return qResult;
    }

    // TODO Move this to SWF to process these rather than simple EMAIL only.
    private void SendEmail(Quote qResult) {
        log.info("Sending Quote Email");
        Configuration to = configurationRepository.findOne("flexi.email.recipient");
        Configuration imgUrlConfig = configurationRepository.findOne("flexi.url.public");
        Configuration subject = configurationRepository.findOne("flexi.email.subject");
        String imageUrl;
        if (imgUrlConfig != null) {
            imageUrl = imgUrlConfig.getValue() + FLEXI_EMAIL_BANNER_PATH;
        } else {
            imageUrl = FLEXI_EMAIL_BANNER_PATH;
        }

        try {
            String emailBody = emailGenerator.generateEmailBody(qResult, new Date(), imageUrl);
            List<String> recipients = new ArrayList<String>();
            if (to != null) {
                recipients = Arrays.asList(to.getValue().split(","));
            } else {
                recipients.add("corey.banks@silverquest.com.au");
                recipients.add("john.kim@silverquest.com.au");
            }
            log.debug("Sending email to " + recipients.size() + " recipients.");
            emailService.sendMail(recipients, subject.getValue(), emailBody);
        } catch (Exception e) {
            log.error("Unable to generate email! " + e.getLocalizedMessage());
        }
    }
}
