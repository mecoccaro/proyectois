package ucab.ingsw.proyecto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/account", produces = "application/json")
public class AccountsController {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "", consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity register(@Valid @RequestBody AccountSignUp signUp){
        boolean result = accountService.register(signUp);

        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/{email}", method = RequestMethod.GET)
    public ResponseEntity<List<AccountResponse>> getAccountEmail(@PathVariable("email") String email){
        ArrayList<AccountResponse> response = new ArrayList<>();
        accountService.findAccountEmail(email).forEach(a ->{
            AccountResponse accountResponse = new AccountResponse();
            accountResponse.setName(a.getName());
            accountResponse.setLastName(a.getLastName());
            accountResponse.setEmail(a.getEmail());
            accountResponse.setId(a.getUuid());

            response.add(accountResponse);
        });

        return ResponseEntity.ok(response);
    }

}
