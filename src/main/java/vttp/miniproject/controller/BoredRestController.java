package vttp.miniproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp.miniproject.model.BoredResults;
import vttp.miniproject.service.RedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(path = "/bored", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class BoredRestController {
    private static final Logger logger = LoggerFactory.getLogger(BoredRestController.class);

    @Autowired
    RedisService redisService;

    @GetMapping (path="/user/{username}", consumes =MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE) 
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
     
        logger.info("get" + username);
            Optional<BoredResults> optUser = redisService.getByUsername(username);

            if(optUser.isEmpty()){
            JsonObject errJson = Json.createObjectBuilder()
            .add("error","User " + username + " not found")
            .build();
            return ResponseEntity.status(404).body(errJson.toString());
            
        }
        
        return ResponseEntity.ok(optUser.get());
    }

}
