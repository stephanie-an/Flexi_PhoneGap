package au.com.silverquest.flexigroup.view;

import au.com.silverquest.flexigroup.model.entity.*;
import au.com.silverquest.flexigroup.model.repository.*;
import au.com.silverquest.flexigroup.services.QuoteService;
import au.com.silverquest.flexigroup.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: MC
 * Date: 2/24/13
 * Time: 1:28 PM
 * To change this template use File | Settings | File Templates.
 */
@Named("quoteManager")
@ManagedBean
@Scope("view")
public class QuoteView implements Serializable {

    @Inject
    private ProductRepository productRepository;
    @Inject
    private EquipmentRepository equipmentRepository;
    @Inject
    private TaxRepository taxRepository;
    @Inject
    private CustomerRepository customerRepository;
    @Inject
    private QuoteRepository quoteRepository;
    @Inject
    private StatusCodesRepository statusCodesRepository;
    @Inject
    private SalesStaffRepository salesStaffRepository;

    @Inject
    private ConfigurationRepository configurationRepository;

    private List<Product> allProducts;
    private List<Equipment> allEquipments;
    private List<Tax> allTaxBrackets;
    private Integer productId;
    private Integer equipmentId;
    private Integer term;
    private BigDecimal amount;
    private Integer taxId;
    private Customer customer;
    private Tax selectedTax;

    private Quote quote;
    private List<Quote> quoteList;
    private List<Quote> filteredQuotes;

    private String productSearch;
    private Date fromSubmitDate;
    private Date toSubmitDate;

    @Autowired
    private QuoteService quoteService;


    @PostConstruct
    public void init() {
        allProducts = new ArrayList<Product>();
        allEquipments = new ArrayList<Equipment>();
        allTaxBrackets = new ArrayList<Tax>();
        customer = new Customer();

        quote = new Quote();
        quoteList = new ArrayList<Quote>();
        loadQuoteList();
    }

    public String calculateQuote() {
        Customer customer = getCustomer();
        customer.setUuid(UUID.randomUUID());
        customer = customerRepository.save(customer);
        SalesStaff staff = salesStaffRepository.findByEmail("kl@harveynorman.com.au").get(0);
        Quote quote = new Quote();
        quote.setProduct(getProduct());
        quote.setTerm(getTerm());
        quote.setAmount(getAmount());
        quote.setCustomer(customer);
        quote.setTax(getTax());
        quote.setEquipment(getEquipment());
        StatusCodes statusCodes = statusCodesRepository.findByValue(StatusCodeEnum.SAVED.toString()).get(0);
        quote.setStatuscode(statusCodes);
        quote.setSalesStaff(staff);
        quote.setSubmitdate(Calendar.getInstance().getTime());
        quote.setUuid(UUID.randomUUID());
        quoteRepository.save(quote);
        return "flexiMobCalculationResults.xhtml??faces-redirect=true&id=1";
    }

    public void filterQuotes() {
        if ((getProductSearch() == null || getProductSearch().equals("")) && getFromSubmitDate() == null && getToSubmitDate() == null) {
            filteredQuotes = quoteList;
        } else if ((getProductSearch() != null && !getProductSearch().equals("")) && getFromSubmitDate() == null && getToSubmitDate() == null){
            filteredQuotes = quoteService.findByProductName(getProductSearch());
        } else if ((getProductSearch() != null && !getProductSearch().equals("")) && (getFromSubmitDate() != null || getToSubmitDate() != null)) {
            filteredQuotes = quoteService.findByProductNameAndSubmitDate(getProductSearch(), getFromSubmitDate(), getToSubmitDate());
        } else if ((getProductSearch() == null || getProductSearch().equals("")) && (getFromSubmitDate() != null || getToSubmitDate() != null)) {
            filteredQuotes = quoteService.findBySubmitDate(getFromSubmitDate(), getToSubmitDate());
        }
    }

    public void loadQuoteList() {
        this.quoteList = quoteService.listQuote();
    }

    public void updateQuote() {
        quoteService.save(quote);
    }

    public void deleteQuote() {
        quoteService.delete(quote);
        loadQuoteList();
    }


    public QuoteRepository getQuoteRepository() {
        return quoteRepository;
    }

