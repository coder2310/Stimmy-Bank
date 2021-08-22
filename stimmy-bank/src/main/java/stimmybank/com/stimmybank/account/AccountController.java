package stimmybank.com.stimmybank.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import stimmybank.com.stimmybank.exceptions.RestTemplateException;
import stimmybank.com.stimmybank.exceptions.UserNotFoundException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@Controller
@RequestMapping(path="/stimmybank")
public class AccountController {
    public  AccountService accountService;

    @Autowired
    AccountController(AccountService accountService) {this.accountService = accountService;}

    private Map<String,String> validateOpenAccountBodyValues(Map<String, String> infoRequired) {
        Map<String, String> response = new HashMap<>();

        if (validateOpenAccountBody(infoRequired).get("httpStatus")
                .equals(Integer.toString(HttpStatus.BAD_REQUEST.value()))){
            return validateOpenAccountBody(infoRequired);
        }

        boolean userExistsInSystem;

            userExistsInSystem = accountService.userExists(infoRequired.get("userName"));
            if (!userExistsInSystem) {
                response.put("userError", "false");
            }
            try {
                if (infoRequired.get("accountType").toUpperCase(Locale.ROOT)
                        .equals(AccountType.CHECKING.toString())
                        && accountService.userHasCheckingAccount(infoRequired.get("userName"))) {
                    response.put("checkingError", "true");
                }
                if (infoRequired.get("accountType").toUpperCase(Locale.ROOT)
                        .equals(AccountType.SAVINGS.toString())
                        && accountService.userHasSavingsAccount(infoRequired.get("userName"))) {
                    response.put("savingsError", "true");
                }
            }

            catch (UserNotFoundException exception){
                response.put("errorMessage", infoRequired.get("userName") + " does not exist");
                response.put("httpStatus", Integer.toString(HttpStatus.BAD_REQUEST.value()));
            }
            try {
                if (infoRequired.get("accountType").toUpperCase(Locale.ROOT)
                        .equals(AccountType.CHECKING.toString())
                        && Double.parseDouble(infoRequired.get("amount")) < 100) {
                    response.put("amountError", "for a checking account must be at least 100");
                }

                if (infoRequired.get("accountType").toUpperCase(Locale.ROOT)
                        .equals(AccountType.SAVINGS.toString())
                        && Double.parseDouble(infoRequired.get("amount")) < 500) {
                    response.put("amountError", "for savings account must be at least 500");
                }
            }

            catch (NumberFormatException exception){
                response.put("amountError", "number format exception");
                response.put("httpStatus", Integer.toString(HttpStatus.BAD_REQUEST.value()));
            }



        if (response.containsKey("userError") || response.containsKey("checkingError") ||
        response.containsKey("savingsError") || response.containsKey("amountError") ||
        response.containsKey("errorMessage")){
            response.put("httpStatus", Integer.toString(HttpStatus.BAD_REQUEST.value()));
            return  response;
        }

        response.put("httpStatus", Integer.toString(HttpStatus.OK.value()));
        return response;
    }
    private Map<String, String> validateOpenAccountBody(Map<String, String> infoRequired) {
        Map<String, String> response = new HashMap<>();
        if (!infoRequired.containsKey("userName")) {
            response.put("userNameField", "field not found");
        }
        if (!infoRequired.containsKey("amount")) {
            response.put("amountField", "field not found");
        }
        if (infoRequired.containsKey("accountType")) {
            boolean openingChecking = infoRequired.get("accountType")
                    .toUpperCase(Locale.ROOT)
                    .equals(AccountType.CHECKING.toString().toUpperCase(Locale.ROOT));
            boolean openingSavings = infoRequired.get("accountType")
                    .toUpperCase(Locale.ROOT)
                    .equals(AccountType.SAVINGS.toString().toUpperCase(Locale.ROOT));
            if (!(openingChecking || openingSavings)) {
                response.put("accountType", "not a valid accountType");
            }
        }

        if (!infoRequired.containsKey("accountType")) {
            response.put("accountType", "field not found");
        }
        if (response.containsKey("userNameField") || response.containsKey("amountField")
                || response.containsKey("accountType")) {
            response.put("httpStatus", Integer.toString(HttpStatus.BAD_REQUEST.value()));
            return response;
        }
        response.put("httpStatus", Integer.toString(HttpStatus.OK.value()));
        return response;
    }

    @CrossOrigin
    @GetMapping("/account/userHasCheckingAccount/{userName}")
    public ResponseEntity<Map<String,String>> userHasCheckingAccount(@PathVariable String userName){
        Map<String, String> response = new HashMap<>();
        try {
            response.put("hasChecking", Boolean.toString(accountService.userHasCheckingAccount(userName)));
        }
        catch (UserNotFoundException exception){
            response.put("errorMessage", userName + " user does not exist");
            if (exception.getClass() == UserNotFoundException.class) {
                response.put("errorMessage", userName + " user does not exist");
                response.put("httpStatus", Integer.toString(HttpStatus.BAD_REQUEST.value()));
            }

        }
        return ResponseEntity.ok(response);

    }

    @GetMapping("/account/userHasSavingsAccount/{userName}")
    public ResponseEntity<Map<String,String>> userHasSavingsAccount(@PathVariable String userName){
        Map<String, String> response = new HashMap<>();
        try {
            response.put("hasSavings", Boolean.toString(accountService.userHasSavingsAccount(userName)));
        }
        catch (UserNotFoundException exception){
            response.put("errorMessage", userName + " user does not exist");
            if (exception.getClass() == UserNotFoundException.class) {
                response.put("errorMessage", userName + " user does not exist");
                response.put("httpStatus", Integer.toString(HttpStatus.BAD_REQUEST.value()));
            }


        }
        return ResponseEntity.ok(response);
    }

    @CrossOrigin
    @PostMapping("/openaccount")
    public ResponseEntity<Map<String, String>>
    openAccount(@RequestBody Map<String, String> infoRequired) throws RestTemplateException {

        Map<String, String> bodyValuesIsValid =validateOpenAccountBodyValues(infoRequired);
        Map<String, String> response = new HashMap<>();
        response.putAll(bodyValuesIsValid);

        if (response.get("httpStatus")
                .equals(Integer.toString(HttpStatus.BAD_REQUEST.value()))) { // if it failed return response right away
            return ResponseEntity.ok(response);
        }

        if (response.get("httpStatus").equals(Integer.toString(HttpStatus.OK.value()))){
            // then proceed to open account
            // we need an Account object
            if (infoRequired.get("accountType").toUpperCase(Locale.ROOT).equals(AccountType.CHECKING.toString())){
                Account account = new Account(accountService.getUserInfo(infoRequired.get("userName")),
                        AccountType.CHECKING, Double.parseDouble(infoRequired.get("amount")));
                accountService.openAccount(account);
            }

        }
        return ResponseEntity.ok(response);
    }
//    @PostMapping("/account/createCheckingAccount")
//    public ResponseEntity<Map<String, String>> openCheckingAccount(@RequestBody Map<String, String> infoRequired){
//
//    }

}
