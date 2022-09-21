package vttp.miniproject.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.miniproject.model.BoredResults;
import vttp.miniproject.model.User;

@Service
public class RedisService {
    
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Autowired
    @Qualifier("userRedisConfig")
    RedisTemplate<String, Object> redisTemplate;


    public void save(User user, BoredResults results) {
        logger.info("saving...");
        redisTemplate.opsForValue().set(user.getUsername(),results);
        logger.info("saved");
    }

    // public List <BoredResults> save(List <BoredResults> bored, String user) {
    //     redisTemplate.opsForHash().put(user, bored.get(1).getKey(), bored);
    //     logger.info("Game saved: " + bored.get(1).getKey());
    //     // logger.info("User: " + user.getUserName());
    //     return bored;
    // }


    public Optional<BoredResults> getByUsername(final String username) {
        logger.info("find user by username> " + username);
        try{String jsonString = (String) redisTemplate.opsForValue().get(username);
            new BoredResults();
            BoredResults results = BoredResults.create(jsonString);
            return Optional.of(results);
        }catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        logger.info("Redis Service >>>>> no user found");
        return Optional.empty();
    }
}
