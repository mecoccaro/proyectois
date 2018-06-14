package ucab.ingsw.proyecto.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucab.ingsw.proyecto.command.AlbumCreateCommand;
import ucab.ingsw.proyecto.command.AlbumDeleteCommand;
import ucab.ingsw.proyecto.service.AlbumService;
import ucab.ingsw.proyecto.repository.AlbumRepository;


import javax.validation.Valid;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value = "/album", produces = "application/json")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity getAlbumById(@PathVariable("id") String id){
        return albumService.getAlbumById(id);
    }

    @RequestMapping(value = "/getAlbumList/{id}", method = RequestMethod.GET)
    public ResponseEntity getAlbumList(@PathVariable("id") String id){
        return albumService.getAlbumList(id);
    }

    @RequestMapping(value = "/create", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity createAlbum(@Valid @RequestBody AlbumCreateCommand command){
        return albumService.addAlbum(command);
    }

    @RequestMapping(value = "/delete", consumes = "application/json", method = RequestMethod.DELETE)
    public ResponseEntity deleteAlbum(@Valid @RequestBody AlbumDeleteCommand command){
        return albumService.deleteAlbum(command);
    }

}
