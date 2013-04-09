package au.com.silverquest.flexigroup.services;

import au.com.silverquest.flexigroup.model.entity.Dealer;
import au.com.silverquest.flexigroup.model.entity.SalesStaff;
import au.com.silverquest.flexigroup.model.repository.DealerRepository;
import au.com.silverquest.flexigroup.model.repository.SalesStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stephanie
 * Date: 8/03/13
 * Time: 10:17 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("salesStaffService")
public class SalesStaffServiceImpl implements SalesStaffService {

    @Autowired
    private SalesStaffRepository salesStaffRepository;

    @Autowired
    private DealerRepository dealerRepository;

    public List<SalesStaff> listSalesStaff() {
        return salesStaffRepository.getAll();
    }
    public List<Dealer> listDealer() {
        return dealerRepository.getAll();
    }
    public Dealer getDealer(Integer id) {
        return dealerRepository.findOne(id);
    }

    public void save(SalesStaff salesStaff) {
        salesStaffRepository.save(salesStaff);
    }
    public void delete(SalesStaff salesStaff) {
        salesStaffRepository.delete(salesStaff);
    }

    public void saveDealer(Dealer dealer) {
        dealerRepository.save(dealer);
    }

    public void deleteDealer(Dealer dealer) {
        dealerRepository.delete(dealer);
    }

    /*public List<SalesStaff> listSalesStaffByDealer(List<Dealer> dealers) {
        return salesStaffRepository.getSalesStaffByDealer(dealers.get(0).getId());
    }*/

}
