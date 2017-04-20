package com.macchiato.controllers.logincontroller;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.macchiato.utility.DiscussionBoardUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by li on 4/4/2017.
 */
@Controller
public class LoginController {
    @RequestMapping(value="login.htm", method = RequestMethod.GET)
    public String LoginService (){
        UserService userService = UserServiceFactory.getUserService();
        //Creates dummy data for the discussion board
        DiscussionBoardUtils.createDummyDiscussionData();
        if(userService.isUserLoggedIn()){
            System.out.println("");
            User user = userService.getCurrentUser();
            System.out.println(user.getEmail());
            System.out.println(user.getUserId());
            System.out.println("Already logged in: UserBean Logged out");
            return "redirect:" + userService.createLogoutURL("/");
        }
        else{
            return "redirect:" + userService.createLoginURL("/Student.htm");
        }
    }
}
