package ucab.ingsw.proyecto.controller;

import ucab.ingsw.proyecto.service.MediaService;
import ucab.ingsw.proyecto.command.MediaCreateCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value="/media", produces = "application/json")
public class MediaController {

    @Autowired
    MediaService mediaService;

    @RequestMapping(value = "/create/{id}", consumes = "application/json", method = RequestMethod.PUT)
    public ResponseEntity createMedia(@Valid @RequestBody MediaCreateCommand command, @PathVariable("id") String id){
        return mediaService.createMedia(command, id);
    }
}
