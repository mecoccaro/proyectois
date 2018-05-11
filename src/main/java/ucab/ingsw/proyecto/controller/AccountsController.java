package ucab.ingsw.proyecto.controller;

import ucab.ingsw.proyecto.service.AccountService;
import ucab.ingsw.proyecto.response.AccountsResponse;
import ucab.ingsw.proyecto.command.AccountSignUpCommand;
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

    @RequestMapping(value = "", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity register(@Valid @RequestBody AccountSignUpCommand command) {

        boolean result = accountService.register(command);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    public ResponseEntity<List<AccountsResponse>> getAccountsByEmail(@PathVariable("email") String email) {

        ArrayList<AccountsResponse> response = new ArrayList<>();
        accountService.findAccountsByEmail(email).forEach(it ->{
            AccountsResponse accountsResponse = new AccountsResponse();
            accountsResponse.setFirstName(it.getFirstName());
            accountsResponse.setLastName(it.getLastName());
            accountsResponse.setEmail(it.getEmail());
            accountsResponse.setId(it.getUuid());

            response.add(accountsResponse);
        });

        return ResponseEntity.ok(response);
    }
}
