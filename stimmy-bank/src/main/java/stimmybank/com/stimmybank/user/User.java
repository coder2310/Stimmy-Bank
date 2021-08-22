package stimmybank.com.stimmybank.user;

import com.google.common.hash.Hashing;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import stimmybank.com.stimmybank.account.Account;
import stimmybank.com.stimmybank.transaction.Transaction;
import javax.persistence.*;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user")
@IdClass(UserId.class)
public class User implements Serializable {
    @Id
    @Column(nullable = false)
    private final String userName;

    @Id
    @Column(nullable = false)
    private final String uuid;
    @Column(nullable = false)
    private final String firstName;
    @Column(nullable = false)
    private final String lastName;
    @Column(nullable = false)
    private final String passWord;
    @Column(nullable = false)
    private final Timestamp joined;

    @OneToMany(mappedBy = "user")
    private List<Account> accounts;
    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", uuid='" + uuid + '\'' +
                ", joined=" + joined +
                '}';
    }
    private static String hashPassword(String passWord) {
           return Hashing.sha256().hashString(passWord, StandardCharsets.UTF_8).toString();
    }
    User() throws NoSuchAlgorithmException {
        this.firstName = "";
        this.lastName = "";
        this.userName = "";
        this.passWord = "";
        this.uuid = "";
        this.joined = null;
    }

    public User(String firstName, String lastName, String userName, String passWord) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.passWord = passWord;
        this.uuid = UUID.randomUUID().toString();
        this.joined = new Timestamp(System.currentTimeMillis());
    }
    public String getPassWord() {
        return this.passWord;
    }

    public String getUuid() {
        return this.uuid;
    }

    public Timestamp getJoined() {
        return joined;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return this.userName;
    }


}
