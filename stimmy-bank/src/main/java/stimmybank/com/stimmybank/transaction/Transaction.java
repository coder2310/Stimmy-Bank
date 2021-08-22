package stimmybank.com.stimmybank.transaction;

import stimmybank.com.stimmybank.user.User;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table
public class Transaction {
    @Id
    private final String transactionID;
    private final double amount;
    private final String sendingAccountNumber;
    private final String receivingAccountNumber;
    private final Timestamp timestamp;
    @ManyToOne
    private User user; // user who executed the transaction

    public String getTransactionID() {
        return transactionID;
    }

    public double getAmount() {
        return amount;
    }

    public String getSendingAccountNumber() {
        return sendingAccountNumber;
    }

    public String getReceivingAccountNumber() {
        return receivingAccountNumber;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public User getUser() {
        return user;
    }

    public Transaction(double amount, String sendingAccountNumber, String receivingAccountNumber) {
        this.transactionID = UUID.randomUUID().toString();
        this.amount = amount;
        this.sendingAccountNumber = sendingAccountNumber;
        this.receivingAccountNumber = receivingAccountNumber;
        this.timestamp = new Timestamp(System.currentTimeMillis());
    }
}
