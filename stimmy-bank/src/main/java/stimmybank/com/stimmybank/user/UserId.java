package stimmybank.com.stimmybank.user;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserId implements Serializable {
    private  String uuid;
    private final String userName;
    UserId(){
        this.uuid = "";
        this.userName = "";
    }
    UserId(String uuid, String userName){
        this.uuid = uuid;
        this.userName = userName;
    }



    public String getUuid() {
        return uuid;
    }

    public String getUserName() {
        return userName;
    }
}
