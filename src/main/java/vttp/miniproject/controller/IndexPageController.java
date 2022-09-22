package vttp.miniproject.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import vttp.miniproject.model.BoredResults;
import vttp.miniproject.model.UpdateData;
import vttp.miniproject.model.User;
import vttp.miniproject.service.RedisService;
import vttp.miniproject.service.BoredService;

@Controller
public class IndexPageController {

    private Logger logger = LoggerFactory.getLogger(IndexPageController.class);
    
    @Autowired
    private RedisService redisService;

    @Autowired
    BoredService service;

    @Autowired
    private User user;

    @GetMapping("/")
    public String login(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/userlogin")
    public String loginUser(Model model, @RequestParam(value= "username", required=true) String username){
        Optional<User> exsUser = redisService.findbyUser(username);
        User sessUser = new User();
        if (exsUser.isEmpty()){
            sessUser.setUsername(username);
        } else {
            sessUser = exsUser.get();
        }
        redisService.save(sessUser);
        logger.info("login username >>>>>>" + sessUser.getUsername());
        List<BoredResults> bored = sessUser.getResults();
        model.addAttribute("username", sessUser.getUsername());
        model.addAttribute("boredlist",bored);
        // List<Object> listofresults = redisService.findAll(user);
        // model.addAttribute("listofresults", listofresults);
        return "main";
    }

    @PostMapping("/mainpage/{username}")
    public String addActivities( @PathVariable(name="username", required=true) String username, 
    @RequestParam(value = "apiCallNo") Integer apiCallNo, Model model){

        User sessUser = redisService.findbyUser(username).get();
        List<BoredResults> exsResults = sessUser.getResults();
        List<BoredResults> boredcalls = service.multipleAPICalls(apiCallNo);
        if (exsResults==null){
            exsResults=new ArrayList<BoredResults>();
        }
        for (int i=0; i < boredcalls.size(); i++){
            exsResults.add(boredcalls.get(i));
        }
        sessUser.setResults(exsResults);
        redisService.save(sessUser);
        model.addAttribute("boredlist", exsResults);
        
        return "main";
    }

    @PostMapping("/update/{username}")
    public String update( @PathVariable(name="username", required=true) String username, 
    @ModelAttribute BoredResults datarow, Model model){
        model.addAttribute("datarow", datarow);
        User sessUser = redisService.findbyUser(username).get();
        List<BoredResults> exsResults = sessUser.getResults();
        for (int i =0; i < exsResults.size(); i++){
            if (exsResults.get(i).getKey() == datarow.getKey()){
                exsResults.get(i).setCompletionstatus(datarow.getCompletionstatus());
                exsResults.get(i).setRating(datarow.getRating());
            }
        }
        sessUser.setResults(exsResults);
        redisService.save(sessUser);
        
        return "update";
    }

    @PostMapping("/updateReturn/{username}")
    public String updateReturn( @PathVariable(name="username", required=true) String username, 
    @ModelAttribute BoredResults datarow, Model model){
        model.addAttribute("datarow", datarow);
        // logger.info(datarow.getKey());
        User sessUser = redisService.findbyUser(username).get();
        List<BoredResults> exsResults = sessUser.getResults();
        for (int i =0; i < exsResults.size(); i++){
            if (exsResults.get(i).getKey().equals(datarow.getKey())){
                exsResults.get(i).setCompletionstatus(datarow.getCompletionstatus());
                exsResults.get(i).setRating(datarow.getRating());
            }
        }
        sessUser.setResults(exsResults);
        redisService.save(sessUser);
        model.addAttribute("boredlist", exsResults);
        return "main";
    }

    @GetMapping ("/retrieve/{username}")
    public String retrieveuser(Model model, @PathVariable(name="username") String username){
        User exsUser = new User();
        exsUser = redisService.findbyUser(username).get();
        List<BoredResults> boredcalls = exsUser.getResults();
        model.addAttribute("username",username);
        model.addAttribute("boredlist",boredcalls);
        return "results";
    }

    @GetMapping(path = "/bored/{count}")
    public String displayMultipleResultWithMultiService(Model model, @PathVariable Integer count){
        logger.info("testing displayMultipleResults>>>" + count.toString());
        List<BoredResults> boredcalls = service.multipleAPICalls(count);
        model.addAttribute("boredlist",boredcalls);
        return "apimulti";
    }

    @PostMapping("/mainpage")
    public String MainAfterLogin(@ModelAttribute UpdateData query, Model model){
        // this.user = user;
        model.addAttribute("query", query);
        List<BoredResults> boredcalls = service.multipleAPICalls(query.getNUM_API_CALLS());
        User exsUser = new User();
        Optional<User> dbUser = redisService.findbyUser("jptest");
        if (dbUser.isEmpty()){
            exsUser.setUsername("jptest");
        } else {
            exsUser = dbUser.get();
        }
        exsUser.setResults(boredcalls);
        redisService.save(exsUser);
        model.addAttribute("boredlist",boredcalls);
        return "select";
    }

}
