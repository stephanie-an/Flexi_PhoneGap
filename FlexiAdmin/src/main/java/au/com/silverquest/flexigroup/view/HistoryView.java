package au.com.silverquest.flexigroup.view;


import au.com.silverquest.flexigroup.model.entity.*;
import au.com.silverquest.flexigroup.model.repository.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: MC
 * Date: 2/23/13
 * Time: 11:03 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("historyManager")
@Scope("view")
public class HistoryView implements Serializable {
    private List<Quote> history;
    private Quote quote;

    @Autowired
    private QuoteRepository quoteRepository;

    @PostConstruct
    public void init() {
        history = new ArrayList<Quote>();
    }

    public List<Quote> getHistory() {
        return history;
    }

    public void setHistory(List<Quote> history) {
        this.history = history;
    }

    public List<Quote> getAllHistory() {


        getHistory().add(quote1());
        getHistory().add(quote2());
        return getHistory();
    }

    public Quote quote1() {
        Quote quote = new Quote();
        Customer customer = new Customer();
        customer.setId(1);
        customer.setComment("ABC");
        customer.setEmail("johnsmith@hotmail.com");
        customer.setName("John Smith");
        customer.setPhone("00000000");

        Product product = new Product();
        product.setName("Test 1");

        StatusCodes statusCodes = new StatusCodes(1, "Submitted");

        SalesStaff salesStaff = new SalesStaff();
        salesStaff.setEmail("abc@hotmail.com");
        salesStaff.setFirstname("John");
        salesStaff.setLastname("Smith");

        quote.setId(new Integer(1));
        quote.setAmount(BigDecimal.TEN);
        quote.setCustomer(customer);
        quote.setMonthlypayment(BigDecimal.valueOf(20));
        quote.setMonthlypaymentaftertax(BigDecimal.valueOf(18));
        quote.setProduct(product);
        quote.setResidual(BigDecimal.valueOf(1000));
        quote.setStatuscode(statusCodes);
        quote.setSalesStaff(salesStaff);
        quote.setSubmitdate(Calendar.getInstance().getTime());
        quote.setTerm(new Integer(24));
        quote.setWeeklypayment(BigDecimal.valueOf(20));

        return quote;
    }

    public Quote quote2() {
        Quote quote = new Quote();
        Customer customer = new Customer();
        customer.setId(2);
        customer.setComment("ABC");
        customer.setEmail("Charles@hotmail.com");
        customer.setName("Charles");
        customer.setPhone("035345389");

        Product product = new Product();
        product.setName("Test 1");

        StatusCodes statusCodes = new StatusCodes(1, "Submitted");

        SalesStaff salesStaff = new SalesStaff();
        salesStaff.setEmail("abc@hotmail.com");
        salesStaff.setFirstname("John");
        salesStaff.setLastname("Smith");

        quote.setId(new Integer(2));
        quote.setAmount(BigDecimal.valueOf(23.50));
        quote.setCustomer(customer);
        quote.setMonthlypayment(BigDecimal.valueOf(20));
        quote.setMonthlypaymentaftertax(BigDecimal.valueOf(18));
        quote.setProduct(product);
        quote.setResidual(BigDecimal.valueOf(1000));
        quote.setStatuscode(statusCodes);
        quote.setSalesStaff(salesStaff);
        quote.setSubmitdate(Calendar.getInstance().getTime());
        quote.setTerm(new Integer(24));
        quote.setWeeklypayment(BigDecimal.valueOf(20));

        return quote;
    }

    public Quote getQuote() {
        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

        if (id != null) {
//            if ("1")
            if ("1".equalsIgnoreCase(id)) {
                quote = quote1();
            } else {
                quote = quote2();
            }
        }
        return quote;
    }

    public void onRowSelect(Quote quote) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("flexiMobHistoryQuoteInfo.xhtml?id=" + quote.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editQuote() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("flexiMobCalculation.xhtml?id=");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void emailQuote() {
        try {
            FacesMessage msg = new FacesMessage("Success", "Quote has been emailed to Flexirent!");
            FacesContext.getCurrentInstance().addMessage(null, msg);

//            Thread.sleep(3000);
//            FacesContext.getCurrentInstance().getExternalContext().redirect("flexiMobHome.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
