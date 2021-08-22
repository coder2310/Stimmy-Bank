package stimmybank.com.stimmybank.user;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getUsers() throws NoSuchAlgorithmException {
        return userRepository.findAll();
    }

    public void registerNewUser(User user) throws IllegalStateException {
        Optional<User> userByUserName =
                userRepository.findIfUserExists(user.getUserName(), user.getUuid());
        if (userByUserName.isPresent()){
            throw new IllegalStateException("user name taken");
        }
        userRepository.save(user);

    }
    public boolean deleteUser(String userName){
        Optional<User> userByUserName =
                userRepository.findIfUserExists(userName);
        if (userByUserName.isEmpty()){
            return  false;
        }
        Map<String,String> key = new HashMap<>();
        key.put(
                userByUserName.get().getUserName(),
                userByUserName.get().getUuid()
        );

        userRepository.delete(userByUserName.get());
        return true;
    }

    public  boolean checkIfUserExists(String userName){
        return userRepository.findIfUserExists(userName).isPresent();
    }

    public User getUserInfo(String userName){
        if(checkIfUserExists(userName)){
            return userRepository.findIfUserExists(userName).get();
        }
        return null;
    }

}
