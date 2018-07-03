package ucab.ingsw.proyecto.controller;

import ucab.ingsw.proyecto.command.AccountUpdateCommand;
import ucab.ingsw.proyecto.service.AccountService;
import ucab.ingsw.proyecto.response.AccountsResponse;
import ucab.ingsw.proyecto.command.AccountSignUpCommand;
import ucab.ingsw.proyecto.command.AccountLogInCommand;
import ucab.ingsw.proyecto.command.AccountAddFriendCommand;
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

    @RequestMapping(value = "/update/{uuid}", consumes = "application/json", method = RequestMethod.PUT)
    public ResponseEntity update(@Valid @RequestBody AccountUpdateCommand command, @PathVariable("id") String uuid) {
        return accountService.updateAccount(command, uuid);
    }
    @RequestMapping(value = "/searchId/{id}", method = RequestMethod.GET)
    public ResponseEntity getAccount(@PathVariable("id") String id) {
        return accountService.accountsById(id);
    }

    @RequestMapping(value = "/addFriend/{id}", consumes = "application/json", method = RequestMethod.PUT)
    public ResponseEntity addFriend(@Valid @RequestBody AccountAddFriendCommand command, @PathVariable("id") String id){
        return accountService.addFriend(command,id);
    }

    @RequestMapping(value = "/searchName/{name}", consumes = "application/json", method = RequestMethod.GET)
    public ResponseEntity getAccountByName(@PathVariable("name") String name){
        return accountService.getAccountsByName(name);
    }

}
