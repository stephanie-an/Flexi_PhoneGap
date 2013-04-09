package au.com.silverquest.flexigroup.services.mobile;

/**
 * Created with IntelliJ IDEA.
 * User: coreyb
 * Date: 22/02/13
 * Time: 9:18 PM
 * To change this template use File | Settings | File Templates.
 */

import au.com.silverquest.flexigroup.model.entity.SalesStaff;
import au.com.silverquest.flexigroup.model.repository.SalesStaffRepository;
import au.com.silverquest.flexigroup.util.SubEntityFinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

@Controller
public class SalesStaffJsonService {

    final Logger log = LoggerFactory.getLogger(SalesStaffJsonService.class);

    @Inject
    private SalesStaffRepository salesStaffRepository;

    @Inject
    SubEntityFinder subEntityFinder;

    // TODO Delete these once it's working...
    @RequestMapping(value = "/salesstaff", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public List<SalesStaff> getSalesStaff() {
        log.info("getSalesStaff() service call received");
        List<SalesStaff> salesStaff = salesStaffRepository.getAll();
        log.debug("Returning results: " + salesStaff.size());
        return salesStaff;
    }

    @RequestMapping(value = "/salesstaff/{uuid}", method = RequestMethod.GET, headers = "Accept=application/json")
    @ResponseBody
    public SalesStaff getSalesByUuid(@PathVariable UUID uuid) {
        log.info("getSalesStaffByUuid() service call received for: " + uuid);
        SalesStaff salesStaff = salesStaffRepository.findByUuid(uuid);
        if (salesStaff != null) {
            log.debug("Returning Staff Id: " + salesStaff.getId());
        } else {
            log.debug("No Staff found");
        }
        return salesStaff;
    }

    @RequestMapping(value = "/salesstaff", method = RequestMethod.POST, headers = "Accept=application/json")
    @ResponseBody
    public SalesStaff addSalesStaff(@RequestBody SalesStaff staff) {
        log.info("addSalesStaff() service call received");
        staff.setDealer(subEntityFinder.findDealer(staff));

        SalesStaff salesStaff;
        try {
            salesStaff = salesStaffRepository.save(staff);
        } catch (Exception e) {
            // Why are we trying to create something that already exists? Perhaps a PUT would be better
            log.error("Error trying to create non-unique staff: " + staff.getUuid());
            salesStaff = null;
        }
        log.debug("Saved staff id: " + salesStaff.getId());
        return salesStaff;
    }

    @Transactional
    @RequestMapping(value = "/salesstaff/{uuid}", method = RequestMethod.PUT, headers = "Accept=application/json")
    @ResponseBody
    public SalesStaff updateSalesStaff(@RequestBody SalesStaff staff, @PathVariable UUID uuid) {
        log.info("updateSalesStaff() service call received for: " + uuid);
        SalesStaff salesStaff = salesStaffRepository.findByUuid(uuid);
        if (salesStaff != null) {
            log.debug("Found existing Sales Staff: " + salesStaff.getId());
            salesStaff.setFirstname(staff.getFirstname());
            salesStaff.setLastname(staff.getLastname());
            salesStaff.setEmail(staff.getEmail());
            salesStaff.setPhone(staff.getPhone());
            salesStaff.setDealer(subEntityFinder.findDealer(staff));
        } else {
            log.debug("Brand new record... saving it as is!");
            staff.setDealer(subEntityFinder.findDealer(staff));
            salesStaff = salesStaffRepository.save(staff);
        }
        log.debug("Returning result Id: " + salesStaff.getId());
        return salesStaff;
    }
}
