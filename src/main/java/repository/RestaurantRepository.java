package repository;

import model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called alertRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, String> {

}
