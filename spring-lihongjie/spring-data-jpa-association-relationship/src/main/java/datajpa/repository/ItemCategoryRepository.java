package datajpa.repository;

import datajpa.domain.domain4.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCategoryRepository extends JpaRepository<Category, Long> {


}
