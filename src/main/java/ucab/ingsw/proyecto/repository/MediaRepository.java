package ucab.ingsw.proyecto.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucab.ingsw.proyecto.model.Media;

import java.util.List;

@Repository
public interface MediaRepository extends CrudRepository<Media, Long> {
    List<Media> findByNameIgnoreCaseContaining(String titulo);
}
