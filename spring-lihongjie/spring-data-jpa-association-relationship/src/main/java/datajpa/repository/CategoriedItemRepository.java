package datajpa.repository;

import datajpa.domain.domain4.CategorizedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriedItemRepository extends JpaRepository<CategorizedItem, Long> {


}
