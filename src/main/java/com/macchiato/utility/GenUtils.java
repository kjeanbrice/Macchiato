package com.macchiato.utility;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.KeyFactory.Builder;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.macchiato.general.generaldata.CourseData;
import com.macchiato.general.generaldata.CourseDataHelper;
import com.macchiato.general.generaldata.EnrollmentData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karl on 3/26/2017.
 */
public class GenUtils {
    public static final int STUDENT = 0;
    public static final int INSTRUCTOR = 1;
    public static final int ADMIN = 2;
    public final static int SUCCESS = 3;
    public final static int FAILURE = 4;
    public static final int NOT_LOGGED_IN = 5;
    public static final int NO_PERMISSION = 6;
    public static final int EMPTY_PARAMETERS = 7;
    public final static int DUPLICATE_COURSE = 8;
    public final static int INVALID_LENGTH = 9;
    public final static int COURSE_NOT_FOUND = 10;
    public final static int DUPLICATE_ENTRY = 11;
    public final static int DATABASE_ERROR = 12;




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
            user.setProperty("access",GenUtils.INSTRUCTOR);
            //user.setProperty("course","336");
            user.setProperty("email",email.trim());
            System.out.println("Create Instructor: Key: " + user.getKey());
            System.out.println("Create Instructor: User already in datastore");
        } catch (EntityNotFoundException e) {
            user = new Entity("User",email);
            user.setProperty("access",GenUtils.INSTRUCTOR);
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
            user.setProperty("access",GenUtils.STUDENT);
            //user.setProperty("course","336");
            user.setProperty("email",email.trim());
            System.out.println("Create Instructor: Key: " + user.getKey());
            System.out.println("Create Student: User already in datastore");
        } catch (EntityNotFoundException e) {
            user = new Entity("User",email);
            user.setProperty("access",GenUtils.STUDENT);
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


    public static CourseDataHelper retrieveCourseList(String email) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter email_filter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email);
        Query q = new Query("Course").setFilter(email_filter);
        PreparedQuery pq = datastore.prepare(q);

        CourseDataHelper data = null;
        String username = "";
        String i_email = null;
        String course = null;
        String section = null;
        String course_code = null;
        long course_id;
        ArrayList<CourseData> courseData = new ArrayList<>();

        for (Entity result : pq.asIterable()) {
            i_email = (String)result.getProperty("email");
            course = (String)result.getProperty("name");
            section = (String)result.getProperty("section");
            course_code=(String)result.getProperty("course_code");
            course_id = (long)result.getProperty("id");


            CourseData newData = new CourseData(i_email,username,course,section,course_id);
            newData.setCourseCode(course_code);
            courseData.add(newData);
        }

        data = new CourseDataHelper(email);
        data.setCourseList(courseData);
        System.out.println(data.generateJSON());
        return data;
    }

    public static  ArrayList<Object> getCourseID(String instructor_email, String course, String section){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter email_filter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, instructor_email.trim());
        Query.Filter name_filter = new Query.FilterPredicate("name", Query.FilterOperator.EQUAL, course.trim());
        Query.Filter section_filter = new Query.FilterPredicate("section", Query.FilterOperator.EQUAL, section.trim());
        Query.CompositeFilter composite_filter = Query.CompositeFilterOperator.and(email_filter, name_filter, section_filter);

        Query q = new Query("Course").setFilter(composite_filter);
        PreparedQuery pq = datastore.prepare(q);

        int course_size = pq.asList(FetchOptions.Builder.withDefaults()).size();
        if (course_size != 1) {
            return null;
        }

        Entity e = pq.asList(FetchOptions.Builder.withDefaults()).get(0);
        long id = (long)e.getProperty("id");

        ArrayList<Object> temp = new ArrayList<>();
        temp.add(0,id);

        return temp;
    }


    public static EnrollmentData getEnrolledUser(String email, String course , Key forumkey){

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter groupkey_filter = new Query.FilterPredicate("forum_key", Query.FilterOperator.EQUAL, forumkey);
        Query.Filter email_filter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email);
        Query.CompositeFilter enroll_filter = Query.CompositeFilterOperator.and(groupkey_filter,email_filter);
        Query q = new Query("Enrollment").setFilter(enroll_filter);
        PreparedQuery pq = datastore.prepare(q);

        List<Entity> enrollment_list = pq.asList(FetchOptions.Builder.withDefaults());
        EnrollmentData enrolled_user = null;
        for (int i = 0; i < enrollment_list.size(); i++) {
            Entity enrollment_data = enrollment_list.get(i);
            String enrollment_email = (String) enrollment_data.getProperty("email");
            if (enrollment_email.equalsIgnoreCase(email)) {
                enrolled_user = new EnrollmentData(enrollment_data.getKey().getId(), enrollment_email,
                        (String) enrollment_data.getProperty("username"), course, (long) enrollment_data.getProperty("access"), (long)enrollment_data.getProperty("u_set"));
                break;
            }
        }

        return enrolled_user;
    }

    public static int enrollUser(Key forum_key, String email, String nickname, int access,long course_id){


        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter forum_id_filter = new Query.FilterPredicate("forum_key", Query.FilterOperator.EQUAL, forum_key);
        Query.Filter email_filter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email);
        Query.CompositeFilter email_forumid_filter = Query.CompositeFilterOperator.and(forum_id_filter,email_filter);
        Query q = new Query("Enrollment").setFilter(email_forumid_filter);
        PreparedQuery pq = datastore.prepare(q);

        int student_size = pq.asList(FetchOptions.Builder.withDefaults()).size();
        if(student_size > 1){
            return DiscussionBoardUtils.DATABASE_ERROR;
        }

        if(student_size == 1){
            return DiscussionBoardUtils.DUPLICATE_ENTRY;
        }

        Entity user = new Entity("Enrollment");
        user.setProperty("email",email);
        user.setProperty("forum_key",forum_key);
        user.setProperty("access",access);
        user.setProperty("course_id",course_id);

        if(nickname == null || nickname.trim().length() == 0){
            user.setProperty("username",email);
            user.setProperty("u_set",0);
        }
        else{
            user.setProperty("username",nickname);
            user.setProperty("u_set",1);
        }
        datastore.put(user);

        return DiscussionBoardUtils.SUCCESS;

    }







}



