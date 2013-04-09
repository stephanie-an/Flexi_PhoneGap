package au.com.silverquest.flexigroup.services.mobile;

/**
 * Created with IntelliJ IDEA.
 * User: coreyb
 * Date: 22/02/13
 * Time: 9:18 PM
 * To change this template use File | Settings | File Templates.
 */

import au.com.silverquest.flexigroup.model.entity.Equipment;
import au.com.silverquest.flexigroup.model.repository.EquipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Controller
public class EquipmentJsonService {

    final Logger log = LoggerFactory.getLogger(EquipmentJsonService.class);

    @Inject
    private EquipmentRepository equipmentRepository;

    @RequestMapping(value = "/equipment", method = RequestMethod.GET, headers = "Accept=application/json")
    public
    @ResponseBody
    List<Equipment> getEquipment(@RequestParam(value = "lastmodified", required = false, defaultValue = "0") Long lastmodified) {
        log.debug("getEquipment() service call received: " + lastmodified);
        List<Equipment> equipments = equipmentRepository.getAll(new Date(lastmodified));
        log.debug("Returning results: " + equipments.size());
        return equipments;
    }

    @RequestMapping(value = "/equipment/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public
    @ResponseBody
    Equipment getEquipmentById(@PathVariable Integer id) {
        log.debug("getEquipmentById() service call received for: " + id);
        Equipment equipment = equipmentRepository.findOne(id);
        log.debug("Returning results: " + equipment.getName());
        return equipment;
    }
}
