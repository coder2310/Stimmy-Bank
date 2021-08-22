package stimmybank.com.stimmybank.forms;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import stimmybank.com.stimmybank.user.User;
import stimmybank.com.stimmybank.user.UserService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class Regristration {
    private final UserService userService;

    @Autowired
    public Regristration(UserService userService){
        this.userService = userService;
    }

    public Map<String, Object> registerUser(@ModelAttribute("user") User user){
        Map<String, Object> response = new HashMap<String, Object>();
        try{
            userService.registerNewUser(user);
            response.put("status", "inserted");
        }
        catch (IllegalStateException exception){
            response.put("status", "failed");

        }

        finally {
            return response;
        }
    }


}
