package au.com.silverquest.flexigroup.util;

import au.com.silverquest.flexigroup.model.entity.*;
import au.com.silverquest.flexigroup.model.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: coreyb
 * Date: 23/02/13
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SubEntityFinder {

    final Logger log = LoggerFactory.getLogger(SubEntityFinder.class);

    @Inject
    private DealerRepository dealerRepository;

    @Inject
    private CustomerRepository customerRepository;

    @Inject
    private EquipmentRepository equipmentRepository;

    @Inject
    private ProductRepository productRepository;

    @Inject
    private SalesStaffRepository salesStaffRepository;

    @Inject
    private StatusCodesRepository statusCodesRepository;

    @Inject
    private TaxRepository taxRepository;

    /**
     * Take a SalesStaff object, and either find or create the Dealer from whatever info was supplied.
     *
     * @param staff Sales Staff to find dealer from.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Dealer findDealer(SalesStaff staff) {
        Dealer realRecord = null;

        Dealer dealer = staff.getDealer();
        if (dealer != null) {
            // This is the order we try to link them with.
            Integer id = dealer.getId();
            UUID uuid = dealer.getUuid();
            String name = dealer.getName();

            if (id != null) {
                log.debug("Have a dealer Id to link: " + id);
                realRecord = dealerRepository.findOne(id);
                if (realRecord != null) {
                    return realRecord;
                }
                log.debug("Not found, ignore it!");
            }

            if (uuid != null) {
                log.debug("Have a dealer UUID to link: " + uuid);
                realRecord = dealerRepository.findByUuid(uuid);
                if (realRecord != null) {
                    return realRecord;
                }
                log.debug("Not found");
            }

            if (name != null) {
                log.debug("Only have a dealer name to link: " + name);
                List<Dealer> dealerList = dealerRepository.findByName(name);
                if ((dealerList != null) && (dealerList.size() > 0)) {
                    realRecord = dealerList.get(0);
                    log.debug("Found an existing dealer with that name: " + realRecord.getId());
                    return realRecord;
                } else {
                    log.debug("Not found, creating a new dealer");
                    Dealer tDealer = new Dealer();
                    tDealer.setName(name);
                    if (uuid != null) {
                        // We will honour the UUID sent.
                        tDealer.setUuid(uuid);
                    } else {
                        tDealer.setUuid(UUID.randomUUID());
                    }
                    realRecord = dealerRepository.save(tDealer);
                    return realRecord;
                }
            }
        }
        log.debug("No dealer to link");
        return realRecord;
    }

    /**
     * Take a Quote object, and either find or create the Customer from whatever info was supplied.
     *
     * @param quote Quote to find Customer from.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Customer findCustomer(Quote quote) {
        Customer realRecord = null;

        Customer cust = quote.getCustomer();
        if (cust != null) {
            // This is the order we try to link them with.
            Integer id = cust.getId();
            UUID uuid = cust.getUuid();
            String name = cust.getName();
            String email = cust.getEmail();
            String phone = cust.getPhone();
            String comment = cust.getComment();
            comment = comment.replaceAll("(\r\n|\n)","<br/>");

            if (id != null) {
                log.debug("Have a customer Id to link: " + id);
                realRecord = customerRepository.findOne(id);
                if (realRecord != null) {
                    return realRecord;
                }
                log.debug("Not found, ignore it!");
            }

            if (uuid != null) {
                log.debug("Have a customer UUID to link: " + uuid);
                realRecord = customerRepository.findByUuid(uuid);
                if (realRecord != null) {
                    return realRecord;
                }
                log.debug("Not found");
            }

            if ((name != null) && (email != null)) {
                log.debug("Only have a customer name and contact details to link: " + name + " - " + email + " - " + phone);
                List<Customer> customerList = customerRepository.findByNameAndEmailAndPhone(name, email, phone);

                if ((customerList != null) && (customerList.size() == 1)) {
                    realRecord = customerList.get(0);
                    realRecord.setComment(comment);
                    log.debug("Found an existing customer with the same name and contact details: " + realRecord.getId());
                    return realRecord;
                } else {
                    log.debug("Not found, creating a new customer");
                    Customer tCustomer = new Customer(name, email, cust.getPhone(),comment);
                    if (uuid != null) {
                        // We will honour the UUID sent.
                        tCustomer.setUuid(uuid);
                    }
                    realRecord = customerRepository.save(tCustomer);
                    return realRecord;
                }
            }
        }
        log.debug("No customer to link");
        return realRecord;
    }

    /**
     * Take a Quote object, and either find or create the Equipment from whatever info was supplied.
     *
     * @param quote Quote to find Customer from.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Equipment findEquipment(Quote quote) {
        Equipment realRecord = null;

        Equipment equip = quote.getEquipment();
        if (equip != null) {
            // This is the order we try to link them with.
            Integer id = equip.getId();
            UUID uuid = equip.getUuid();
            String name = equip.getName();

            if (id != null) {
                log.debug("Have a equipment Id to link: " + id);
                realRecord = equipmentRepository.findOne(id);
                if (realRecord != null) {
                    return realRecord;
                }
                log.debug("Not found, ignore it!");
            }

            if (uuid != null) {
                log.debug("Have a equipment UUID to link: " + uuid);
                realRecord = equipmentRepository.findByUuid(uuid);
                if (realRecord != null) {
                    return realRecord;
                }
                log.debug("Not found");
            }

            if (name != null) {
                log.debug("Only have a equipment name to link: " + name);
                List<Equipment> equipmentList = equipmentRepository.findByName(name);
                if ((equipmentList != null) && (equipmentList.size() == 1)) {
                    realRecord = equipmentList.get(0);
                    log.debug("Found existing equipment with the same name: " + realRecord.getId());
                    return realRecord;
                } else {
                    log.debug("Not found, creating new equipment");
                    Equipment tEquipment = new Equipment(name);
                    if (uuid != null) {
                        // We will honour the UUID sent.
                        tEquipment.setUuid(uuid);
                    }
                    realRecord = equipmentRepository.save(tEquipment);
                    return realRecord;
                }
            }
        }
        log.debug("No equipment to link");
        return realRecord;
    }

    /**
     * Take a Quote object, and either find or create the Product from whatever info was supplied.
     *
     * @param quote Quote to find Customer from.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Product findProduct(Quote quote) {
        Product realRecord = null;

        Product product = quote.getProduct();
        if (product != null) {
            // This is the order we try to link them with.
            Integer id = product.getId();
            UUID uuid = product.getUuid();
            String name = product.getName();

            if (id != null) {
                log.debug("Have a product Id to link: " + id);
                realRecord = productRepository.findOne(id);
                if (realRecord != null) {
                    return realRecord;
                }
                log.debug("Not found, ignore it!");
            }

            if (uuid != null) {
                log.debug("Have a product UUID to link: " + uuid);
                realRecord = productRepository.findByUuid(uuid);
                if (realRecord != null) {
                    return realRecord;
                }
                log.debug("Not found");
            }

            if (name != null) {
                log.debug("Only have a product name to link: " + name);
                List<Product> productList = productRepository.findByName(name);
                if ((productList != null) && (productList.size() == 1)) {
                    realRecord = productList.get(0);
                    log.debug("Found existing product with the same name: " + realRecord.getId());
                    return realRecord;
                } else {
                    log.error("Tried to link a quote to an un registered product");
                }
            }
        }
        log.debug("No equipment to link");
        return realRecord;
    }

    /**
     * Take a Quote object, and either find or create the Sales Staff from whatever info was supplied.
     *
     * @param quote Quote to find Customer from.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public SalesStaff findSalesStaff(Quote quote) {
        SalesStaff realRecord = null;

        SalesStaff salesStaff = quote.getSalesStaff();
        if (salesStaff != null) {
            // This is the order we try to link them with.
            Integer id = salesStaff.getId();
            UUID uuid = salesStaff.getUuid();

            if (id != null) {
                log.debug("Have a salesStaff Id to link: " + id);
                realRecord = salesStaffRepository.findOne(id);
                if (realRecord != null) {
                    return realRecord;
                }
                log.debug("Not found, ignore it!");
            }

            if (uuid != null) {
                log.debug("Have a salesStaff UUID to link: " + uuid);
                realRecord = salesStaffRepository.findByUuid(uuid);
                if (realRecord != null) {
                    return realRecord;
                }
                log.debug("Not found");
            }

            log.debug("Not found, creating new sales staff");
            SalesStaff tSalesStaff = salesStaff;
            if (tSalesStaff.getUuid() == null) {
                tSalesStaff.setUuid(UUID.randomUUID());
            }
            tSalesStaff.setDealer(this.findDealer(salesStaff));
            realRecord = salesStaffRepository.save(tSalesStaff);
            return realRecord;
        }
        log.debug("No equipment to link");
        return realRecord;
    }

    /**
     * Take a Quote object, and find the Status Code from whatever info was supplied.
     *
     * @param quote Quote to find Customer from.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public StatusCodes findStatusCode(Quote quote) {
        StatusCodes realRecord = null;

        StatusCodes statusCode = quote.getStatuscode();
        if (statusCode != null) {
            // This is the order we try to link them with.
            Integer id = statusCode.getId();
            String value = statusCode.getValue();

            if (id != null) {
                log.debug("Have a statusCode Id to link: " + id);
                realRecord = statusCodesRepository.findOne(id);
                if (realRecord != null) {
                    return realRecord;
                }
                log.debug("Not found, ignore it!");
            }

            if (value != null) {
                log.debug("Have a statusCode value to link: " + value);
                List<StatusCodes> statusCodes = statusCodesRepository.findByValue(value);
                if ((statusCodes != null) && (statusCodes.size() > 0)) {
                    realRecord = statusCodes.get(0);
                    log.debug("Found the matching status code: " + realRecord.getId());
                    return realRecord;
                }
                log.debug("Not found");
            }
        }
        log.debug("No Status Code to link, setting it to UNKNOWN");
        realRecord = statusCodesRepository.findOne(0);
        return realRecord;
    }

    /**
     * Take a Quote object, and find the Status Code from whatever info was supplied.
     *
     * @param quote Quote to find Customer from.
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Tax findTax(Quote quote) {
        Tax realRecord = null;

        Tax tax = quote.getTax();
        if (tax != null) {
            // This is the order we try to link them with.
            Integer id = tax.getId();
            UUID uuid = tax.getUuid();
            String description = tax.getDescription();

            if (id != null) {
                log.debug("Have a tax Id to link: " + id);
                realRecord = taxRepository.findOne(id);
                if (realRecord != null) {
                    return realRecord;
                }
                log.debug("Not found, ignore it!");
            }

            if (uuid != null) {
                log.debug("Have a tax UUID to link: " + uuid);
                realRecord = taxRepository.findByUuid(uuid);
                if (realRecord != null) {
                    return realRecord;
                }
                log.debug("Not found");
            }

            if (description != null) {
                log.debug("Have a tax description to link: " + description);
                List<Tax> taxes = taxRepository.findByDescription(description);
                if ((taxes != null) && (taxes.size() == 1)) {
                    realRecord = taxes.get(0);
                    log.debug("Found the matching tax bracket: " + realRecord.getId());
                    return realRecord;
                } else {
                    log.error("Tried to link a quote to an unknown tax bracket");
                }
            }
        }
        log.debug("No tax bracket to link, setting it to UNKNOWN");
        return realRecord;
    }
}
