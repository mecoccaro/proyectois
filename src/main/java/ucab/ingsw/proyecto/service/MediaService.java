package ucab.ingsw.proyecto.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ucab.ingsw.proyecto.command.MediaCreateCommand;
import ucab.ingsw.proyecto.command.MediaDeleteCommand;
import ucab.ingsw.proyecto.model.Accounts;
import ucab.ingsw.proyecto.model.Album;
import ucab.ingsw.proyecto.model.Media;
import ucab.ingsw.proyecto.repository.AccountsRepository;
import ucab.ingsw.proyecto.repository.AlbumRepository;
import ucab.ingsw.proyecto.repository.MediaRepository;
import ucab.ingsw.proyecto.response.AlbumResponse;
import ucab.ingsw.proyecto.response.AlertsResponse;
import ucab.ingsw.proyecto.response.MediaResponse;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("mediaService")
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountsRepository accountsRepository;

    private Media newMedia(MediaCreateCommand command){
        Media media = new Media();
        media.setId(System.currentTimeMillis());
        media.setAlbumId(Long.parseLong(command.getAlbumId()));
        media.setUrl(command.getUrl());
        media.setType(command.getType());
        return media;
    }
    private Accounts searchAccountsById(String id){
        if(accountsRepository.findById(Long.parseLong(id)).isPresent()){
            Accounts accounts = accountsRepository.findById(Long.parseLong(id)).get();
            return accounts;
        }
        else
            return null;
    }

    private Media searchMediaById(String id){

        if(mediaRepository.existsById(Long.parseLong(id))) {
            Media media = mediaRepository.findById(Long.parseLong(id)).get();
            return media;
        }else return null;
    }

    private AlertsResponse alertsResponse(String message){
        AlertsResponse response = new AlertsResponse();
        response.setMessage(message);
        return response;
    }

    public List<MediaResponse> createMediaList(Album album){
        List<MediaResponse> mediaList = new ArrayList<>();
        List<Long> mediaIdList = album.getMedia();
        mediaRepository.findAll().forEach(it->{
            if(mediaIdList.stream().anyMatch(item->item == it.getId())){
                MediaResponse mediaResponse = new MediaResponse();
                mediaResponse.setId(it.getId());
                mediaResponse.setUrl(it.getUrl());
                mediaResponse.setType(it.getType());
                mediaList.add(mediaResponse);
            }
        });
        return mediaList;
    }

    public ResponseEntity<Object> getMediaList(String id){
        if(albumRepository.existsById(Long.parseLong(id))){
            Album album = albumRepository.findById(Long.parseLong(id)).get();
            List<MediaResponse> mediaResponseList = createMediaList(album);

            if(mediaResponseList.isEmpty()){
                log.info("Media list of the album ={} is empty", id);
                return ResponseEntity.ok().body(alertsResponse("no_medias"));
            }

            else {
                log.info("Medias of the album ={}", id);
                return ResponseEntity.ok(mediaResponseList);
            }

        }

        else {
            log.info("The album ={} doesnt exist", id);
            return ResponseEntity.badRequest().body(alertsResponse("album_id_not_exist"));
        }

    }

    public ResponseEntity<Object> getMediaId(String id){
        if(mediaRepository.existsById(Long.parseLong(id))){
            Media media = mediaRepository.findById(Long.parseLong(id)).get();
            MediaResponse mediaResponse = new MediaResponse();
            mediaResponse.setId(media.getId());
            mediaResponse.setUrl(media.getUrl());
            mediaResponse.setType(media.getType());
            log.info("Media id ={}", id);
            return ResponseEntity.ok(mediaResponse);
        }

        else {
            log.info("Media ={} doesnt exist", id);
            return ResponseEntity.badRequest().body(alertsResponse("Media_id_not_exist"));
        }

    }

    public ResponseEntity<Object> createMedia(MediaCreateCommand command){
        Accounts accounts = searchAccountsById(command.getAccountId());

        if(accounts == null){
            log.info("Cant find the account ={}", command.getAccountId());
            return ResponseEntity.badRequest().body(alertsResponse("invalid account"));
        }

        else if(!(albumRepository.existsById(Long.parseLong(command.getAlbumId())))){
            log.info("Cant find album ={}", command.getAlbumId());
            return ResponseEntity.badRequest().body(alertsResponse("invalid album Id."));
        }

        else if(!(accounts.getAlbums().stream().anyMatch(i-> i == Long.parseLong(command.getAlbumId())))){
            log.info("The album ={} is not on the account ={}", command.getAlbumId(), command.getAccountId());
            return ResponseEntity.badRequest().body(alertsResponse("album not on list"));
        }

        else{
            Media media = new Media();
            Album album = albumRepository.findById(Long.parseLong(command.getAlbumId())).get();
            boolean choice = album.getMedia().add(media.getId());

            if(choice) {
                log.info("Media ={} added to the album ={}", album.getId(), accounts.getId());
                albumRepository.save(album);
                mediaRepository.save(media);
                return ResponseEntity.ok().body(alertsResponse("media added"));
            }

            else{
                log.error("Error adding media ={} on the album ={}", album.getId(), accounts.getId());
                return ResponseEntity.badRequest().body(alertsResponse("error_adding_media"));
            }

        }

    }

    public ResponseEntity<Object> deleteMedia(MediaDeleteCommand command){
        Accounts accounts = searchAccountsById(command.getAccountId());

        if(accounts == null){
            log.info("Cant find the account ={}", command.getAccountId());
            return ResponseEntity.badRequest().body(alertsResponse("invalid account"));
        }

        else if(!(albumRepository.existsById(Long.parseLong(command.getAlbumId())))){
            log.info("Cant find the album ={}", command.getAlbumId());
            return ResponseEntity.badRequest().body(alertsResponse("invalid album"));
        }

        else if(!(accounts.getAlbums().stream().anyMatch(i-> i == Long.parseLong(command.getAlbumId())))){
            log.info("The album ={} is not on the account ={}", command.getAlbumId(), command.getAccountId());
            return ResponseEntity.badRequest().body(alertsResponse("album not on account"));
        }

        else if(!(mediaRepository.existsById(Long.parseLong(command.getMediaId())))){
            log.info("Cant find media ={}", command.getMediaId());
            return ResponseEntity.badRequest().body(alertsResponse("invalid media."));
        }

        else{
            Album album = albumRepository.findById(Long.parseLong(command.getAlbumId())).get();

            if(album.getMedia().stream().anyMatch(i-> i == Long.parseLong(command.getMediaId()))){
                if(album.getMedia().remove(Long.parseLong(command.getMediaId()))){
                    log.info("Media ={} deleted from the album ={}", command.getMediaId(), album.getId());
                    albumRepository.save(album);
                    mediaRepository.deleteById(Long.parseLong(command.getMediaId()));
                    return ResponseEntity.ok().body(alertsResponse("media_deleted"));
                }

                else{
                    log.error("Error deleting media ={} from the album ={}", command.getMediaId(), album.getId());
                    return ResponseEntity.badRequest().body(alertsResponse("error_deleting_media"));
                }

            }

            else{
                log.info("Media ={} is not on the album ={}", command.getAlbumId(), command.getMediaId());
                return ResponseEntity.badRequest().body(alertsResponse("media_not_on_album"));
            }

        }

    }

}
