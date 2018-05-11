package ucab.ingsw.proyecto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("accountService")
public class AccountService {

    @Autowired
    private AccountsRepository accountsRepository;

    private Accounts buildAccount(AccountSignUp signUp){
        Accounts accounts = new Accounts();
        accounts.setId(System.currentTimeMillis());
        accounts.setName(signUp.getName());
        accounts.setLastName(signUp.getLastName());
        accounts.setEmail(signUp.getEmail());
        accounts.setPassword(signUp.getPassword());

        return accounts;
    }

    public boolean register(AccountSignUp signUp){
        log.debug("About to process [{}]", signUp);

        Accounts accounts = buildAccount(signUp);
        accounts = accountsRepository.save(accounts);

        log.info("Cuenta registrada con el id={}", accounts.getId());

        return true;
    }

    public List<Accounts> findAccountEmail(String email){
        List<Accounts> accounts = accountsRepository.findFirst3ByEmailIgnoreCaseContaining(email);

        log.info("Se encontro {} con la informacion del correo={}", accounts.size(),email);

        return accounts;
    }

}
