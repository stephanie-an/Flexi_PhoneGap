package au.com.silverquest.flexigroup.view;


import au.com.silverquest.flexigroup.model.entity.Dealer;
import au.com.silverquest.flexigroup.model.entity.SalesStaff;
import au.com.silverquest.flexigroup.services.SalesStaffService;
import au.com.silverquest.flexigroup.util.Util;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: Stephanie
 * Date: 21/02/13
 * Time: 12:22 AM
 * To change this template use File | Settings | File Templates.
 */
@Component("salesStaffManager")
@Scope("view")
public class SalesStaffView implements Serializable {

    private SalesStaff salesStaff;
    private List<SalesStaff> salesStaffList;
    private List<SalesStaff> filteredSalesStaffList;
    private Dealer dealer;
    private List<Dealer> dealerList;
    private Integer selectedDealer;


    private String firstname;
    private String lastname;
    private String email;
    private String phone;

    @Autowired
    private SalesStaffService salesStaffService;

    @PostConstruct
    public void init() {
        salesStaff = new SalesStaff();
        salesStaffList = new ArrayList<SalesStaff>();
        dealerList = new ArrayList<Dealer>();
        loadSalesStaffList();
    }


    public void loadSalesStaffList() {
        salesStaffList = salesStaffService.listSalesStaff();
    }

    public void createSalesStaff(ActionEvent actionEvent) {
        salesStaff = new SalesStaff();
        dealer = getDealer();
        salesStaff.setDealer(dealer);
        salesStaff.setUuid(UUID.randomUUID());
        salesStaff.setFirstname(getFirstname());
        salesStaff.setLastname(getLastname());
        salesStaff.setEmail(getEmail());
        salesStaff.setPhone(getPhone());
        salesStaffService.save(salesStaff);

        firstname = null;
        lastname = null;
        email = null;
        loadSalesStaffList();
    }

    public void updateSalesStaff() {
        salesStaffService.save(salesStaff);
        loadSalesStaffList();
    }

    public void deleteSalesStaff() {
        salesStaffService.delete(salesStaff);
        loadSalesStaffList();
    }


    public SalesStaff getSalesStaff() {
        return salesStaff;
    }

    public void setSalesStaff(SalesStaff salesStaff) {
        this.salesStaff = salesStaff;
    }

    public List<SalesStaff> getSalesStaffList() {
        return salesStaffList;
    }

    public void setSalesStaffList(List<SalesStaff> salesStaffList) {
        this.salesStaffList = salesStaffList;
    }

    public List<SalesStaff> getFilteredSalesStaffList() {
        return filteredSalesStaffList;
    }

    public void setFilteredSalesStaffList(List<SalesStaff> filteredSalesStaffList) {
        this.filteredSalesStaffList = filteredSalesStaffList;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }

    public List<Dealer> getDealerList() {
        return dealerList;
    }

    public void setDealerList(List<Dealer> dealerList) {
        this.dealerList = dealerList;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSelectedDealer() {
        return selectedDealer;
    }

    public void setSelectedDealer(Integer id) {
        this.dealer = salesStaffService.getDealer(id);
    }
}