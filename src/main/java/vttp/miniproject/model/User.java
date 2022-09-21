package vttp.miniproject.model;

import org.springframework.stereotype.Component;
import java.io.Serializable;

@Component
public class User implements Serializable{
    String username;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
