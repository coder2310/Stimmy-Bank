package stimmybank.com.stimmybank.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import stimmybank.com.stimmybank.user.User;

import java.util.Map;
import java.util.Optional;

@Repository
public interface AccountRepository
        extends JpaRepository<Account, Map<String, String>>{


    @Query("SELECT a FROM Account a WHERE a.user.userName = ?1 AND a.accountType = 'CHECKING'")
    Optional<Account> userHasCheckingAccount(String userName);

    @Query("SELECT a FROM Account a WHERE a.user.userName = ?1 AND a.accountType = 'SAVINGS'")
    Optional<Account> userHasSavingsAccount(String uuid);

//    @Query("INSERT INTO Account (")
}
