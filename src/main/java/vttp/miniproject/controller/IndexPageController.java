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

import vttp.miniproject.model.BoredResults;
import vttp.miniproject.model.Query;
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
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/userlogin")
    public String loginSubmit(@ModelAttribute Query query, Model model){
        // this.user = user;
        model.addAttribute("user", user);
        // List<Object> listofresults = redisService.findAll(user);
        // model.addAttribute("listofresults", listofresults);
        return "main";
    }

    @PostMapping("/mainpage")
    public String MainAfterLogin(@ModelAttribute Query query, Model model){
        // this.user = user;
        model.addAttribute("query", query);
        List<BoredResults> boredcalls = service.multipleAPICalls(query.getNUM_API_CALLS());
        model.addAttribute("boredlist",boredcalls);
        return "dummy";
    }

    @GetMapping ("/user")
    public String displayOneResult(Model model){
        Optional<BoredResults> bored = service.BoredAPICall();
        model.addAttribute("boredlist",bored.get());
        User exsUser = new User();
        exsUser.setUsername("test");
        redisService.save(exsUser, bored.get());
        return "testpage";
    }

    @GetMapping ("/user/{username}")
    public String retrieveUser(Model model, @PathVariable(name="username") String username){
        logger.info("input>>>>>>" + username);
        Optional <BoredResults> bored = redisService.getByUsername("test");
        logger.info(bored.toString());
        model.addAttribute("boredlist",bored);
        return "landing";
    }

       // @GetMapping(path = "/test/{noRun}")
    // public String displayMultipleResult(Model model, @PathVariable Integer noRun){
    //     logger.info("no.of runs>>>" + noRun.toString());
    //     List<BoredResults> boredlist = new ArrayList<>();
    //     for (int i =0; i < noRun; i++){
    //     Optional<BoredResults> boredcalls = service.testAPICall("hello");
    //     boredlist.add(boredcalls.get());
    //     }
    //     model.addAttribute("boredlist",boredlist);
    //     return "apimulti";
    // }


    // @GetMapping(path = "/test/{noRun}")
    // public String displayMultipleResult(Model model, @PathVariable Integer noRun){
    //     logger.info("no.of runs>>>" + noRun.toString());
    //     List<BoredResults> boredlist = new ArrayList<>();
    //     for (int i =0; i < noRun; i++){
    //     Optional<BoredResults> boredcalls = service.testAPICall("hello");
    //     boredlist.add(boredcalls.get());
    //     }
    //     model.addAttribute("boredlist",boredlist);
    //     return "apimulti";
    // }

    @GetMapping(path = "/bored/{count}")
    public String displayMultipleResultWithMultiService(Model model, @PathVariable Integer count){
        logger.info("testing displayMultipleResults>>>" + count.toString());
        List<BoredResults> boredcalls = service.multipleAPICalls(count);
        // User user = new User();
        // user.setUsername("JP TEST");
        redisService.save(boredcalls, "JPTEST2");
        model.addAttribute("boredlist",boredcalls);
        return "apimulti";
    }

}
