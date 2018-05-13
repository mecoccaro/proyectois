package ucab.ingsw.proyecto.service;

import org.springframework.http.ResponseEntity;
import ucab.ingsw.proyecto.command.AccountSignUpCommand;
import ucab.ingsw.proyecto.command.AccountLogInCommand;
import ucab.ingsw.proyecto.response.AccountsResponse;
import ucab.ingsw.proyecto.response.AlertsResponse;
import ucab.ingsw.proyecto.repository.AccountsRepository;
import ucab.ingsw.proyecto.model.Accounts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("accountService")
public class AccountService {

    @Autowired(required=false)
    private AccountsRepository accountsRepository;

    private Accounts buildAccount(AccountSignUpCommand command) {
        Accounts account = new Accounts();
        account.setId(System.currentTimeMillis());
        account.setFirstName(command.getFirstName());
        account.setLastName(command.getLastName());
        account.setEmail(command.getEmail());
        account.setPassword(command.getPassword());

        return account;
    }
    private AlertsResponse buildAlert(String message){
        AlertsResponse response = new AlertsResponse();
        response.setMessage(message);
        return response;
    }
    
    public ResponseEntity<Object> register(AccountSignUpCommand command) {
        log.debug("About to process [{}]", command);

        if(accountsRepository.searchByEmail(command.getEmail())){
            log.info("email {} already registered", command.getEmail());
            return ResponseEntity.badRequest().body(buildAlert("La cuenta ya esta registrada."));
        }
        else {
            if(!command.getPassword().equals(command.getConfirmationPassword())) {
                log.info("Mismatching passwords.");
                return ResponseEntity.badRequest().body(buildAlert("Las contraseñas son diferentes."));
            }

            else {
                Accounts account = buildAccount(command);
                account = accountsRepository.save(account);

                log.info("Registered account ID={}", account.getId());

                return ResponseEntity.ok().body(buildAlert("Cuenta registrada."));
            }
        }
    }


    public  ResponseEntity<Object> login(AccountLogInCommand command) {

        log.debug("About to process [{}]", command);

        Accounts account =accountsRepository.findByEmail(command.getEmail());
        if (account == null){
            log.info("email not found={}", command.getEmail());
            return  ResponseEntity.badRequest().body(buildAlert("Email Invalido."));
        }
        else{
            if(account.getPassword().equals(command.getPassword())){
                log.info("Account founded for email={}",account.getId());

                AccountsResponse accountsResponse = new AccountsResponse();
                accountsResponse.setFirstName(account.getFirstName());
                accountsResponse.setLastName(account.getLastName());
                accountsResponse.setEmail(account.getEmail());
                accountsResponse.setId(account.getUuid());
                return ResponseEntity.ok(accountsResponse);
            }
            else {
                log.info("password not valid for account= {}",account.getEmail());

                return  ResponseEntity.badRequest().body(buildAlert("Clave invalida"));
            }
        }
    }


    public List<Accounts> findAccountsByEmail(String email) {

        List<Accounts> accounts = accountsRepository.findFirst3ByEmailIgnoreCaseContaining(email);

        log.info("Found {} records with the partial email address={}", accounts.size(), email);
        return accounts;
    }
}