    public void setQuoteRepository(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public StatusCodesRepository getStatusCodesRepository() {
        return statusCodesRepository;
    }

    public void setStatusCodesRepository(StatusCodesRepository statusCodesRepository) {
        this.statusCodesRepository = statusCodesRepository;
    }

    public CustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Tax> getAllTaxBrackets() {
        if (allTaxBrackets.size() == 0) {
            allTaxBrackets = taxRepository.getAll();
        }
        return allTaxBrackets;
    }

    public void setAllTaxBrackets(List<Tax> allTaxBrackets) {
        this.allTaxBrackets = allTaxBrackets;
    }

    public TaxRepository getTaxRepository() {
        return taxRepository;
    }

    public void setTaxRepository(TaxRepository taxRepository) {
        this.taxRepository = taxRepository;
    }


    public List<Equipment> getAllEquipments() {
        if (allEquipments.size() == 0) {
            allEquipments = equipmentRepository.getAll();
        }
        return allEquipments;
    }

    public void setAllEquipments(List<Equipment> allEquipments) {
        this.allEquipments = allEquipments;
    }


    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        if (allProducts.size() == 0) {
            allProducts = productRepository.getAll();
        }
        return allProducts;
    }

    public void setAllProducts(List<Product> allProducts) {
        this.allProducts = allProducts;
    }


    public EquipmentRepository getEquipmentRepository() {
        return equipmentRepository;
    }

    public void setEquipmentRepository(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    public Product getProduct() {
        return productRepository.findOne(getProductId());
    }

    public Tax getTax() {
        return taxRepository.findOne(getTaxId());
    }

    public Equipment getEquipment() {
        return equipmentRepository.findOne(getEquipmentId());
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }

    public List<Quote> getQuoteList() {
        return quoteList;
    }

    public void setQuoteList(List<Quote> quoteList) {
        this.quoteList = quoteList;
    }

    public List<Quote> getFilteredQuotes() {
        return filteredQuotes;
    }

    public void setFilteredQuotes(List<Quote> filteredQuotes) {
        this.filteredQuotes = filteredQuotes;
    }

    public String getProductSearch() {
        return productSearch;
    }

    public void setProductSearch(String productSearch) {
        this.productSearch = productSearch;
    }

    public Date getToSubmitDate() {
        return toSubmitDate;
    }

    public void setToSubmitDate(Date toSubmitDate) {
        this.toSubmitDate = toSubmitDate;
    }

    public Date getFromSubmitDate() {
        return fromSubmitDate;
    }

    public void setFromSubmitDate(Date fromSubmitDate) {
        this.fromSubmitDate = fromSubmitDate;
    }

    public Tax getSelectedTax() {
        return selectedTax;
    }

    public void writeToCSV() {
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        DateFormat submitDateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();

        List<Quote> quotesToExport = null;
        if (getFilteredQuotes() != null) {

            quotesToExport =  getFilteredQuotes();
        } else {
            quotesToExport = getQuoteList();
        }
        List<String[]> data = new ArrayList<String[]>();
        data.add(new String[] {"ID", "Submit Date", "Product", "Equipment Type", "Amount", "Term", "Tax Bracket", "Customer"});
        for (int i=0; i<quotesToExport.size(); i++) {
            data.add(new String[] {quotesToExport.get(i).getId().toString(), submitDateFormatter.format(quotesToExport.get(i).getSubmitdate()), quotesToExport.get(i).getProduct().getName(), quotesToExport.get(i).getEquipment().getName(), quotesToExport.get(i).getAmount().toString(), quotesToExport.get(i).getTerm().toString(), quotesToExport.get(i).getTax().getDescription(), quotesToExport.get(i).getCustomer().getName()});
        }
        Configuration quotesCSVName = configurationRepository.findOne("flexi.csv.quotes");
        Util.writeToCSV(quotesCSVName.getValue() + dateFormatter.format(date) + ".csv", data);
    }


  /*  public StreamedContent download(String path) {
        File file = new File(path);
        InputStream input = new FileInputStream(file);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        return new DefaultStreamedContent(input, externalContext.getMimeType(file.getName()), file.getName());
    }*/





    /*Query query = session.createQuery("from Stock where stockCode = :code ");
    query.setParameter("code", "7277");
    List list = query.list();*/
}
