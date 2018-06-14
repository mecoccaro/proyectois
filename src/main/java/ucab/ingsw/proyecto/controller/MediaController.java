package ucab.ingsw.proyecto.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ucab.ingsw.proyecto.command.MediaCreateCommand;
import ucab.ingsw.proyecto.command.MediaDeleteCommand;
import ucab.ingsw.proyecto.model.Media;
import ucab.ingsw.proyecto.service.MediaService;

import javax.validation.Valid;

@Slf4j
@CrossOrigin
@RestController

@RequestMapping(value = "/media", produces = "application/json")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ResponseEntity getMediaById(@PathVariable("id") String id){
        return mediaService.getMediaId(id);
    }

    @RequestMapping(value = "/getMediaList/{id}", method = RequestMethod.GET)
    public ResponseEntity getMediaList(@PathVariable("id") String id){
        return mediaService.getMediaList(id);
    }

    @RequestMapping(value = "/create", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity createMedia(@Valid @RequestBody MediaCreateCommand command){
        return mediaService.createMedia(command);
    }

    @RequestMapping(value = "/delete", consumes = "application/json", method = RequestMethod.DELETE)
    public ResponseEntity deleteMedia(@Valid @RequestBody MediaDeleteCommand command){
        return mediaService.deleteMedia(command);
    }

}
