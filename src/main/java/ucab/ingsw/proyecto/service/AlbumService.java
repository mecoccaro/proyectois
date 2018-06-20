package ucab.ingsw.proyecto.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import ucab.ingsw.proyecto.command.AlbumCreateCommand;
import ucab.ingsw.proyecto.model.Album;
import ucab.ingsw.proyecto.response.AlertsResponse;
import ucab.ingsw.proyecto.repository.AlbumRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucab.ingsw.proyecto.model.Accounts;
import ucab.ingsw.proyecto.repository.AccountsRepository;

@CrossOrigin
@Slf4j
@Service("albumService")
public class AlbumService {

    @Autowired
    AccountsRepository accountsRepository;

    @Autowired
    AlbumRepository albumRepository;

    private Accounts buildAlbum(Album album, String id){
        Accounts accounts = new Accounts();
        accounts = searchAccountsById(id);
        accounts.getAlbums().add(album.getId());


        return accounts;
    }

    private Album buildAlbumes(AlbumCreateCommand command, String id){
        Album album = new Album();
        album.setId(System.currentTimeMillis());
        album.setAccountId(Long.parseLong(id));
        album.setTitle(command.getTitle());
        album.setDescription(command.getDescription());

        return album;

    }

    private Accounts searchAccountsById(String id){
        if(accountsRepository.findById(Long.parseLong(id)).isPresent()){
            Accounts accounts = accountsRepository.findById(Long.parseLong(id)).get();
            return accounts;
        }
        else
            return null;
    }

    private AlertsResponse buildAlert(String message) {
        AlertsResponse response = new AlertsResponse();
        response.setMessage(message);
        return response;
    }

    public ResponseEntity<Object> addAlbum(AlbumCreateCommand command, String id){
        log.debug("About to process [{}]", command);

        if (!accountsRepository.existsById(Long.parseLong(id))) {
            log.info("Cannot find user ID={}");

            return ResponseEntity.badRequest().body(buildAlert("invalid"));
        } else {
            Album album = buildAlbumes(command,id);
            Accounts accounts = buildAlbum(album,id);
            accounts = accountsRepository.save(accounts);
            album = albumRepository.save(album);



            log.info("Album added ID={}", command);

            return ResponseEntity.ok().body(buildAlert("Album anadido"));

        }
    }
}
