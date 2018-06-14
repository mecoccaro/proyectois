package ucab.ingsw.proyecto.repository;

import ucab.ingsw.proyecto.model.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ucab.ingsw.proyecto.model.Album;

import java.util.List;

public interface AlbumRepository extends CrudRepository<Album, Long>{

    List<Album> findAlbumById(String partialAlbum);
}
