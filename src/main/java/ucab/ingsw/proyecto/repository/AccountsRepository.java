package ucab.ingsw.proyecto.repository;

import ucab.ingsw.proyecto.model.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsRepository  extends CrudRepository<Accounts, Long>{

    List<Accounts> findFirst3ByEmailIgnoreCaseContaining(String partialEmailAdress);
    boolean  existsByEmail(String email);
    Accounts findByEmail(String email);

}
