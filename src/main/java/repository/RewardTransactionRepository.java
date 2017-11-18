package repository;

import model.RewardTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called alertRepository
// CRUD refers Create, Read, Update, Delete

@Repository
public interface RewardTransactionRepository extends CrudRepository<RewardTransaction, String> {

}
