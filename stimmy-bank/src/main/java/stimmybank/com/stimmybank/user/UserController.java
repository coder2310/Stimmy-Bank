package stimmybank.com.stimmybank.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import stimmybank.com.stimmybank.auth.AuthenticationRequest;
import stimmybank.com.stimmybank.auth.AuthenticationResponse;
import stimmybank.com.stimmybank.services.MyUserDetailsService;
import stimmybank.com.stimmybank.util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(path="/stimmybank")
public class UserController {
    private final UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    JwtUtil jwtTokenUtil;
    public UserController(UserService userService) {
        this.userService = userService;
    }
   @CrossOrigin
   @GetMapping("/users")
    public List<User>  getUsers(HttpServletRequest request) throws NoSuchAlgorithmException {
//        HttpSession session = request.getSession();
//        session.setAttribute("sessionID", 1);
       System.out.println("USERS END POINT HIT");
        return userService.getUsers();

    }

    @PostMapping("/register")
    public void registerNewUser(@RequestBody User user){
        userService.registerNewUser(user);
    }

    @GetMapping("/user/exists/{userName}")
    @ResponseBody
    public Map<String, Boolean> userExists(@PathVariable("userName") String userName){
        if (userService.checkIfUserExists(userName)){
            return Collections.singletonMap("userExists", true);
        };
        return Collections.singletonMap("userExists", false);

    }

    @GetMapping("/user/info/{userName}")
    public User getUserInfo(@PathVariable("userName") String userName){
        User user = userService.getUserInfo(userName);
        return user;
    }

    @DeleteMapping("/users/delete/{userName}")
    @ResponseBody
    public Map<String, Boolean> deleteStudent(@PathVariable("userName") String userName){
        if(userService.deleteUser(userName)) {
            return Collections.singletonMap("userDeleted", true);
        };
        return Collections.singletonMap("userDeleted", false);
    }

    @CrossOrigin
    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
    throws Exception {
//        System.out.println(authenticationRequest.getUserName());
//        String data = authenticationRequest.getUserName() + " " + authenticationRequest.getPassword();
//        System.out.println("AUTHENTICATE END POINT HIT");
//        System.out.println("SETTING UP JWT 1");
//        System.out.println("Auth Request Username:" + data);
//        System.out.println("Auth Request Password:");
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
//
//        final String jwt = jwtTokenUtil.generateToken(userDetails);
//        System.out.println("RETURNING JWT");
//        return  ResponseEntity.ok(new AuthenticationResponse(jwt));
        try {
            System.out.println("Start of try block");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword())
            );
            System.out.println("End of try block");
        }

        catch (BadCredentialsException exception){
            Map<String,String> response = new HashMap<>();
            System.out.println("in catch block");
            response.put("status: ", HttpStatus.BAD_REQUEST.toString());
            return ResponseEntity.ok(response);
            //throw new Exception("Incorrect username or password");
        }
        System.out.println("SETTING UP JWT");
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());

        final String jwt = jwtTokenUtil.generateToken(userDetails);
        System.out.println("RETURNING JWT");
        return  ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}
