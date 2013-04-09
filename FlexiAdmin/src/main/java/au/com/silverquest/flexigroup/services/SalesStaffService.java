package au.com.silverquest.flexigroup.services;

import au.com.silverquest.flexigroup.model.entity.Dealer;
import au.com.silverquest.flexigroup.model.entity.SalesStaff;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stephanie
 * Date: 8/03/13
 * Time: 10:16 PM
 * To change this template use File | Settings | File Templates.
 */
public interface SalesStaffService {

    public void save(SalesStaff salesStaff);
    public void delete(SalesStaff salesStaff);
    public List<SalesStaff> listSalesStaff();
    //public List<SalesStaff> listSalesStaffByDealer(List<Dealer> dealers);

    public Dealer getDealer(Integer id);
    public void saveDealer(Dealer dealer);
    public void deleteDealer(Dealer dealer);
    public List<Dealer> listDealer();
}
