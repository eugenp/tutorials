package com.baeldung.boot.unique.field.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.boot.unique.field.dao.AssetRepository;
import com.baeldung.boot.unique.field.dao.CompanyRepository;
import com.baeldung.boot.unique.field.dao.Customer2Repository;
import com.baeldung.boot.unique.field.dao.CustomerRepository;
import com.baeldung.boot.unique.field.dao.SaleRepository;
import com.baeldung.boot.unique.field.data.Asset;
import com.baeldung.boot.unique.field.data.Company;
import com.baeldung.boot.unique.field.data.Customer;
import com.baeldung.boot.unique.field.data.Customer2;
import com.baeldung.boot.unique.field.data.Sale;
import com.baeldung.boot.unique.field.data.SaleId;

@RestController
@RequestMapping("/unique-field")
public class UniqueFieldController {
    @Autowired
    private SaleRepository saleRepo;

    @Autowired
    private CompanyRepository companyRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private Customer2Repository customer2Repo;

    @Autowired
    private AssetRepository assetRepo;

    @PostMapping("/sale")
    public Sale post(@RequestBody Sale sale) {
        return saleRepo.insert(sale);
    }

    @GetMapping("/sale")
    public Optional<Sale> getSale(SaleId id) {
        return saleRepo.findBySaleId(id);
    }

    @PostMapping("/company")
    public Company post(@RequestBody Company company) {
        return companyRepo.insert(company);
    }

    @PutMapping("/company")
    public Company put(@RequestBody Company company) {
        return companyRepo.save(company);
    }

    @GetMapping("/company/{id}")
    public Optional<Company> getCompany(@PathVariable String id) {
        return companyRepo.findById(id);
    }

    @PostMapping("/customer")
    public Customer post(@RequestBody Customer customer) {
        return customerRepo.insert(customer);
    }

    @PostMapping("/customer2")
    public Customer2 post(@RequestBody Customer2 customer) {
        return customer2Repo.insert(customer);
    }

    @GetMapping("/customer/{id}")
    public Optional<Customer> getCustomer(@PathVariable String id) {
        return customerRepo.findById(id);
    }

    @PostMapping("/asset")
    public Asset post(@RequestBody Asset asset) {
        return assetRepo.insert(asset);
    }

    @GetMapping("/asset/{id}")
    public Optional<Asset> getAsset(@PathVariable String id) {
        return assetRepo.findById(id);
    }
}
