package ucab.ingsw.proyecto.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsRepository extends CrudRepository<Friends,Long> {

    List<Friends> findFriendsById(String partialId);
    boolean existsFriendsById(String Id);
}
