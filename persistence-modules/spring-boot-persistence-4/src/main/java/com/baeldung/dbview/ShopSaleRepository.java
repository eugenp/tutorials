package com.baeldung.dbview;

import java.util.List;

public interface ShopSaleRepository extends ViewRepository<ShopSale, ShopSaleCompositeId> {

    List<ShopSale> findByIdShopId(Integer shopId);

}