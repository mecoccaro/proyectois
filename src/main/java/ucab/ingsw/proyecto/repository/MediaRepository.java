package ucab.ingsw.proyecto.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucab.ingsw.proyecto.model.Media;

@Repository("mediaRepository")
public interface MediaRepository extends CrudRepository<Media, Long> {
}
