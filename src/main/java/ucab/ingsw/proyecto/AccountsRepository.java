package ucab.ingsw.proyecto;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository("accountsRepository")
public interface AccountsRepository  extends CrudRepository<Accounts, Long>{

    List<Accounts> findFirst3ByEmailIgnoreCaseContaining(String partialEmailAdress);
}
