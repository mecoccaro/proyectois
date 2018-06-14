package ucab.ingsw.proyecto.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import ucab.ingsw.proyecto.command.*;
import ucab.ingsw.proyecto.model.Album;
import ucab.ingsw.proyecto.model.Media;
import ucab.ingsw.proyecto.repository.MediaRepository;
import ucab.ingsw.proyecto.response.AccountsResponse;
import ucab.ingsw.proyecto.response.AlertsResponse;
import ucab.ingsw.proyecto.response.AlbumResponse;
import ucab.ingsw.proyecto.repository.FriendsRepository;
import ucab.ingsw.proyecto.repository.AlbumRepository;
import ucab.ingsw.proyecto.model.Friends;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucab.ingsw.proyecto.model.Accounts;
import ucab.ingsw.proyecto.repository.AccountsRepository;
import ucab.ingsw.proyecto.response.AccountsResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Slf4j
@Service("mediaervice")
public class MediaService {

    @Autowired
    MediaRepository mediaRepository;

    @Autowired
    AlbumRepository albumRepository;

    private Album buildMedia(Media media, String id){
        Album album = new Album();
        album = searchAlbumsById(id);
        album.getMedia().add(media.getId());


        return album;
    }

    private Media builtMedias(MediaCreateCommand command, String id){
        Media media =new Media();
        media.setId(System.currentTimeMillis());
        media.setAlbumId(Long.parseLong(id));
        media.setUrl(command.getUrl());
        media.setType(command.getType());

        return media;
    }

    private Album searchAlbumsById(String id){
        if(albumRepository.findById(Long.parseLong(id)).isPresent()){
            Album album = albumRepository.findById(Long.parseLong(id)).get();
            return album;
        }
        else
            return null;
    }

    private AlertsResponse buildAlert(String message) {
        AlertsResponse response = new AlertsResponse();
        response.setMessage(message);
        return response;
    }
    public ResponseEntity<Object> createMedia(MediaCreateCommand command, String id){
        log.debug("About to process [{}]", command);

        if (!albumRepository.existsById(Long.parseLong(id))) {
            log.info("Cannot find album ID={}");

            return ResponseEntity.badRequest().body(buildAlert("invalid"));
        } else {
            Media media = builtMedias(command, id);
            Album album = buildMedia(media,id);
            album = albumRepository.save(album);
            media = mediaRepository.save(media);



            log.info("media added ID={}", command);

            return ResponseEntity.ok().body(buildAlert("Media anadido"));

        }
    }


}
