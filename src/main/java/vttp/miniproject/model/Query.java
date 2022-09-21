package vttp.miniproject.model;

import java.io.Serializable;

public class Query implements Serializable{
    Integer NUM_API_CALLS;

    public Integer getNUM_API_CALLS() {
        return NUM_API_CALLS;
    }

    public void setNUM_API_CALLS(Integer nUM_API_CALLS) {
        NUM_API_CALLS = nUM_API_CALLS;
    }
}
