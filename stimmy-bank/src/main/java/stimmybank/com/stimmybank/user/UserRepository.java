package stimmybank.com.stimmybank.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository
    extends JpaRepository<User, Map<String, String>> {



    @Query("SELECT u FROM User u WHERE u.userName = ?1 OR u.uuid = ?1")
    Optional<User> findIfUserExists(String userName, String uuid);

    @Query("SELECT u FROM User u WHERE u.userName = ?1")
    Optional<User> findIfUserExists(String userName);



}
