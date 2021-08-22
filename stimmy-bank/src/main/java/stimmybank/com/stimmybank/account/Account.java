package stimmybank.com.stimmybank.account;

import com.sun.istack.NotNull;
import stimmybank.com.stimmybank.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table
public class Account implements Serializable {


    @Id
    @Column(nullable = false)
    private final String accountNumber;
    @Column(nullable = false)
    private final String routingNumber;
    @Column(nullable = false)
    private final Timestamp dateOpened;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private final AccountType accountType;

    @ManyToOne
    @NotNull
    private final User user;
    private double amount;

    public String getUuid(){
        return user.getUuid();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public Timestamp getDateOpened() {
        return dateOpened;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public double getAmount() {
        return amount;
    }

    private String generateAccountNumber(){
        StringBuilder randomAccountNumber = new StringBuilder();
        for (int  i = 0; i < 12; ++ i){
            int randDigit = (int) (Math.random() * ((9) + 1));
            randomAccountNumber.append(Integer.toString(randDigit));
        }
        return randomAccountNumber.toString();
    }

    private String generateRoutingNumber(){
        StringBuilder randomRoutingNumber = new StringBuilder();
        for (int  i = 0; i < 9; ++ i){
            int randDigit = (int) (Math.random() * ((9) + 1));
            randomRoutingNumber.append(Integer.toString(randDigit));
        }
        return randomRoutingNumber.toString();
    }

    public Account(User user, AccountType accountType, double amount) {
        this.accountNumber = generateAccountNumber();
        this.routingNumber = generateRoutingNumber();
        this.dateOpened = new Timestamp(System.currentTimeMillis());
        this.accountType = accountType;
        this.amount = amount;
        this.user = user;
    }
    public Account(){
        this.accountNumber = "";
        this.routingNumber = "";
        this.dateOpened = null;
        this.accountType = null;
        this.amount = 0;
        this.user = null;
    }
}
