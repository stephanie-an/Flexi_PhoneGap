package au.com.silverquest.flexigroup.services.mobile;

/**
 * Created with IntelliJ IDEA.
 * User: coreyb
 * Date: 22/02/13
 * Time: 9:18 PM
 * To change this template use File | Settings | File Templates.
 */

import au.com.silverquest.flexigroup.model.entity.Tax;
import au.com.silverquest.flexigroup.model.repository.TaxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Controller
public class TaxJsonService {

    final Logger log = LoggerFactory.getLogger(TaxJsonService.class);

    @Inject
    private TaxRepository taxRepository;

    @RequestMapping(value = "/tax", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<Tax> getTaxes(@RequestParam(value = "lastmodified", required = false, defaultValue = "0") Long lastmodified) {
        log.info("getTaxes() service call received: " + lastmodified);
        List<Tax> taxes = taxRepository.getAll(new Date(lastmodified));
        log.debug("Returning results: " + taxes.size());
        return taxes;
    }

    @RequestMapping(value = "/tax/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public
    @ResponseBody
    Tax getTaxesById(@PathVariable Integer id) {
        log.info("getTaxById() service call received for: " + id);
        Tax tax = taxRepository.findOne(id);
        log.debug("Returning results: " + tax.getUuid());
        return tax;
    }
}
