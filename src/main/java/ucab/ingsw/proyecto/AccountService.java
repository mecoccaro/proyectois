package ucab.ingsw.proyecto;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service("accountService")
public class AccountService {

    @Autowired
    private AccountsRepository accountsRepository;

}
