package com.macchiato.controllers.discussioncontroller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.macchiato.utility.discussiondata.*;
import com.macchiato.utility.DiscussionBoardUtils;
import com.macchiato.utility.GenUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Karl on 3/28/2017.
 */
@Controller
public class DiscussionBoardController {

    public static String createDiscussionBoard(String course) {
        final int STUDENT = 0;
        final int NOT_LOGGED_IN = 1;
        final int INSTRUCTOR = 2;

        int status = (int) (GenUtils.checkCredentials().get(0));
        switch (status) {
            case STUDENT:
                return "F:STUDENT";
            case NOT_LOGGED_IN:
                return "F:LOGIN";
            case INSTRUCTOR:
                User user = GenUtils.getActiveUser();
                if (user == null) {
                    return "F:LOGIN";
                }
                String email = user.getEmail();
                DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
                Key key = new KeyFactory.Builder("User", email).getKey();
                Query.Filter user_key_filter = new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.EQUAL, key);

                Query q = new Query("User").setFilter(user_key_filter);
                PreparedQuery pq = datastore.prepare(q);

                int user_size = pq.asList(FetchOptions.Builder.withDefaults()).size();
                if (user_size != 1) {
                    return "F:DATA_ERROR";
                }


                key = new KeyFactory.Builder("User", email).addChild("Course", course.trim()).getKey();
                Query.Filter course_key_filter = new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.EQUAL, key);
                q = new Query("Course").setFilter(course_key_filter);
                pq = datastore.prepare(q);
                int course_size = pq.asList(FetchOptions.Builder.withDefaults()).size();
                if (course_size != 1) {
                    return "F:DATA_ERROR";
                }

