package stimmybank.com.stimmybank.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import stimmybank.com.stimmybank.user.User;
import stimmybank.com.stimmybank.user.UserService;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    public MyUserDetailsService(UserService userService) {this.userService = userService;}
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return  new User("aks646", "Python2310!", new ArrayList<>());
        User user = userService.getUserInfo(username);
        return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassWord(),
                new ArrayList<>());

    }

}
