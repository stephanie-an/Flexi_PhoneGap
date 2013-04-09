package au.com.silverquest.flexigroup.model;

import au.com.silverquest.flexigroup.model.entity.*;
import au.com.silverquest.flexigroup.model.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: MC
 * Date: 2/20/13
 * Time: 1:21 AM
 * To change this template use File | Settings | File Templates.
 */
@Named("entityLoader")
public class EntityLoader {
    private static final Logger log = LoggerFactory.getLogger(EntityLoader.class);
    public static final String COMMERCIAL_OPERATING_LEASE = "Commercial Operating Lease";
    public static final String COMMERCIAL_MINI_LEASE = "Commercial Finance Lease";

    @Inject
    private ProductRepository productRepository;

    @Inject
    EquipmentRepository equipmentRepository;

    @Inject
    private RatesRepository ratesRepository;

    @Inject
    private TaxRepository taxRepository;

    @Inject
    private StatusCodesRepository statusCodesRepository;

    @Inject
    private ContentTypeRepository contentTypeRepository;

    @Inject
    private DealerRepository dealerRepository;

    @Inject
    private SalesStaffRepository salesStaffRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private RoleRepository roleRepository;

    @Inject
    private CustomerRepository customerRepository;

    @PostConstruct
    private void load() {
        log.info("Generating the Product's");

        Product opLease, miniLease;
        try {
            opLease = productRepository.save(new Product(COMMERCIAL_OPERATING_LEASE));
        } catch (Exception e) {
            log.debug(COMMERCIAL_OPERATING_LEASE + " Already exists");
            opLease = productRepository.findByName(COMMERCIAL_OPERATING_LEASE).get(0);
            log.debug("id = " + opLease.getId());
        }
        try {
            miniLease = productRepository.save(new Product(COMMERCIAL_MINI_LEASE));
        } catch (Exception e) {
            log.debug(COMMERCIAL_MINI_LEASE + " Already exists");
            miniLease = productRepository.findByName(COMMERCIAL_MINI_LEASE).get(0);
            log.debug("id = " + miniLease.getId());
        }

        log.info("Generating the Equipment");
        wrapEquipment(new Equipment("AV"));
        wrapEquipment(new Equipment("CATERING"));
        wrapEquipment(new Equipment("COFFEE MACHINES"));
        wrapEquipment(new Equipment("COMMERCIAL LAUNDRY"));
        wrapEquipment(new Equipment("POINT OF SALE"));
        wrapEquipment(new Equipment("TELEPHONY"));
        wrapEquipment(new Equipment("TOOLS"));
        wrapEquipment(new Equipment("HEALTH/BEAUTY"));
        wrapEquipment(new Equipment("PRO CAMERA"));
        wrapEquipment(new Equipment("IT"));
        wrapEquipment(new Equipment("SECURITY"));
        wrapEquipment(new Equipment("COMMERCIAL CLEANING"));
        wrapEquipment(new Equipment("COMMERCIAL MACHINERY"));
        wrapEquipment(new Equipment("AGRICULTURE MACHINERY/EQUIPMENT"));
        wrapEquipment(new Equipment("GYM & FITNESS EQUIPMENT"));
        wrapEquipment(new Equipment("OTHER"));

        log.info("Generating the Rates Table");
        RatesData[] opLeaseRates = new RatesData[]{
                new RatesData(500, 1250, 10.0, 5.82, 4.65, null, null),
                new RatesData(1251, 2250, 10.0, 5.54, 4.55, 3.75, 2.73),
                new RatesData(2251, 2750, null, 5.52, 4.50, 3.63, 2.73),
                new RatesData(2751, 3250, null, 5.5, 4.46, 3.60, 2.73),
                new RatesData(3251, 3750, null, 5.48, 4.43, 3.57, 2.73),
                new RatesData(3751, 4500, null, 5.43, 4.35, 3.56, 2.73),
                new RatesData(4501, 6000, null, 5.35, 4.17, 3.48, 2.58),
                new RatesData(6001, 7500, null, 5.14, 4.01, 3.22, 2.58),
                new RatesData(7501, 10000, null, 5.12, 3.81, 3.15, 2.58),
                new RatesData(10001, 20000, null, 4.95, 3.66, 2.95, 2.42),
                new RatesData(20001, 100000, null, 4.90, 3.55, 2.85, 2.37)
        };
        RatesData[] miniLeaseRates = new RatesData[]{
                // Special Case (yuck) for Residual Amounts
                new RatesData(0, 0, null, null, 15.0, 10.0, 5.0),
                // Rates Data
                new RatesData(1500, 2499, null, null, 4.39, 3.62, 3.11),
                new RatesData(2500, 3999, null, null, 4.37, 3.59, 3.09),
                new RatesData(4000, 5999, null, null, 3.97, 3.26, 2.81),
                new RatesData(6000, 7999, null, null, 3.72, 3.02, 2.57),
                new RatesData(8000, 9999, null, null, 3.71, 2.99, 2.55),
                new RatesData(10000, 11999, null, null, 3.69, 2.97, 2.54),
                new RatesData(12000, 14999, null, null, 3.56, 2.84, 2.43),
                new RatesData(15000, 19999, null, null, 3.54, 2.82, 2.41),
                new RatesData(20000, 100000, null, null, 3.43, 2.79, 2.37)
        };

        GenerateTheRates(opLease, opLeaseRates);
        GenerateTheRates(miniLease, miniLeaseRates);

        log.info("Generating the Tax Rates");
        wrapTaxRates(new Tax("$6k - $34k", 6000, 34000, new BigDecimal(15.0), new BigDecimal(0.835)));
        wrapTaxRates(new Tax("$34k - $80k", 34000, 80000, new BigDecimal(30.0), new BigDecimal(0.685)));
        wrapTaxRates(new Tax("$80k - $180k", 80000, 180000, new BigDecimal(40.0), new BigDecimal(0.585)));
        wrapTaxRates(new Tax("$180k+", 180000, -1, new BigDecimal(45.0), new BigDecimal(0.535)));
        wrapTaxRates(new Tax("Company + GST", -1, -1, new BigDecimal(40.0), new BigDecimal(0.6363)));

        log.info("Generating the Status Codes");
        StatusCodes status01 = new StatusCodes(0, StatusCodeEnum.UNKNOWN.toString());
        StatusCodes status02 = new StatusCodes(1, StatusCodeEnum.NEW.toString());
        StatusCodes status03 = new StatusCodes(2, StatusCodeEnum.SAVED.toString());
        StatusCodes status04 = new StatusCodes(3, StatusCodeEnum.PENDING.toString());
        StatusCodes status05 = new StatusCodes(4, StatusCodeEnum.SUBMITTED.toString());
        StatusCodes status06 = new StatusCodes(5, StatusCodeEnum.PROCESSED.toString());
        status01.setUuid(UUID.fromString("2A9B875A-8877-11E2-A71D-E0F8471D80CD"));
        status02.setUuid(UUID.fromString("2A9B8ACA-8877-11E2-A71D-E0F8471D80CD"));
        status03.setUuid(UUID.fromString("2A9B8BDD-8877-11E2-A71D-E0F8471D80CD"));
        status03.setUuid(UUID.fromString("2A9B8C9B-8877-11E2-A71D-E0F8471D80CD"));
        status05.setUuid(UUID.fromString("2A9B8D51-8877-11E2-A71D-E0F8471D80CD"));
        status06.setUuid(UUID.fromString("2A9B8E02-8877-11E2-A71D-E0F8471D80CD"));

        try {
            statusCodesRepository.save(status01);
            statusCodesRepository.save(status02);
            statusCodesRepository.save(status03);
            statusCodesRepository.save(status04);
            statusCodesRepository.save(status05);
            statusCodesRepository.save(status06);
        } catch (Exception e) {
            log.debug("Status already exists");
        }


        log.info("Generating the Content Types");
        ContentType ct1 = new ContentType("Ticker Text");
        ContentType ct2 = new ContentType("Flexi Benefits");
        ContentType ct3 = new ContentType("News & Offers");
        ContentType ct4 = new ContentType("Key Contacts");
        ct1.setUuid(UUID.fromString("60cb000a-88b3-11e2-ae19-089e01021beb"));
        ct2.setUuid(UUID.fromString("27e5661f-88b3-11e2-ae19-089e01021beb"));
        ct3.setUuid(UUID.fromString("3989f160-8c2a-11e2-a6f9-001374000000"));
        ct4.setUuid(UUID.fromString("3ee0e9a1-8c2a-11e2-a6f9-001374000000"));

        try {
            contentTypeRepository.save(ct1);
            contentTypeRepository.save(ct2);
            contentTypeRepository.save(ct3);
            contentTypeRepository.save(ct4);
        } catch (Exception e) {
            log.debug(ct1.getType() + " Already exists");
        }

        log.info("Generating Default Roles");

        Role role1 = new Role("ROLE_ROOT", "SILVERQUEST");
        Role role2 = new Role("ROLE_ADMIN", "FLEXI COMMERCIAL");
        Role role3 = new Role("ROLE_USER", "USER");
        try {
            roleRepository.save(role1);
            roleRepository.save(role2);
            roleRepository.save(role3);
        } catch (Exception e) {
            log.debug("Roles already exist");
        }

        List<Role> roles1 = new ArrayList<Role>();
        roles1.add(role1);
        roles1.add(role2);
        roles1.add(role3);

        List<Role> roles2 = new ArrayList<Role>();
        roles2.add(role2);
        roles2.add(role3);

        List<Role> roles3 = new ArrayList<Role>();
        roles3.add(role3);

        log.info("Generating Default Users");

        User user1 = new User("Stephanie", "An", "steph_an", "pass", "stephanie.an@silverquest.com.au", roles1);
        User user2 = new User("John", "Kim", "jkim", "pass", "john.kim@silverquest.com.au", roles1);
        User user3 = new User("Corey", "Banks", "coreyb", "pass", "corey.banks@silverquest.com.au", roles1);
        User user4 = new User("David", "Beckham", "admin", "pass", "manchesterunited@flexigroup.com.au", roles2);
        User user5 = new User("Justin", "Bieber", "user", "pass", "bieberfever@flexigroup.com.au", roles3);
        try {
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);
            userRepository.save(user4);
            userRepository.save(user5);
        } catch (Exception e) {
            log.debug("Users already exist");
        }

