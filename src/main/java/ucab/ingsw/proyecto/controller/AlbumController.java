package ucab.ingsw.proyecto.controller;

import ucab.ingsw.proyecto.command.AccountUpdateCommand;
import ucab.ingsw.proyecto.service.AccountService;
import ucab.ingsw.proyecto.response.AccountsResponse;
import ucab.ingsw.proyecto.command.AccountSignUpCommand;
import ucab.ingsw.proyecto.command.AccountLogInCommand;
import ucab.ingsw.proyecto.command.AccountAddFriendCommand;
import ucab.ingsw.proyecto.command.AlbumCreateCommand;
import ucab.ingsw.proyecto.service.AlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@CrossOrigin
@Slf4j
@RestController
@RequestMapping(value="/album", produces = "application/json")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

//    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
//    public ResponseEntity getAlbumById(@PathVariable("id") String id){
//        return albumService.getAlbumById(id);
//    }
//
//    @RequestMapping(value = "/getAlbumList/{id}", method = RequestMethod.GET)
//    public ResponseEntity getAlbumList(@PathVariable("id") String id){
//        return albumService.getAlbumList(id);
//    }

    @RequestMapping(value = "/create/{id}", consumes = "application/json", method = RequestMethod.PUT)
    public ResponseEntity createAlbum(@Valid @RequestBody AlbumCreateCommand command,@PathVariable("id") String id) {
        return albumService.addAlbum(command, id);
    }
}
