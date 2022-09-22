package vttp.miniproject.model;

import java.io.Serializable;

public class UpdateData implements Serializable{
    Integer NUM_API_CALLS;
    private String completionstatus;
    private String rating;

    public String getCompletionstatus() {
        return completionstatus;
    }

    public void setCompletionstatus(String completionstatus) {
        this.completionstatus = completionstatus;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Integer getNUM_API_CALLS() {
        return NUM_API_CALLS;
    }

    public void setNUM_API_CALLS(Integer nUM_API_CALLS) {
        NUM_API_CALLS = nUM_API_CALLS;
    }
}