        Customer c1 = new Customer("Angelina Jolie", "ange@gmail.com", "0423 938 005", null);
        Customer c2 = new Customer("Will.i.am", "blackeyedpeas@hotmail.com", "0430 019 338", null);
        Customer c3 = new Customer("Tom Cruz", "missionimpossible@gmail.com", "0433 221 118", null);

        try {
            customerRepository.save(c1);
            customerRepository.save(c2);
            customerRepository.save(c3);
        } catch (Exception e) {
            log.debug("Users already exist");
        }

        log.info("Generating Default Dealers");
        Dealer d1 = new Dealer("Harvey Norman", "dealer@harveynorman.com.au", "02 9048 9000");
        Dealer d2 = new Dealer("JB HI-FI", "dealer@jbhifi.com.au", "02 9304 9333");
        Dealer d3 = new Dealer("BMW", "dealer@bmw.com.au", "02 8392 0039");
        Dealer d4 = new Dealer("Appliances Online", "dealer@appliances.com.au", "02 9273 7011");

        d1.setUuid(UUID.fromString("1f65f398-88b3-11e2-ae19-089e01021beb"));
        d2.setUuid(UUID.fromString("665bb5e9-88b3-11e2-ae19-089e01021beb"));
        d3.setUuid(UUID.fromString("432fe2e3-88b3-11e2-ae19-089e01021beb"));
        d4.setUuid(UUID.fromString("3977d49c-88b3-11e2-ae19-089e01021beb"));

