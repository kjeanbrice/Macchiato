package com.macchiato.controllers;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by li on 4/4/2017.
 */
@Controller
public class LogoutController {
    @RequestMapping(value="logout.htm", method = RequestMethod.GET)
    public String LoginService (){
        UserService userService = UserServiceFactory.getUserService();
        if(userService.isUserLoggedIn()){
            System.out.println("User Logged out");
            return "redirect:" + userService.createLogoutURL("/");
        }
        else{
            System.out.println("User already logged out");
        }
        return "/";
    }
}
