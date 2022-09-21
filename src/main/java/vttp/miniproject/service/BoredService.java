package vttp.miniproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import vttp.miniproject.model.BoredResults;

@Service
public class BoredService {
    private static final Logger logger = LoggerFactory.getLogger(Service.class);

    String apiKey = "insert";
    String URL = "http://www.boredapi.com/api/activity/";

    // ?key=9212950
 

    public Optional<BoredResults> BoredAPICall(){
        // logger.info("testing logger");

        String API_URL = UriComponentsBuilder.fromUriString(URL).toUriString();
        RestTemplate template = new RestTemplate();
        try {
            // HttpHeaders headers = new HttpHeaders();
            // headers.add("Apikey",apiKey);
            // HttpEntity request = new HttpEntity(headers);
            // logger.info(request.toString());
            //A GET request will be performed to the given URL sending the HTTP headers that are wrapped in the HttpEntity instance.
            ResponseEntity<String> resp = template.getForEntity(API_URL, String.class);
            logger.info(resp.getBody());
            BoredResults results = BoredResults.create(resp.getBody());
            return Optional.of(results);
        } catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
        }   
        return Optional.empty();
    }


    public List<BoredResults> multipleAPICalls(int count){

        List<BoredResults> boredlist = new ArrayList<>();

        // boolean DuplicateStatus = false;
        // List<String> boredlistKeys = new ArrayList<>();
            
        for (int i =0; i < count; i++){
            Optional<BoredResults> boredcalls = BoredAPICall();

            // if(boredlist.size() > 0){
            //     for (int j=0; j < boredlist.size(); j++){
            //         boredlistKeys.add(boredlist.get(j).getKey());
            //     }
            // }
            boredlist.add(boredcalls.get());
            }
        return boredlist;
    }

}
