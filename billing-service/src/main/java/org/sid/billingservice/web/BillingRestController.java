package org.sid.billingservice.web;

import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.feign.CustomerRestClient;
import org.sid.billingservice.feign.ProductItemRestClient;
import org.sid.billingservice.model.Customer;
import org.sid.billingservice.model.Product;
import org.sid.billingservice.repositories.BillRepository;
import org.sid.billingservice.repositories.ProductItemRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillingRestController {
    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductItemRestClient productItemRestClient;
    
    public BillingRestController(BillRepository billRepository ,
                                 ProductItemRepository productItemRepository,
                                CustomerRestClient customerRestClient,
                                ProductItemRestClient productItemRestClient) {

        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productItemRestClient = productItemRestClient;
    }


    @GetMapping(path = "/fullBill/{id}")
    public Bill getBillById(@PathVariable(name = "id") Long id) {
        Bill bill =  billRepository.findById(id).get();
        Customer customer = customerRestClient.getCustomerById(bill.getCustomerId());
        bill.setCustomer(customer);
        bill.getProductItems().forEach(pi -> {
            Product product = productItemRestClient.getProductById(pi.getProductId());
            pi.setProductName(product.getName());
        });
        return bill;

    }

}
