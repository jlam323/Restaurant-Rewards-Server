package repository;

import model.Points;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called alertRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface PointsRepository extends CrudRepository<Points, String> {

}
