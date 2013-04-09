package au.com.silverquest.flexigroup.services;

import au.com.silverquest.flexigroup.model.entity.Rates;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 24/02/13
 * Time: 12:29 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RatesService {

    Rates findRatesByProductAndTerm(Integer price, Integer term, String product);

    Rates findResidualRate(Integer term);

}
