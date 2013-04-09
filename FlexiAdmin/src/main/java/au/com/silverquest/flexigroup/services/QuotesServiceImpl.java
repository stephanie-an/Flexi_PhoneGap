package au.com.silverquest.flexigroup.services;

import au.com.silverquest.flexigroup.model.entity.Quote;
import au.com.silverquest.flexigroup.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 24/02/13
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("quoteService")
public class QuotesServiceImpl implements QuoteService {

    @Autowired
    RatesService ratesService;

    @Autowired
    TaxRepository taxRepository;

    @Autowired
    EquipmentRepository equipmentRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    QuoteRepository quoteRepository;


    public void populateChildren(Quote quote) {

       if( quote.getCustomer() != null ){
         quote.setCustomer(customerRepository.findOne(quote.getCustomer().getId()));
       }

       if( quote.getTax() != null) {
         quote.setTax(taxRepository.findOne(quote.getTax().getId()));
       }

       if( quote.getEquipment() != null ){
         quote.setEquipment(equipmentRepository.findOne(quote.getEquipment().getId()));
       }

       if( quote.getProduct() != null ){
         quote.setProduct(productRepository.findOne(quote.getProduct().getId()));
       }

    }

    public List<Quote> listQuote() {
        return quoteRepository.getAll();
    }

    public List<Quote> findByProductName(String productName) {
        return quoteRepository.findByProductName("%"+productName+"%");
    }

    public List<Quote> findBySubmitDate(Date fromSubmitDate, Date toSubmitDate) {
        if (fromSubmitDate != null && toSubmitDate != null) {
            return quoteRepository.findbyDateRange(fromSubmitDate, getNextDate(toSubmitDate));
        } else if (fromSubmitDate != null && toSubmitDate == null) {
            return quoteRepository.findByFromDate(fromSubmitDate);
        } else {

            return quoteRepository.findByToDate(getNextDate(toSubmitDate));
        }
    }

    public List<Quote> findByProductNameAndSubmitDate(String productName, Date fromSubmitDate, Date toSubmitDate) {
        if (fromSubmitDate != null && toSubmitDate != null) {
            return quoteRepository.findByProductNameAndSubmitDate("%"+productName+"%", fromSubmitDate, getNextDate(toSubmitDate));
        } else if (fromSubmitDate != null && toSubmitDate == null) {
            return quoteRepository.findByProductNameAndFromDate("%"+productName+"%", fromSubmitDate);
        } else {
            return quoteRepository.findByProductNameAndToDate("%"+productName+"%", getNextDate(toSubmitDate));
        }
    }

    public void save(Quote quote) {
        quoteRepository.save(quote);
    }

    public void delete(Quote quote) {
        quoteRepository.delete(quote);
    }

    private Date getNextDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);
        date = c.getTime();
        return date;
    }

    public void writeCSV(Quote quote) {

    }
}
