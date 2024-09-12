package com.baeldung.dbview;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = DatabaseViewApplication.class, properties = {
        "spring.jpa.show-sql=false",
        "spring.jpa.properties.hibernate.format_sql=false",
        "spring.jpa.hibernate.ddl-auto=none",
        "spring.jpa.defer-datasource-initialization=true",
        "spring.sql.init.data-locations=classpath:shop-sale-data.sql"
})
class ShopSaleRepositoryLiveTest {

    private static final ShopSaleCompositeId id = ShopSaleCompositeId.builder()
            .shopId(1)
            .year(2024)
            .month(1)
            .build();

    @Autowired
    private ShopSaleRepository shopSaleRepository;

    @Test
    void whenCount_thenValueGreaterThanOne() {
        assertThat(shopSaleRepository.count()).isGreaterThan(0);
    }

    @Test
    void whenFindAll_thenReturnAllRecords() {
        assertThat(shopSaleRepository.findAll()).isNotEmpty();
    }

    @Test
    void whenExistsById_thenReturnTrue() {
        assertThat(shopSaleRepository.existsById(id)).isTrue();
    }

    @Test
    void whenFindAllById_thenReturnListWithTwoInstances() {
        assertThat(shopSaleRepository.findAllById(List.of (id))).hasSize(1);
    }

    @Test
    void whenFindById_thenReturnAnInstance() {
        assertThat(shopSaleRepository.findById(id).isPresent()).isTrue();
    }

    @Test
    void whenFindByShopId_thenReturnAllShopSaleOfThatShop() {
        var shopId = 1;
        List<ShopSale> shopSaleVidList = shopSaleRepository.findByIdShopId(shopId);
        assertThat(shopSaleVidList).isNotEmpty();
        shopSaleVidList.forEach(s -> assertThat(s.getId().getShopId()).isEqualTo(shopId));
    }




}