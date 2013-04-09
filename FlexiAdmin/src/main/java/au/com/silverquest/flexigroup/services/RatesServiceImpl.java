package au.com.silverquest.flexigroup.services;

import au.com.silverquest.flexigroup.model.entity.Rates;
import au.com.silverquest.flexigroup.model.repository.RatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 24/02/13
 * Time: 12:29 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("ratesService")
public class RatesServiceImpl implements RatesService {

    @Autowired
    RatesRepository ratesRepository;

    /**
     * Method to get distinct Rate for given parameters
     * @param price
     * @param term
     * @param product
     * @return
     */
    @Override
    public Rates findRatesByProductAndTerm(Integer price, Integer term, String product) {
      List<Rates> rates = ratesRepository.findByProductAndTerm(price, term, product);

      if(rates.isEmpty()) {
        return null;
      }
      return rates.get(0);
    }

    @Override
    public Rates findResidualRate(Integer term) {
      List<Rates> rates = ratesRepository.findResidualRate(term);

      if(rates.isEmpty()) {
        return null;
      }
      return rates.get(0);

    }
}
