package datajpa.repository;

import datajpa.domain.domain3.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {


}
