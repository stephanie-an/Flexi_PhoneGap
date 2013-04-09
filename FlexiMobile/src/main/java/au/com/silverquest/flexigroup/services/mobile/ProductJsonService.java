package au.com.silverquest.flexigroup.services.mobile;

/**
 * Created with IntelliJ IDEA.
 * User: coreyb
 * Date: 22/02/13
 * Time: 9:18 PM
 * To change this template use File | Settings | File Templates.
 */

import au.com.silverquest.flexigroup.model.entity.Product;
import au.com.silverquest.flexigroup.model.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@Controller
public class ProductJsonService {

    final Logger log = LoggerFactory.getLogger(ProductJsonService.class);

    @Inject
    private ProductRepository productRepository;

    @RequestMapping(value = "/product", method = RequestMethod.GET, headers = "Accept=application/json")
    public
    @ResponseBody
    List<Product> getProducts(@RequestParam(value = "lastmodified", required = false, defaultValue = "0") Long lastmodified) {
        log.debug("getProduct() service call received: " + lastmodified);
        List<Product> products = productRepository.getAll(new Date(lastmodified));
        log.debug("Returning results: " + products.size());
        return products;
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET, headers = "Accept=application/json")
    public
    @ResponseBody
    Product getProductsById(@PathVariable Integer id) {
        log.debug("getProductById() service call received for: " + id);
        Product product = productRepository.findOne(id);
        log.debug("Returning results: " + product.getName());
        return product;
    }
}
