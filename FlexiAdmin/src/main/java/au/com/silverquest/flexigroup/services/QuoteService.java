package au.com.silverquest.flexigroup.services;

import au.com.silverquest.flexigroup.model.entity.Quote;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 24/02/13
 * Time: 11:49 AM
 * To change this template use File | Settings | File Templates.
 */
public interface QuoteService {

    void populateChildren(Quote quote);

    public List<Quote> listQuote();

    public List<Quote> findByProductName(String productName);

    public List<Quote> findBySubmitDate(Date fromSubmitDate, Date toSubmitDate);

    public List<Quote> findByProductNameAndSubmitDate(String productName, Date fromSubmitDate, Date toSubmitDate);

    public void save(Quote quote);

    public void delete(Quote quote);

    public void writeCSV(Quote quote);
}
