package vttp.miniproject.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BoredResults implements Serializable{

    private static final Logger logger = LoggerFactory.getLogger(BoredResults.class);

    private String activity;
    private String type;
    private BigDecimal participants;
    private BigDecimal price;
    private String key;
    private BigDecimal accessibility;
    
    public String getActivity() {
        return activity;
    }
    public void setActivity(String activity) {
        this.activity = activity;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public BigDecimal getParticipants() {
        return participants;
    }
    public void setParticipants(BigDecimal participants) {
        this.participants = participants;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public BigDecimal getAccessibility() {
        return accessibility;
    }
    public void setAccessibility(BigDecimal accessibility) {
        this.accessibility = accessibility;
    }

    public static BoredResults create(String json) throws IOException{
        BoredResults p = new BoredResults();
        try(InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            // logger.info("JsonObject read >>>>" + o);
            // JsonArray dataArr = o.getJsonArray("Data");
            // logger.info("dataArr >>>>>" + dataArr);
            p.activity = o.getString("activity");
            p.type = o.getString("type");
            // c.result = o.getJsonNumber("result").bigDecimalValue();
            p.participants = o.getJsonNumber("participants").bigDecimalValue();
            p.price = o.getJsonNumber("price").bigDecimalValue();
            p.key = o.getString("key");
            p.accessibility = o.getJsonNumber("accessibility").bigDecimalValue();
            // logger.info(p.price.toString());
            // logger.info(p.rating);
            }
        return p;
    }

    public JsonObject toJson() {
		return Json.createObjectBuilder()
			.add("activity", this.activity)
			.add("type", this.type)
			.add("participants", this.participants)
			.add("price", this.price)
			.add("key", this.key)
			.add("accessibility", this.accessibility)
			.build();
	}

}

