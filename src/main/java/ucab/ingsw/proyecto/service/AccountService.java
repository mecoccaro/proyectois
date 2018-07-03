package ucab.ingsw.proyecto.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.client.RestTemplate;
import ucab.ingsw.proyecto.command.AccountSignUpCommand;
import ucab.ingsw.proyecto.command.AccountLogInCommand;
import ucab.ingsw.proyecto.command.AccountUpdateCommand;
import ucab.ingsw.proyecto.command.AccountAddFriendCommand;
import ucab.ingsw.proyecto.response.AccountsResponse;
import ucab.ingsw.proyecto.response.AlertsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucab.ingsw.proyecto.model.Accounts;
import ucab.ingsw.proyecto.repository.AccountsRepository;
import ucab.ingsw.proyecto.service.profile.*;

import java.util.ArrayList;
import java.util.List;
@CrossOrigin
@Slf4j
@Service("accountService")
public class AccountService {

    @Autowired
    private AccountsRepository accountsRepository;

//    @Autowired
//    private FriendsRepository friendsRepository;

    private Accounts buildAccount(AccountSignUpCommand command) {
        Accounts account = new Accounts();
        account.setId(System.currentTimeMillis());
        account.setFirstName(command.getFirstName());
        account.setLastName(command.getLastName());
        account.setDateOfBirth(command.getDateOfBirth());
        account.setEmail(command.getEmail());
        account.setPassword(command.getPassword());
        account.setProfilePicture(getProfile(command.getGender()));

        return account;
    }

    private Accounts buildAgainAccount(AccountUpdateCommand command, String uuid) {
        Accounts accounts = new Accounts();
        accounts.setId(Long.parseLong(uuid));
        accounts.setFirstName(command.getFirstName());
        accounts.setLastName(command.getLastName());
        accounts.setDateOfBirth(command.getDateOfBirth());
        accounts.setEmail(command.getEmail());
        accounts.setPassword(command.getPassword());
        accounts.setProfilePicture(command.getProfilePicture());

        return accounts;
    }

    private Accounts buildFriend(AccountAddFriendCommand command, String id){
        Accounts accounts = new Accounts();
        accounts.setId(Long.parseLong(id));
        accounts = searchAccountsById(id);
        accounts.getFriends().add(command.getFriendId());

        return accounts;
    }

    private AlertsResponse buildAlert(String message) {
        AlertsResponse response = new AlertsResponse();
        response.setMessage(message);
        return response;
    }

    private Accounts searchAccountsById(String id){
        if(accountsRepository.findById(Long.parseLong(id)).isPresent()){
            Accounts accounts = accountsRepository.findById(Long.parseLong(id)).get();
            return accounts;
        }
        else
            return null;
    }

    private static final String profile = "https://randomuser.me/api/?gender=";


    private String getProfile(String gender){
        String url;
        if(gender.toLowerCase().equals("male")){
            url = profile+"male";
        }
        else
            url = profile+"female";
        RestTemplate restTemplate = new RestTemplate();
        AccountContainer accountContainer = restTemplate.getForObject(url, AccountContainer.class);

        log.info("Returning profile picture ={}");
        return accountContainer.getResults().get(0).getPicture().getLarge();
    }


    public ArrayList<AccountsResponse> searchAccountsByName(String search){
        log.debug("About to process [{}]", search);
        ArrayList<AccountsResponse> response = new ArrayList<>();
        accountsRepository.findAll().forEach(it->{
            String name = it.getFirstName();
            String lastName = it.getLastName();
            String fullName = name.concat(lastName);
            if(fullName.toLowerCase().contains(search.toLowerCase())) {
                AccountsResponse accountsResponse = new AccountsResponse();
                accountsResponse.setFirstName(it.getFirstName());
                accountsResponse.setLastName(it.getLastName());
                accountsResponse.setEmail(it.getEmail());
                accountsResponse.setId(it.getUuid());
                accountsResponse.setDateOfBirth(it.getDateOfBirth());
                response.add(accountsResponse);
            }
        });
        return response;
    }



