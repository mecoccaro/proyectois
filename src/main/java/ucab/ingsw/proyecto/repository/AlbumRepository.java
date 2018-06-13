package ucab.ingsw.proyecto.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucab.ingsw.proyecto.model.Album;

import java.util.List;

@Repository("albumRepository")
public interface AlbumRepository extends CrudRepository<Album, Long> {
    List<Album> findByNameIgnoreCaseContaining(String titulo);
}
