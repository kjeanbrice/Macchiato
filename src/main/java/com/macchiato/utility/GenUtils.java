package com.macchiato.utility;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.KeyFactory.Builder;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 3/26/2017.
 */
public class GenUtils {
    public static final int STUDENT = 0;
    public static final int INSTRUCTOR = 1;
    public static final int ADMIN = 2;
    public static final int NOT_LOGGED_IN = 3;


    public static void createInstructor(String email){
        if(email == null){
            return;
        }

        if(email.trim().isEmpty()){
            return;
        }

        Entity user;
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key key = new Builder("User",email).getKey();


        // Key key = new Builder("User",email).addChild("Course","cse336").getKey();
        //Key parentKey = KeyFactory.createKey(key,"Group");



        try {
            user = datastore.get(key);
            user.setProperty("access",1);
            //user.setProperty("course","336");
            user.setProperty("email",email.trim());
            System.out.println("Create Instructor: Key: " + user.getKey());
            System.out.println("Create Instructor: User already in datastore");
        } catch (EntityNotFoundException e) {
            user = new Entity("User",email);
            user.setProperty("access",1);
            //user = new Entity("Course","cse336",key1);
            System.out.println("Create Instructor: Key " + user.getKey());
            //user.setProperty("course","336");
            user.setProperty("email",email.trim());
            System.out.println("Create Instructor: New Instructor in datastore.");
        }

        datastore.put(user);
        return;

    }


    public static void createStudent(String email){
        if(email == null){
            return;
        }

        if(email.trim().isEmpty()){
            return;
        }

        Entity user;
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key key = new Builder("User",email).getKey();


        // Key key = new Builder("User",email).addChild("Course","cse336").getKey();
        //Key parentKey = KeyFactory.createKey(key,"Group");



        try {
            user = datastore.get(key);
            user.setProperty("access",0);
            //user.setProperty("course","336");
            user.setProperty("email",email.trim());
            System.out.println("Create Instructor: Key: " + user.getKey());
            System.out.println("Create Student: User already in datastore");
        } catch (EntityNotFoundException e) {
            user = new Entity("User",email);
            user.setProperty("access",0);
            //user = new Entity("Course","cse336",key1);
            System.out.println("Create Student: Key " + user.getKey());
            //user.setProperty("course","336");
            user.setProperty("email",email.trim());
            System.out.println("Create Student: New student in datastore.");
        }

        datastore.put(user);
        return;

    }

    public static ArrayList<Object> checkCredentials(){

        ArrayList<Object> credentials = new ArrayList<>();

        UserService userService = UserServiceFactory.getUserService();
        if(userService.isUserLoggedIn()){
            User user = userService.getCurrentUser();
            if(isInstructor(user.getEmail())){
                credentials.add(0,INSTRUCTOR);
                credentials.add(1,user);
                return credentials;
            }
            else if(isAdmin(user.getEmail())){
                credentials.add(0,ADMIN);
                credentials.add(1,user);
                return credentials;
            }
            else{
                credentials.add(0,STUDENT);
                credentials.add(1,user);
                return credentials;
            }
        }
        else{
            credentials.add(0,NOT_LOGGED_IN);
            credentials.add(1,null);
            return credentials;
        }
    }


    public static boolean isInstructor(String email){

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key key = new Builder("User",email).getKey();

        try {
            Entity user = datastore.get(key);
            long access = -1;
            access = (long)user.getProperty("access");
            if(access == 1){
                return true;
            }
            return false;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    public static boolean isAdmin(String email){

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key key = new Builder("User",email).getKey();

        try {
            Entity user = datastore.get(key);
            long access = -1;
            access = (long)user.getProperty("access");
            if(access == 2){
                return true;
            }
            return false;
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    public static User getActiveUser(){
        UserService userService = UserServiceFactory.getUserService();
        if(userService.isUserLoggedIn()) {
            User user = userService.getCurrentUser();
            return user;
        }
        else{
            return null;
        }
    }

    public static void addUser(String email, String nickname, int access){
        Entity user;
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Key key = new Builder("User",email).getKey();

        try {
            datastore.get(key);
        } catch (EntityNotFoundException e) {

            user = new Entity("User",email.trim());
            user.setProperty("access",access);
            user.setProperty("email",email.trim());
            if(nickname == null | nickname.trim().length() == 0){
                user.setProperty("username",email);
            }
            else {
                user.setProperty("username",nickname);
            }
            datastore.put(user);
        }
    }







}



