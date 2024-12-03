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
class ShopSaleVidRepositoryLiveTest {

    @Autowired
    private ShopSaleVidRepository shopSaleVidRepository;

    @Test
    void whenCount_thenValueGreaterThanOne() {
        assertThat(shopSaleVidRepository.count()).isGreaterThan(0);
    }

    @Test
    void whenFindAll_thenReturnAllRecords() {
        assertThat(shopSaleVidRepository.findAll()).isNotEmpty();
    }

    @Test
    void whenFindByShopId_thenReturnAllShopSaleOfThatShop() {
        var shopId = 1;
        List<ShopSaleVid> shopSaleList = shopSaleVidRepository.findByShopId(shopId);
        assertThat(shopSaleList).isNotEmpty();
        shopSaleList.forEach(s -> assertThat(s.getShopId()).isEqualTo(shopId));
    }

}