package stimmybank.com.stimmybank.account;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import stimmybank.com.stimmybank.exceptions.RestTemplateException;
import stimmybank.com.stimmybank.exceptions.UserNotFoundException;
import stimmybank.com.stimmybank.user.User;
import stimmybank.com.stimmybank.user.UserService;

import java.util.List;
import java.util.Map;


@Service
public class AccountService {
    private final AccountRepository accountRepository;
//    private final String userExistsEndpoint = "http://localhost:8080/stimmybank/user/exists/%s";
//    private final String getUserInfo = "http://localhost:8080/stimmybank/user/info/%s";
    @Autowired
    UserService userService;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public User getUserInfo(String userName) {
        return userService.getUserInfo(userName);

    }
    public boolean userHasCheckingAccount(String userName) throws UserNotFoundException{

       if (!this.userExists(userName)) throw new UserNotFoundException(userName + " does not exists");
       return accountRepository.userHasCheckingAccount(userName).isPresent();

    }

    public boolean userHasSavingsAccount(String userName) throws UserNotFoundException{
        if (!this.userExists(userName)) throw new UserNotFoundException(userName + " does not exists");
        return accountRepository.userHasSavingsAccount(userName).isPresent();

    }


    public boolean userExists(String userName) {
       return userService.checkIfUserExists(userName);

    }

    public void openAccount(Account account){
        System.out.println("ABOUT TO OPEN ACCOUNT");
        accountRepository.save(account);
    }


}
