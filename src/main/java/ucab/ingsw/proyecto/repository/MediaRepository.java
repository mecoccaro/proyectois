package ucab.ingsw.proyecto.repository;

import ucab.ingsw.proyecto.model.Media;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends CrudRepository<Media, Long> {

    List<Media> findMediaByAlbumId(String Id);
}
