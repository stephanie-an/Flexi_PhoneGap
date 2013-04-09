package au.com.silverquest.flexigroup.services.mobile;

/**
 * Created with IntelliJ IDEA.
 * User: coreyb
 * Date: 22/02/13
 * Time: 9:18 PM
 * To change this template use File | Settings | File Templates.
 */

import au.com.silverquest.flexigroup.model.entity.Rates;
import au.com.silverquest.flexigroup.model.repository.RatesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Controller
public class RatesJsonService {

    final Logger log = LoggerFactory.getLogger(RatesJsonService.class);

    @Inject
    private RatesRepository ratesRepository;

    @RequestMapping(value = "/rates", method = RequestMethod.GET, headers = "Accept=application/json")
    public
    @ResponseBody
    List<Rates> getRates(@RequestParam(value = "lastmodified", required = false, defaultValue = "0") Long lastmodified) {
        log.info("getRates() service call received: " + lastmodified);
        List<Rates> rates = ratesRepository.getAll(new Date(lastmodified));
        log.debug("Returning results: " + rates.size());
        return rates;
    }

    @RequestMapping(value = "/rates/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public
    @ResponseBody
    Rates getRatesById(@PathVariable Integer id) {
        log.info("getRatesById() service call received for: " + id);
        Rates rates = ratesRepository.findOne(id);
        log.debug("Returning results: " + rates.getUuid());
        return rates;
    }
}
