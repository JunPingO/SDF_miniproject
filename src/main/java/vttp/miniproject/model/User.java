package vttp.miniproject.model;

import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.util.List;

@Component
public class User implements Serializable{
    String username;
    List<BoredResults> results;

    public List<BoredResults> getResults() {
        return results;
    }
    public void setResults(List<BoredResults> results) {
        this.results = results;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public void addResults(BoredResults result){
        results.add(result);
    }
}