    public ResponseEntity<Object> register(AccountSignUpCommand command) {
        log.debug("About to process [{}]", command);

        if(accountsRepository.existsByEmail(command.getEmail())){
            log.info("email {} already registered", command.getEmail());
            return ResponseEntity.badRequest().body(buildAlert("La cuenta ya esta registrada."));
        }
        else {
            if(!command.getPassword().equals(command.getConfirmationPassword())) {
                log.info("Mismatching passwords.");
                return ResponseEntity.badRequest().body(buildAlert("Las contraseñas no coinciden."));
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
                accountsResponse.setDateOfBirth(account.getDateOfBirth());
                accountsResponse.setEmail(account.getEmail());
                accountsResponse.setId(account.getUuid());
                accountsResponse.setProfilePicture(account.getProfilePicture());
                return ResponseEntity.ok(accountsResponse);
            }
            else {
                log.info("password not valid for account= {}",account.getEmail());

                return  ResponseEntity.badRequest().body(buildAlert("Clave invalida"));
            }
        }
    }

    public ResponseEntity<Object> accountsById(String id){
        log.debug("About to process [{}]", id);

        Accounts accounts = searchAccountsById(id);
        if (accounts == null) {
            log.info("Cannot find ID={}", id);

            return ResponseEntity.badRequest().body(buildAlert("invalid_Id"));
        }
        else {
            AccountsResponse accountsResponse = new AccountsResponse();
            accountsResponse.setProfilePicture(accounts.getProfilePicture());
            accountsResponse.setFirstName(accounts.getFirstName());
            accountsResponse.setLastName(accounts.getLastName());
            accountsResponse.setDateOfBirth(accounts.getDateOfBirth());
            accountsResponse.setEmail(accounts.getEmail());
            accountsResponse.setPassword(accounts.getPassword());
            accountsResponse.setId(accounts.getUuid());
            accountsResponse.setFriends(accounts.getFriends());


            log.info("Account finded with ID={}", id);
            return ResponseEntity.ok(accountsResponse);
        }

    }

    public ResponseEntity getAccountsByName(String search){
        ArrayList<AccountsResponse> response = searchAccountsByName(search);
        if(response.isEmpty()){
            log.info("Cannot find name={}", search);

            return ResponseEntity.badRequest().body(buildAlert("No se ha conseguido el usuario"));
        }
        else {
            log.info("Returning info ={}", search);
            return ResponseEntity.ok(response);
        }
    }

    public ResponseEntity<Object> addFriend(AccountAddFriendCommand command, String id){
        log.debug("About to process [{}]", command);

        if (!accountsRepository.existsById(Long.parseLong(id))) {
            log.info("Cannot find user with ID={}");

            return ResponseEntity.badRequest().body(buildAlert("invalid"));
        } else {
            Accounts accounts = buildFriend(command,id);
            accounts = accountsRepository.save(accounts);

            log.info("Friend addrd ID={}", command);

            return ResponseEntity.ok().body(buildAlert("Amigo anadido"));

        }
    }

    public ResponseEntity<Object> updateAccount(AccountUpdateCommand command, String uuid) {
        log.debug("About to process [{}]", command);

        if (!accountsRepository.existsById(Long.parseLong(uuid))) {
            log.info("Cannot find user with ID={}", uuid);

            return ResponseEntity.badRequest().body(buildAlert("invalid"));
        } else {
            Accounts accounts = buildAgainAccount(command, uuid);
            accounts = accountsRepository.save(accounts);

            log.info("Updated user ID={}", accounts.getId());

            return ResponseEntity.ok().body(buildAlert("Datos Actualizados."));
        }
    }


    public List<Accounts> findAccountsByEmail(String email) {

        List<Accounts> accounts = accountsRepository.findByEmailIgnoreCaseContaining(email);

        log.info("Found {} records with the partial email address={}", accounts.size(), email);
        return accounts;
    }
}
