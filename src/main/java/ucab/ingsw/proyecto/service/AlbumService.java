package ucab.ingsw.proyecto.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ucab.ingsw.proyecto.command.AlbumCreateCommand;
import ucab.ingsw.proyecto.command.AlbumDeleteCommand;
import ucab.ingsw.proyecto.model.Accounts;
import ucab.ingsw.proyecto.model.Album;
import ucab.ingsw.proyecto.repository.AccountsRepository;
import ucab.ingsw.proyecto.repository.AlbumRepository;
import ucab.ingsw.proyecto.repository.MediaRepository;
import ucab.ingsw.proyecto.response.AlbumResponse;
import ucab.ingsw.proyecto.response.AlertsResponse;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("albumService")
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountService accountService;

    private Album newAlbum(AlbumCreateCommand command){
        Album album = new Album();
        album.setId(System.currentTimeMillis());
        album.setUserId(Long.parseLong(command.getUserId()));
        album.setTitle(command.getTitle());
        album.setDescription(command.getDescription());
        return album;
    }

    public List<AlbumResponse> createAlbumList(Accounts accounts){
        List<AlbumResponse> albumResponseList = new ArrayList<>();
        List<Long> albumIds = accounts.getAlbum();
        albumRepository.findAll().forEach(it->{
            if (albumIds.stream().anyMatch(item -> item == it.getId())){
                AlbumResponse albumResponse = new AlbumResponse();
                albumResponse.setId(it.getId());
                albumResponse.setTitle(it.getTitle());
                albumResponse.setDescription(it.getDescription());
                albumResponse.setMedia(it.getMedia());
                albumResponseList.add(albumResponse);
            }
        });
        return albumResponseList;
    }

    private AlertsResponse alertsResponse(String message){
        AlertsResponse response = new AlertsResponse();
        response.setMessage(message);
        return response;
    }

    public ResponseEntity<Object> getAlbumById(String id){
        if(albumRepository.existsById(Long.parseLong(id))){
            Album album = albumRepository.findById(Long.parseLong(id)).get();
            AlbumResponse albumResponse = new AlbumResponse();
            albumResponse.setId(album.getId());
            albumResponse.setTitle(album.getTitle());
            albumRepository.setDescription(album.getDescription());
            albumResponse.setMedia(album.getMedia());
            log.info("Album info id={}", id);
            return ResponseEntity.ok(albumResponse);
        }
        else{
            log.info("Album with id={} doesnt exist", id);
            return ResponseEntity.badRequest().body(alertsResponse("invalid_album_id"));
        }
    }

    public ResponseEntity<Object> getAlbumList(String id){
        Accounts accounts = accountService.searchAccountsById(id);
        if(accounts != null){
            List<AlbumResponse> albumResponseList = createAlbumList(accounts);
            if (albumResponseList.isEmpty()){
                log.info("User ={} album is empty", id);
                return ResponseEntity.ok().body(alertsResponse("empty_list"));
            }
            else {
                log.info("Album list of the user id={}",id);
                return ResponseEntity.ok(albumResponseList);
            }
        }
        else {
            log.info("id={} doesnt exist",id);
            return ResponseEntity.badRequest().body(alertsResponse("invalid_id"));
        }
    }

    public ResponseEntity<Object> addAlbum(AlbumCreateCommand command){
        Accounts accounts = accountService.searchAccountsById(command.getAccountId());
        if(accounts != null){
            List<AlbumResponse> albumResponseList = createAlbumList(accounts);
            List<Long> albumList = accounts.getAlbum();
            if (albumList.stream().anyMatch(it -> it.getTitle().equals(command.getTitle()))){
                log.info("Album title ={} is on the list", command.getTitle());
                return ResponseEntity.badRequest().body(alertsResponse("album_title_exist"));
            }
            else{
                Album album = newAlbum(command);
                boolean choice = albumList.add(album.getId());
                if (choice){
                    log.info("Album ={} added to the list of the account ={} ",album.getId(), accounts.getId());
                    accounts.setAlbum(albumList);
                    accountsRepository.save(accounts);
                    albumRepository.save(album);
                    return ResponseEntity.ok().body(alertsResponse("album_added"));
                }
                else{
                    log.error("Error adding the album ={} to the list of the account ={}", album.getId(), accounts.getId());
                    return ResponseEntity.badRequest().body(alertsResponse("album_not_added"));
                }
            }
        }
        else {
            log.info("The user with the name={} doesnt exist",command.getUserId());
            return ResponseEntity.badRequest().body(alertsResponse("invalid_user"));
        }
    }

    public ResponseEntity<Object> deleteAlbum(AlbumDeleteCommand command){
        Accounts accounts = accountService.searchAccountsById(command.getAccountId());

        if(accounts == null){
            log.info("Cant find the user with the name={}", command.getAccountId());
            return ResponseEntity.badRequest().body(alertsResponse("invalid_user."));
        }

        else if(!(albumRepository.existsById(Long.parseLong(command.getAlbumId())))){
            log.info("Cant find the album with the id={}", command.getAlbumId());
            return ResponseEntity.badRequest().body(alertsResponse("invalid_album."));
        }

        else if(!(user.getAlbums().stream().anyMatch(i-> i == Long.parseLong(command.getAlbumId())))){
            log.info("The album ={} is not on the list of the account ={}", command.getAlbumId(), command.getAccountId());
            return ResponseEntity.badRequest().body(alertsResponse("album_not_on_list"));
        }

        else{
            Album album = albumRepository.findById(Long.parseLong(command.getAlbumId())).get();
            album.getMedia().forEach(it->{
                mediaRepository.deleteById(it);
            });

            if(accounts.getAlbum().remove(Long.parseLong(command.getAlbumId()))){
                log.info("Album ={} deleted from the account ={}", command.getAlbumId(), accounts.getId());
                accountsRepository.save(accounts);
                albumRepository.deleteById(Long.parseLong(command.getAlbumId()));
                return ResponseEntity.ok().body(alertsResponse("album_deleted"));
            }

            else{
                log.error("Error deleting the album ={} from the account ={}", command.getAlbumId(), accounts.getId());
                return ResponseEntity.badRequest().body(alertsResponse("error_deleting_album"));
            }

        }

    }

}
