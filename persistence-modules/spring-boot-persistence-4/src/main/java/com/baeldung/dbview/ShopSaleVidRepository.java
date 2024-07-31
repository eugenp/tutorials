package com.baeldung.dbview;

import java.util.List;

public interface ShopSaleVidRepository extends ViewNoIdRepository<ShopSaleVid, Long> {

    List<ShopSaleVid> findByShopId(Integer shopId);

}