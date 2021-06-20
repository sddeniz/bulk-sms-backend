package UserAuth;

import entity.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TokenConfirmRepository extends CrudRepository<Token, String> {

}
