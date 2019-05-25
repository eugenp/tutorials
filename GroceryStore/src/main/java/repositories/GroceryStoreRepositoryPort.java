package repositories;

import entities.GroceryCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroceryStoreRepositoryPort extends CrudRepository<GroceryCart, Integer> {

}
