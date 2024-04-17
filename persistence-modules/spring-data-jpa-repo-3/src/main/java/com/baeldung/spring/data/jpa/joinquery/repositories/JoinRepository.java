package com.baeldung.spring.data.jpa.joinquery.repositories;

import com.baeldung.spring.data.jpa.joinquery.DTO.ResultDTO;
import com.baeldung.spring.data.jpa.joinquery.DTO.ResultDTO_wo_Ids;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public interface JoinRepository extends CrudRepository<ResultDTO, Long> {
    @Query(value = "SELECT new com.baeldung.spring.data.jpa.joinquery.DTO.ResultDTO(c.id, o.id, p.id, c.name, c.email, o.orderDate, p.productName, p.price) "
      + " from Customer c, CustomerOrder o ,Product p "
      + " where c.id=o.customer.id "
      + " and o.id=p.customerOrder.id "
      + " and c.id=?1 ")
    List<ResultDTO> findResultDTOByCustomer(Long id);

    @Query(value = "SELECT new com.baeldung.spring.data.jpa.joinquery.DTO.ResultDTO_wo_Ids(c.name, c.email, o.orderDate, p.productName, p.price) "
      + " from Customer c, CustomerOrder o ,Product p "
      + " where c.id=o.customer.id "
      + " and o.id=p.customerOrder.id "
      + " and c.id=?1 ")
    List<ResultDTO_wo_Ids> findResultDTOByCustomerWithoutIds(Long id);

    @Query(value = "SELECT c.*, o.*, p.* "
      + " from Customer c, CustomerOrder o ,Product p "
      + " where c.id=o.customer_id "
      + " and o.id=p.customerOrder_id "
      + " and c.id=?1 "
    , nativeQuery = true)
    List<Map<String, Object>> findByCustomer(Long id);
}
