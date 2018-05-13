package ucab.ingsw.proyecto.controller;

import ucab.ingsw.proyecto.service.AccountService;
import ucab.ingsw.proyecto.response.AccountsResponse;
import ucab.ingsw.proyecto.command.AccountSignUpCommand;
import ucab.ingsw.proyecto.command.AccountLogInCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value="/account", produces = "application/json")
public class AccountsController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/registration", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity register(@Valid @RequestBody AccountSignUpCommand command) {
        return accountService.register(command);
    }

    @RequestMapping(value = "/login", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity login(@Valid @RequestBody AccountLogInCommand command) {
        return accountService.login(command);
    }
    

}