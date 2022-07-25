package com.baeldung.boot.unique.field;

import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.boot.unique.field.dao.AssetRepository;
import com.baeldung.boot.unique.field.dao.CompanyRepository;
import com.baeldung.boot.unique.field.dao.CustomerRepository;
import com.baeldung.boot.unique.field.dao.SaleRepository;
import com.baeldung.boot.unique.field.data.Asset;
import com.baeldung.boot.unique.field.data.Company;
import com.baeldung.boot.unique.field.data.Customer;
import com.baeldung.boot.unique.field.data.Sale;
import com.baeldung.boot.unique.field.data.SaleId;

@SpringBootTest
@DirtiesContext
@RunWith(SpringRunner.class)
public class UniqueFieldIntegrationTest {
    @Autowired
    private SaleRepository saleRepo;

    @Autowired
    private CompanyRepository companyRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private AssetRepository assetRepo;

    @Test
    public void givenMultipleIndexes_whenAnyFieldDupe_thenExceptionIsThrown() {
        Asset a = new Asset();
        a.setName("Name");
        a.setNumber(1);

        assetRepo.insert(a);

        Asset b = new Asset();
        b.setName("Name");
        b.setNumber(2);
        assertThrows(DuplicateKeyException.class, () -> {
            assetRepo.insert(b);
        });

        Asset c = new Asset();
        c.setName("Other");
        c.setNumber(1);
        assertThrows(DuplicateKeyException.class, () -> {
            assetRepo.insert(c);
        });
    }

    @Test
    public void givenUniqueIndex_whenInsertingDupe_thenExceptionIsThrown() {
        Company a = new Company();
        a.setName("Name");
        a.setEmail("a@mail.com");

        companyRepo.insert(a);

        Company b = new Company();
        b.setName("Other");
        b.setEmail("a@mail.com");
        assertThrows(DuplicateKeyException.class, () -> {
            companyRepo.insert(b);
        });
    }

    @Test
    public void givenCompoundIndex_whenDupeInsert_thenExceptionIsThrown() {
        Customer customerA = new Customer("Name A");
        customerA.setNumber(1l);
        customerA.setStoreId(2l);

        Customer customerB = new Customer("Name B");
        customerB.setNumber(1l);
        customerB.setStoreId(2l);

        customerRepo.insert(customerA);

        assertThrows(DuplicateKeyException.class, () -> {
            customerRepo.insert(customerB);
        });
    }

    @Test
    public void givenCustomTypeIndex_whenInsertingDupe_thenExceptionIsThrown() {
        SaleId id = new SaleId();
        id.setDate("2022-06-15");
        id.setItem(1L);

        Sale a = new Sale(id);
        a.setValue(53.94);

        saleRepo.insert(a);

        Sale b = new Sale(id);
        b.setValue(100.00);
        assertThrows(DuplicateKeyException.class, () -> {
            saleRepo.insert(b);
        });
    }
}
