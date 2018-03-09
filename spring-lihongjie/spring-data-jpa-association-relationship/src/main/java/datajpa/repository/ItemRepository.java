package datajpa.repository;

import datajpa.domain.domain4.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {


}
