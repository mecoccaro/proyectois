package ucab.ingsw.proyecto.service;

import ucab.ingsw.proyecto.command.AccountSignUpCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ucab.ingsw.proyecto.model.Accounts;
import ucab.ingsw.proyecto.repository.AccountsRepository;

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

    public boolean register(AccountSignUpCommand command) {

        log.debug("About to process [{}]", command);

        Accounts account = buildAccount(command) ;
        account = accountsRepository.save(account);

        log.info("Registered customer with ID={}", account.getId());
        return true;
    }

    public List<Accounts> findAccountsByEmail(String email) {

        List<Accounts> accounts = accountsRepository.findFirst3ByEmailIgnoreCaseContaining(email);

        log.info("Found {} records with the partial email address={}", accounts.size(), email);
        return accounts;
    }
}