                Key key1 = new KeyFactory.Builder("User", email).addChild("Course", course.trim()).addChild("Group", course.trim()).getKey();
                Query.Filter group_key_filter = new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.EQUAL, key1);
                q = new Query("Group").setFilter(group_key_filter);
                pq = datastore.prepare(q);
                int group_size = pq.asList(FetchOptions.Builder.withDefaults()).size();
                if (group_size != 0) {
                    return "F:DATA_ERROR";
                }

                Entity group = new Entity("Group", course.trim(), key);
                System.out.println("groupkey" + group.getKey());
                group.setProperty("email", email);
                group.setProperty("course", course);
                datastore.put(group);

                DiscussionBoardUtils.enrollUser(group.getKey(), email, user.getNickname(), 1);
                break;
            default:
                System.out.println("Switch Error. This area should have never been reached.");
                break;
        }
        return "S";
    }

    @RequestMapping(value = "getcourselist.htm", produces = "text/html;charset=UTF-8" ,method = RequestMethod.GET)
    public void getCourseList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        User active_user = GenUtils.getActiveUser();
        if(active_user == null){
             out.println("F:NOT_LOGGED_IN");
             return;
        }

        CourseDataHelper data = DiscussionBoardUtils.retrieveCourseList(active_user.getEmail());
        if(data == null){
            out.println("F:DATA_ERROR");
        }
        else {
            out.println(data.generateJSON());
        }

    }


    @RequestMapping(value = "viewtest.htm", produces = "text/html;charset=UTF-8" ,method = RequestMethod.GET)
    public String viewTest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int i = 5;
        return "discussionboard";
    }

    @RequestMapping(value = "discussionboard.htm", method = RequestMethod.POST)
    public ModelAndView loadDiscussionBoard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String instructor_email = request.getParameter("i_email");
        String course = request.getParameter("course");
        ModelAndView model;


        if (instructor_email == null || course == null || course.trim().length() == 0 || instructor_email.trim().length() == 0) {
            model = new ModelAndView("error");
            return model;
        }

        Key groupkey = DiscussionBoardUtils.getGroupKey(instructor_email.trim(), course.trim());
        if (groupkey == null) {
            model = new ModelAndView("error");
            return model;
        }

        User active_user = GenUtils.getActiveUser();
        if (active_user == null) {
            model = new ModelAndView("error");
            return model;
        }

        String email = active_user.getEmail();
        int enrollment_status = DiscussionBoardUtils.isEnrolled(groupkey, email);
        switch (enrollment_status) {
            case DiscussionBoardUtils.NOT_ENROLLED:
                model = new ModelAndView("error");
                return model;
            case DiscussionBoardUtils.DUPLICATE_STUDENT:
                model = new ModelAndView("error");
                return model;
            case DiscussionBoardUtils.ENROLLED:
                model = new ModelAndView("Discussionboard");
                model.addObject("i_email",instructor_email);
                model.addObject("course",course);
                return model;
            default:
                model = new ModelAndView("error");
                return model;
        }
    }

    @RequestMapping(value = "discussion_addpost.htm", method = RequestMethod.GET)
    public void addPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String instructor_email = request.getParameter("i_email");
        String course = request.getParameter("course");
        String post_title = request.getParameter("title");
        String post_content = request.getParameter("content");


        if (instructor_email == null || course == null || post_title == null || post_content == null
                || course.trim().length() == 0 || instructor_email.trim().length() == 0 ||
                post_title.trim().length() == 0 || post_content.trim().length() == 0) {
            out.println(DiscussionBoardUtils.EMPTY_PARAMETERS);
            return;
        }

        Key groupkey = DiscussionBoardUtils.getGroupKey(instructor_email.trim(), course.trim());
        if (groupkey == null) {
            out.println(DiscussionBoardUtils.BOARD_NOT_FOUND);
            return;
        }

        User active_user = GenUtils.getActiveUser();
        if (active_user == null) {
            out.println(DiscussionBoardUtils.NOT_LOGGED_IN);
            return;
        }

        String email = active_user.getEmail();
        //DiscussionBoardUtils.enrollUser(groupkey,"kjeanbri@yahoo.com","Jake Wilson ", 0);
        //DiscussionBoardUtils.updateUsername(groupkey,"kjeanbri@yahoo.com", "Karl Wilson");
        //DiscussionBoardUtils.updateUsername(groupkey,email,"Karl Wilson");
        //DiscussionBoardUtils.updateUsername(groupkey,email,"Paul Fodor");
        int enrollment_status = DiscussionBoardUtils.isEnrolled(groupkey, email);
        switch (enrollment_status) {
            case DiscussionBoardUtils.NOT_ENROLLED:
                out.println(DiscussionBoardUtils.NOT_ENROLLED);
                return;
            case DiscussionBoardUtils.DUPLICATE_STUDENT:
                out.println(DiscussionBoardUtils.DATABASE_ERROR);
                return;
            case DiscussionBoardUtils.ENROLLED:
                DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
                Entity user = new Entity("Post");
                user.setProperty("title", post_title);
                user.setProperty("content", post_content);
                user.setProperty("email", email);
                user.setProperty("groupkey", groupkey);
                user.setProperty("timestamp",new Date().getTime());
                datastore.put(user);
                out.println(DiscussionBoardUtils.SUCCESS);
                break;
        }
    }


    @RequestMapping(value = "discussion_addcomment.htm", method = RequestMethod.GET)
    public void addComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();


        String instructor_email = request.getParameter("i_email");
        String course = request.getParameter("course");
        String post_id = request.getParameter("post_id");
        String comment_content = request.getParameter("content");


        if (instructor_email == null || course == null || comment_content == null || post_id == null
                || course.trim().length() == 0 || instructor_email.trim().length() == 0 ||
                comment_content.trim().length() == 0 || post_id.trim().length() == 0) {
            out.println(DiscussionBoardUtils.EMPTY_PARAMETERS);
            return;
        }

        Key groupkey = DiscussionBoardUtils.getGroupKey(instructor_email.trim(), course.trim());
        if (groupkey == null) {
            out.println(DiscussionBoardUtils.BOARD_NOT_FOUND);
            return;
        }

        User active_user = GenUtils.getActiveUser();
        if (active_user == null) {
            out.println(DiscussionBoardUtils.NOT_LOGGED_IN);
            return;
        }

        String email = active_user.getEmail();
        DiscussionBoardUtils.enrollUser(groupkey, "kjeanbri@yahoo.com", "Jake Wilson ", 0);
        //DiscussionBoardUtils.updateUsername(groupkey,"kjeanbri@yahoo.com", "Karl Wilson");
        //DiscussionBoardUtils.updateUsername(groupkey,email,"Karl Wilson");
        //DiscussionBoardUtils.updateUsername(groupkey,email,"Paul Fodor");
        int enrollment_status = DiscussionBoardUtils.isEnrolled(groupkey, email);
        switch (enrollment_status) {
            case DiscussionBoardUtils.NOT_ENROLLED:
                out.println(DiscussionBoardUtils.NOT_LOGGED_IN);
                break;
            case DiscussionBoardUtils.DUPLICATE_STUDENT:
                out.println(DiscussionBoardUtils.DUPLICATE_STUDENT);
                break;
            case DiscussionBoardUtils.ENROLLED:
                DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
                long id = Long.parseLong(post_id);
                Key post_key = KeyFactory.createKey("Post", id);

                try {
                    Entity valid_post = datastore.get(post_key);
                } catch (EntityNotFoundException e) {
                    out.println(DiscussionBoardUtils.POST_NOT_FOUND);
                    return;
                }

                Entity user = new Entity("Comment");
                user.setProperty("postkey", post_key.getId());
                user.setProperty("content", comment_content);
                user.setProperty("email", email);
                user.setProperty("groupkey", groupkey);
                user.setProperty("timestamp",new Date().getTime());
                datastore.put(user);
                out.println(DiscussionBoardUtils.SUCCESS);
                break;
        }
    }

    @RequestMapping(value = "discussion_deletepost.htm", method = RequestMethod.GET)
    public void deletePost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String instructor_email = request.getParameter("i_email");
        String course = request.getParameter("course");
        String post_id = request.getParameter("post_id");


        if (instructor_email == null || course == null || post_id == null) {
            out.println(DiscussionBoardUtils.EMPTY_PARAMETERS);
            return;
        }

        Key groupkey = DiscussionBoardUtils.getGroupKey(instructor_email.trim(), course.trim());
        if (groupkey == null) {
            out.println(DiscussionBoardUtils.BOARD_NOT_FOUND);
            return;
        }

        User active_user = GenUtils.getActiveUser();
        if (active_user == null) {
            out.println(DiscussionBoardUtils.NOT_LOGGED_IN);
            return;
        }

        String email = active_user.getEmail();
        int enrollment_status = DiscussionBoardUtils.isEnrolled(groupkey, email);
        switch (enrollment_status) {
            case DiscussionBoardUtils.NOT_ENROLLED:
                out.println(DiscussionBoardUtils.NOT_ENROLLED);
                return;
            case DiscussionBoardUtils.DUPLICATE_STUDENT:
                out.println(DiscussionBoardUtils.DATABASE_ERROR);
                return;
            case DiscussionBoardUtils.ENROLLED:
                EnrollmentData user = DiscussionBoardUtils.getEnrolledUser(email,course,groupkey);
                if(user.getAccess().equals("INSTRUCTOR")){
                    try {
                        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
                        long id = Long.parseLong(post_id);
                        Key post_key = KeyFactory.createKey("Post", id);

                        Entity valid_post = datastore.get(post_key);
                        Query.Filter postid_filter = new Query.FilterPredicate("postkey", Query.FilterOperator.EQUAL, id);
                        Query q = new Query("Comment").setFilter(postid_filter);
                        PreparedQuery pq = datastore.prepare(q);

                        List<Entity> comment_list = pq.asList(FetchOptions.Builder.withDefaults());
                        int comment_list_size = comment_list.size();
                        for (int i = 0; i < comment_list_size; i++) {
                            datastore.delete(comment_list.get(i).getKey());
                        }
                        datastore.delete(post_key);
                    } catch (EntityNotFoundException e) {
                        out.println(DiscussionBoardUtils.POST_NOT_FOUND);
                        return;
                    }
                    out.println(DiscussionBoardUtils.SUCCESS);
                }
                else{
                    out.println(DiscussionBoardUtils.NO_ACCESS);
                }
                break;
        }
        return;
    }


    @RequestMapping(value = "discussion_deletecomment.htm", method = RequestMethod.GET)
    public void deleteComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String instructor_email = request.getParameter("i_email");
        String course = request.getParameter("course");
        String comment_id = request.getParameter("comment_id");


        if (instructor_email == null || course == null || comment_id == null) {
            out.println(DiscussionBoardUtils.EMPTY_PARAMETERS);
            return;
        }

        Key groupkey = DiscussionBoardUtils.getGroupKey(instructor_email.trim(), course.trim());
        if (groupkey == null) {
            out.println(DiscussionBoardUtils.BOARD_NOT_FOUND);
            return;
        }

        User active_user = GenUtils.getActiveUser();
        if (active_user == null) {
            out.println(DiscussionBoardUtils.NOT_LOGGED_IN);
            return;
        }

        String email = active_user.getEmail();
        int enrollment_status = DiscussionBoardUtils.isEnrolled(groupkey, email);
        switch (enrollment_status) {
            case DiscussionBoardUtils.NOT_ENROLLED:
                out.println(DiscussionBoardUtils.NOT_ENROLLED);
                return;
            case DiscussionBoardUtils.DUPLICATE_STUDENT:
                out.println(DiscussionBoardUtils.DATABASE_ERROR);
                return;
            case DiscussionBoardUtils.ENROLLED:
                EnrollmentData user = DiscussionBoardUtils.getEnrolledUser(email,course,groupkey);
                if(user.getAccess().equals("INSTRUCTOR")){
                    try {
                        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
                        long id = Long.parseLong(comment_id);
                        Key comment_key = KeyFactory.createKey("Comment", id);

                        Entity valid_comment = datastore.get(comment_key);
                        datastore.delete(comment_key);
                    } catch (EntityNotFoundException | NumberFormatException e) {
                        out.println(DiscussionBoardUtils.POST_NOT_FOUND);
                        return;
                    }
                    out.println(DiscussionBoardUtils.SUCCESS);
                }
                else{
                    out.println(DiscussionBoardUtils.NO_ACCESS);
                }
                break;
        }
        return;
    }


    @RequestMapping(value = "discussion_editpost.htm", method = RequestMethod.GET)
    public void editPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String instructor_email = request.getParameter("i_email");
        String course = request.getParameter("course");
        String post_id = request.getParameter("post_id");
        String post_title = request.getParameter("title");
        String post_content = request.getParameter("content");


        if (instructor_email == null || course == null || post_id == null || post_title == null || post_content == null
                || instructor_email.trim().isEmpty() || course.trim().isEmpty() || post_id.trim().isEmpty()
                || post_title.trim().isEmpty() || post_content.trim().isEmpty() ) {
            out.println(DiscussionBoardUtils.EMPTY_PARAMETERS);
            return;
        }

        Key groupkey = DiscussionBoardUtils.getGroupKey(instructor_email.trim(), course.trim());
        if (groupkey == null) {
            out.println(DiscussionBoardUtils.BOARD_NOT_FOUND);
            return;
        }

        User active_user = GenUtils.getActiveUser();
        if (active_user == null) {
            out.println(DiscussionBoardUtils.NOT_LOGGED_IN);
            return;
        }

        String email = active_user.getEmail();
        int enrollment_status = DiscussionBoardUtils.isEnrolled(groupkey, email);
        switch (enrollment_status) {
            case DiscussionBoardUtils.NOT_ENROLLED:
                out.println(DiscussionBoardUtils.NOT_ENROLLED);
                return;
            case DiscussionBoardUtils.DUPLICATE_STUDENT:
                out.println(DiscussionBoardUtils.DATABASE_ERROR);
                return;
            case DiscussionBoardUtils.ENROLLED:
                EnrollmentData user = DiscussionBoardUtils.getEnrolledUser(email,course,groupkey);
                    try {
                        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
                        long id = Long.parseLong(post_id);
                        Key post_key = KeyFactory.createKey("Post", id);

                        Entity valid_post = datastore.get(post_key);
                        String post_email = (String)valid_post.getProperty("email");
                        if(user.getAccess().equals("INSTRUCTOR") || post_email.equalsIgnoreCase(email)){
                            valid_post.setProperty("title",post_title);
                            valid_post.setProperty("content",post_content);
                            datastore.put(valid_post);
                            out.println(DiscussionBoardUtils.SUCCESS);
                        }
                        else{
                            out.println(DiscussionBoardUtils.NO_ACCESS);
                        }
                    } catch (EntityNotFoundException | NumberFormatException e) {
                        out.println(DiscussionBoardUtils.POST_NOT_FOUND);
                        return;
                    }
                break;
        }
        return;
    }


    @RequestMapping(value = "discussion_editcomment.htm", method = RequestMethod.GET)
    public void editComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String instructor_email = request.getParameter("i_email");
        String course = request.getParameter("course");
        String comment_id = request.getParameter("comment_id");
        String comment_content = request.getParameter("content");


        if (instructor_email == null || course == null || comment_id == null || comment_content == null
                || instructor_email.trim().isEmpty() || course.trim().isEmpty() || comment_id.trim().isEmpty()
                || comment_content.trim().isEmpty() ) {
            out.println(DiscussionBoardUtils.EMPTY_PARAMETERS);
            return;
        }

        Key groupkey = DiscussionBoardUtils.getGroupKey(instructor_email.trim(), course.trim());
        if (groupkey == null) {
            out.println(DiscussionBoardUtils.BOARD_NOT_FOUND);
            return;
        }

        User active_user = GenUtils.getActiveUser();
        if (active_user == null) {
            out.println(DiscussionBoardUtils.NOT_LOGGED_IN);
            return;
        }

        String email = active_user.getEmail();
        int enrollment_status = DiscussionBoardUtils.isEnrolled(groupkey, email);
        switch (enrollment_status) {
            case DiscussionBoardUtils.NOT_ENROLLED:
                out.println(DiscussionBoardUtils.NOT_ENROLLED);
                return;
            case DiscussionBoardUtils.DUPLICATE_STUDENT:
                out.println(DiscussionBoardUtils.DATABASE_ERROR);
                return;
            case DiscussionBoardUtils.ENROLLED:
                EnrollmentData user = DiscussionBoardUtils.getEnrolledUser(email,course,groupkey);
                try {
                    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
                    long id = Long.parseLong(comment_id);
                    Key comment_key = KeyFactory.createKey("Comment", id);

                    Entity valid_comment = datastore.get(comment_key);
                    String post_email = (String)valid_comment.getProperty("email");
                    if(user.getAccess().equals("INSTRUCTOR") || post_email.equalsIgnoreCase(email)){
                        valid_comment.setProperty("content",comment_content);
                        datastore.put(valid_comment);
                        out.println(DiscussionBoardUtils.SUCCESS);
                    }
                    else{
                        out.println(DiscussionBoardUtils.NO_ACCESS);
                    }
                } catch (EntityNotFoundException | NumberFormatException e) {
                    out.println(DiscussionBoardUtils.POST_NOT_FOUND);
                    return;
                }
                break;
        }
        return;
    }


    @RequestMapping(value = "discussion_voterequest.htm", method = RequestMethod.GET)
    public void voteRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String instructor_email = request.getParameter("i_email");
        String course = request.getParameter("course");
        String request_for = request.getParameter("for");
        String request_type = request.getParameter("type");
        String request_id = request.getParameter("id");


        if (instructor_email == null || course == null || request_id == null || request_type == null || request_for == null
                || instructor_email.trim().isEmpty() || course.trim().isEmpty() || request_id.trim().isEmpty() || request_type.trim().isEmpty() || request_for.trim().isEmpty()) {
            out.println(DiscussionBoardUtils.EMPTY_PARAMETERS);
            return;
        }

        Key groupkey = DiscussionBoardUtils.getGroupKey(instructor_email.trim(), course.trim());
        if (groupkey == null) {
            out.println(DiscussionBoardUtils.BOARD_NOT_FOUND);
            return;
        }

        User active_user = GenUtils.getActiveUser();
        if (active_user == null) {
            out.println(DiscussionBoardUtils.NOT_LOGGED_IN);
            return;
        }

        String email = active_user.getEmail();
        int enrollment_status = DiscussionBoardUtils.isEnrolled(groupkey, email);
        switch (enrollment_status) {
            case DiscussionBoardUtils.NOT_ENROLLED:
                out.println(DiscussionBoardUtils.NOT_ENROLLED);
                return;
            case DiscussionBoardUtils.DUPLICATE_STUDENT:
                out.println(DiscussionBoardUtils.DATABASE_ERROR);
                return;
            case DiscussionBoardUtils.ENROLLED:
                EnrollmentData user = DiscussionBoardUtils.getEnrolledUser(email,course,groupkey);
                try {
                    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
                    long id = Long.parseLong(request_id);

                    String property_name = null;
                    String entity_kind = null;

                    if(request_for.trim().equalsIgnoreCase("post")){
                        property_name = "postid";
                        entity_kind = "PostLikes";

                    }else if(request_for.trim().equalsIgnoreCase("comment")){
                        property_name = "commentid";
                        entity_kind = "CommentLikes";
                    }
                    else{
                        out.println(DiscussionBoardUtils.INVALID_ARGS);
                        return;
                    }

                    Query.Filter id_filter = new Query.FilterPredicate(property_name, Query.FilterOperator.EQUAL, id);
                    Query.Filter email_filter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email);
                    Query.CompositeFilter email_id_filter = Query.CompositeFilterOperator.and(id_filter,email_filter);
                    Query q = new Query(entity_kind).setFilter(email_id_filter);

                    PreparedQuery pq = datastore.prepare(q);
                    List<Entity> likes_list = pq.asList(FetchOptions.Builder.withDefaults());
                    int likes_list_size = likes_list.size();

                    if(likes_list_size == 0 && request_type.trim().equalsIgnoreCase("like")) {
                        Entity temp = new Entity(entity_kind);
                        temp.setProperty("email", email);
                        temp.setProperty(property_name, id);
                        datastore.put(temp);
                        out.println(DiscussionBoardUtils.SUCCESS);
                    }
                    else if(likes_list_size == 1 && request_type.trim().equalsIgnoreCase("dislike")){
                            Entity temp = likes_list.get(0);
                            datastore.delete(temp.getKey());
                            out.println(DiscussionBoardUtils.SUCCESS);
                    }else if(likes_list_size == 1 && request_type.trim().equalsIgnoreCase("like")){
                        out.println(DiscussionBoardUtils.DUPLICATE_STUDENT);
                    }
                    else if(likes_list_size == 0 && request_type.trim().equalsIgnoreCase("dislike")){
                        out.println(DiscussionBoardUtils.DUPLICATE_STUDENT);
                    }
                    else{
                        out.println(DiscussionBoardUtils.DATABASE_ERROR);
                    }

                } catch (NumberFormatException e) {
                    out.println(DiscussionBoardUtils.POST_NOT_FOUND);
                    return;
                }
                break;
        }
        return;
    }











    @RequestMapping(value = "populate_discussionboard.htm", method = RequestMethod.GET)
    public void populateDiscussionBoard(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();



        String instructor_email = request.getParameter("i_email");
        String course = request.getParameter("course");

        if (instructor_email == null || course == null || course.trim().length() == 0 || instructor_email.trim().length() == 0) {
            out.println(DiscussionBoardUtils.EMPTY_PARAMETERS);
            return;
        }

        Key groupkey = DiscussionBoardUtils.getGroupKey(instructor_email.trim(), course.trim());
        if (groupkey == null) {
            out.println(DiscussionBoardUtils.BOARD_NOT_FOUND);
            return;
        }

        User active_user = GenUtils.getActiveUser();
        if (active_user == null) {
            out.println(DiscussionBoardUtils.NOT_LOGGED_IN);
            return;
        }

        String email = active_user.getEmail();
        int enrollment_status = DiscussionBoardUtils.isEnrolled(groupkey, email);
        switch (enrollment_status) {
            case DiscussionBoardUtils.NOT_ENROLLED:
                out.println(DiscussionBoardUtils.NOT_ENROLLED);
                break;
            case DiscussionBoardUtils.DUPLICATE_STUDENT:
                out.println(DiscussionBoardUtils.DATABASE_ERROR);
                break;
            case DiscussionBoardUtils.ENROLLED:

                try {
                    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
                    Entity group = datastore.get(groupkey);

                    Query.Filter groupkey_filter = new Query.FilterPredicate("groupkey", Query.FilterOperator.EQUAL, groupkey);
                    Query q = new Query("Enrollment").setFilter(groupkey_filter);
                    PreparedQuery pq = datastore.prepare(q);

                    /*Retrieve enrollment list*/
                    List<Entity> enrollment_list = pq.asList(FetchOptions.Builder.withDefaults());
                    int enrollment_list_size = enrollment_list.size();


                    ArrayList<PostData> post_array_list = new ArrayList<>();

                    /*Retrieve data about the creator of the discussion group*/
                    EnrollmentData instructor_data = DiscussionBoardUtils.getEnrolledUser(instructor_email,course,groupkey);

                    /*Retrieve data about the user accessing the discussion board*/
                    EnrollmentData active_user_enrollment_data = DiscussionBoardUtils.getEnrolledUser(email,course,groupkey);

                    /*Retrieve Posts*/
                    PostData postData;
                    q = new Query("Post").setFilter(groupkey_filter);
                    q.addSort("timestamp", Query.SortDirection.DESCENDING);
                    pq = datastore.prepare(q);

                    List<Entity> post_list = pq.asList(FetchOptions.Builder.withDefaults());
                    int post_list_size = post_list.size();
                    int comment_list_size = 0;
                    for (int i = 0; i < post_list_size; i++) {
                        Entity post = post_list.get(i);
                        String post_email = (String) post.getProperty("email");
                        String post_title = (String) post.getProperty("title");
                        String post_content = (String) post.getProperty("content");
                        long post_id =  post.getKey().getId();

                        /*Retrieve details about the user that made this post*/
                        EnrollmentData post_user = DiscussionBoardUtils.getEnrolledUser(post_email,course,groupkey);

                        /*Retrieve the number of "likes" this post has.*/
                        Query.Filter postid_filter = new Query.FilterPredicate("postid", Query.FilterOperator.EQUAL, post_id);
                        q = new Query("PostLikes").setFilter(postid_filter);
                        pq = datastore.prepare(q);
                        int post_likes = pq.countEntities(FetchOptions.Builder.withDefaults());

                        /*Retrieve Comments*/
                        ArrayList<CommentData> comment_array_list = new ArrayList<>();
                        q = new Query("Comment");
                        q.addSort("timestamp", Query.SortDirection.ASCENDING);
                        pq = datastore.prepare(q);
                        List<Entity> comment_list = pq.asList(FetchOptions.Builder.withDefaults());
                        int c_list_size = comment_list.size();
                        for (int j = 0; j < c_list_size; j++) {
                            long comment_post_id = (long) comment_list.get(j).getProperty("postkey");
                            if (comment_post_id == post_id) {
                                comment_list_size = comment_list_size + 1;
                                String comment_email = (String) comment_list.get(j).getProperty("email");
                                long comment_id = comment_list.get(j).getKey().getId();
                                String comment_content = (String) comment_list.get(j).getProperty("content");

                                /*Retrieve details about the user that made this comment*/
                                EnrollmentData comment_user = DiscussionBoardUtils.getEnrolledUser(comment_email,course,groupkey);

                                /*Retrieve the number of "likes" this comment has.*/
                                Query.Filter commentid_filter = new Query.FilterPredicate("commentid", Query.FilterOperator.EQUAL, comment_id);
                                q = new Query("CommentLikes").setFilter(commentid_filter);
                                pq = datastore.prepare(q);
                                int comment_likes = pq.countEntities(FetchOptions.Builder.withDefaults());


                                CommentData temp_comment = new CommentData(comment_id, comment_user, comment_content, comment_likes);
                                temp_comment.determinePermissions(active_user_enrollment_data);
                                comment_array_list.add(temp_comment);
                            }
                        }


                        PostData temp_post = new PostData(post_id, post_user, post_title, post_content, post_likes, comment_array_list);
                        temp_post.determinePermissions(active_user_enrollment_data);
                        post_array_list.add(temp_post);
                    }


                    //Key groupID, String email, String course, String username, String access, ArrayList<PostData> postData
                    DiscussionBoardData discussionBoardData = new DiscussionBoardData(groupkey, enrollment_list_size,
                            post_list_size, comment_list_size,instructor_data, post_array_list,active_user_enrollment_data);

                    out.println(discussionBoardData.generateJSON());
                    System.out.println(discussionBoardData.generateJSON());


                } catch (EntityNotFoundException e) {
                    e.printStackTrace();
                }
                break;


        }


    }
}
