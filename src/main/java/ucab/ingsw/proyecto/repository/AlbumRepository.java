package ucab.ingsw.proyecto.repository;

import ucab.ingsw.proyecto.model.Album;
import ucab.ingsw.proyecto.controller.AlbumController;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Long> {

    List<Album> findAlbumByTitulo(String partialTitulo);
    boolean  existsByTitulo(String titulo);
    Album findByTitulo(String titulo);

}
