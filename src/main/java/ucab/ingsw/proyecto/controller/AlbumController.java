package ucab.ingsw.proyecto.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ucab.ingsw.proyecto.service.AlbumService;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = "/album", produces = "application/json")
public class AlbumController {
}
