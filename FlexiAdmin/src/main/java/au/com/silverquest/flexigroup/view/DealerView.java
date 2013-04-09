package au.com.silverquest.flexigroup.view;

import au.com.silverquest.flexigroup.model.entity.Content;
import au.com.silverquest.flexigroup.model.entity.ContentType;
import au.com.silverquest.flexigroup.model.entity.Dealer;
import au.com.silverquest.flexigroup.model.entity.SalesStaff;
import au.com.silverquest.flexigroup.services.ContentService;
import au.com.silverquest.flexigroup.services.SalesStaffService;
import au.com.silverquest.flexigroup.util.Base64;
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
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
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
@Component("dealerManager")
@Scope("view")
public class DealerView implements Serializable {

    private Dealer dealer;
    private List<Dealer> dealerList;
    private List<Dealer> filteredDealerList;
    private String selectedDealer;
    private String name;
    private String email;
    private String phone;

    @Autowired
    private SalesStaffView salesStaffView;


    @Autowired
    private SalesStaffService salesStaffService;

    @PostConstruct
    public void init() {
        dealer = new Dealer();
        dealerList = new ArrayList<Dealer>();
        loadDealerList();
    }

    public void loadDealerList() {
        dealerList = salesStaffService.listDealer();
        salesStaffView.setDealerList(dealerList);
    }


    public void save(ActionEvent actionEvent) {
        //dealer = salesStaffService.getDealer(getSelectedDealer());
        dealer = new Dealer();
        dealer.setUuid(UUID.randomUUID());
        dealer.setName(getName());
        dealer.setEmail(getEmail());
        dealer.setPhone(getPhone());
        salesStaffService.saveDealer(dealer);

        name = null;
        email = null;
        phone = null;

        loadDealerList();
       // salesStaffView.setDealerList(dealerList);
    }

    public void updateDealer() {
        salesStaffService.saveDealer(dealer);
        loadDealerList();
    }

    public void deleteDealer() {
        salesStaffService.deleteDealer(dealer);
        loadDealerList();
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

    public List<Dealer> getFilteredDealerList() {
        return filteredDealerList;
    }

    public void setFilteredDealerList(List<Dealer> filteredDealerList) {
        this.filteredDealerList = filteredDealerList;
        //salesStaffView.setFilteredSalesStaffList(salesStaffService.listSalesStaffByDealer(filteredDealerList));
    }

    public String getSelectedDealer() {
        return selectedDealer;
    }

    public void setSelectedDealer(String selectedDealer) {
        this.selectedDealer = selectedDealer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}