        try {
            dealerRepository.save(d1);
            dealerRepository.save(d2);
            dealerRepository.save(d3);
            dealerRepository.save(d4);
        } catch (Exception e) {
            log.debug("Dealers already exist");
        }

        log.info("Generating Default Sales Staffs");

        SalesStaff ss1 = new SalesStaff("Jack", "Johnson", "jj@harveynorman.com.au", "02 9048 9012", d1);
        SalesStaff ss2 = new SalesStaff("Nicole", "Kidman", "moulinrouge@harveynorman.com.au", "02 9048 9012", d1);
        SalesStaff ss3 = new SalesStaff("Jennifer", "Lopez", "jennyfromtheblock@appliances.com.au", "02 9273 7019", d4);
        SalesStaff ss4 = new SalesStaff("Michael", "Jackson", "kingofpop@bmw.com.au", "02 8392 1023", d2);

        ss1.setUuid(UUID.fromString("2dc00144-88b3-11e2-ae19-089e01021beb"));
        ss2.setUuid(UUID.fromString("4cf4d89c-88b3-11e2-ae19-089e01021beb"));
        ss3.setUuid(UUID.fromString("6cdb20af-88b3-11e2-ae19-089e01021beb"));
        ss4.setUuid(UUID.fromString("34cb2b7a-88b3-11e2-ae19-089e01021beb"));

        try {
            salesStaffRepository.save(ss1);
            salesStaffRepository.save(ss2);
            salesStaffRepository.save(ss3);
            salesStaffRepository.save(ss4);
        } catch (Exception e) {
            log.debug("Sales Staffs already exist");
        }
    }



    private void GenerateTheRates(Product product, RatesData[] ratesData) {
        for (RatesData rateData : ratesData) {
            Integer minAmt = rateData.minAmount;
            Integer maxAmt = rateData.maxAmount;
            for (int i = 0; i < 5; i++) {
                BigDecimal rate = rateData.rates[i];
                if (rate != null) {
                    Rates pRate = new Rates(minAmt, maxAmt, rateData.terms[i], rate, product);
                    try {
                        ratesRepository.save(pRate);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    // I am not going to make this more generic...

    private void wrapTaxRates(Tax rate) {
        try {
            taxRepository.save(rate);
        } catch (Exception e) {
        }
    }

    private void wrapEquipment(Equipment equip) {
        try {
            equipmentRepository.save(equip);
        } catch (Exception e) {
        }
    }

    private class RatesData {
        public Integer minAmount;
        public Integer maxAmount;
        public BigDecimal[] rates;
        public Integer[] terms = new Integer[]{12, 24, 36, 48, 60};

        public RatesData(Integer minAmount, Integer maxAmount, Double rate12, Double rate24, Double rate36, Double rate48, Double rate60) {
            this.minAmount = minAmount;
            this.maxAmount = maxAmount;
            rates = new BigDecimal[5];
            this.rates[0] = (rate12 == null) ? null : new BigDecimal(rate12);
            this.rates[1] = (rate24 == null) ? null : new BigDecimal(rate24);
            this.rates[2] = (rate36 == null) ? null : new BigDecimal(rate36);
            this.rates[3] = (rate48 == null) ? null : new BigDecimal(rate48);
            this.rates[4] = (rate60 == null) ? null : new BigDecimal(rate60);
        }
    }
}
