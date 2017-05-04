package com.macchiato.controllers.instructorcontroller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.macchiato.controllers.discussioncontroller.DiscussionBoardController;
import com.macchiato.utility.DiscussionBoardUtils;
import com.macchiato.utility.GenUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

/**
 * Created by Karl on 5/2/2017.
 */
@Controller
public class InstructorController {
    @RequestMapping(value = "addcourse2.htm", produces = "text/html;charset=UTF-8" ,method = RequestMethod.GET)
    public void addCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        String course_name = request.getParameter("course_name");
        String description = request.getParameter("description");
        String section = request.getParameter("section");

        if(course_name == null || section == null || course_name.trim().isEmpty() || section.trim().isEmpty()){
            out.println(GenUtils.EMPTY_PARAMETERS);
            return;
        }

        if(description == null || description.trim().isEmpty()){
            description = "";
        }


        ArrayList<Object> obj = GenUtils.checkCredentials();
        if(obj.get(1) == null){
            out.println(GenUtils.NOT_LOGGED_IN);
            return;
        }


        int credential_status = (int)obj.get(0);
        switch(credential_status){
            case GenUtils.STUDENT:
                out.println(GenUtils.NO_PERMISSION);
                return;
            case GenUtils.INSTRUCTOR:
            case GenUtils.ADMIN:
                DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

                User user = (User)obj.get(1);

                Query.Filter email_filter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, user.getEmail());
                Query.Filter name_filter = new Query.FilterPredicate("name", Query.FilterOperator.EQUAL, course_name);
                Query.Filter section_filter = new Query.FilterPredicate("section", Query.FilterOperator.EQUAL, section);
                Query.CompositeFilter composite_filter = Query.CompositeFilterOperator.and(email_filter,name_filter,section_filter);

                Query q = new Query("Course").setFilter(composite_filter);
                PreparedQuery pq = datastore.prepare(q);

                int course_size = pq.asList(FetchOptions.Builder.withDefaults()).size();
                if(course_size > 0){
                    out.println(GenUtils.DUPLICATE_COURSE);
                    return;
                }

                KeyRange kr = datastore.allocateIds("Course",1);
                Key key = kr.getEnd();

                SecureRandom random = new SecureRandom();
                String course_code = new BigInteger(30, random).toString(32) + key.getId();

                Entity e = new Entity("Course",key);
                e.setProperty("name",course_name);
                e.setProperty("course_code",course_code);
                e.setProperty("description",description);
                e.setProperty("section",section);
                e.setProperty("email",user.getEmail());
                e.setProperty("id",key.getId());
                datastore.put(e);

                int status = DiscussionBoardController.createDiscussionBoard(key.getId());
                if(status != DiscussionBoardUtils.SUCCESS){
                    out.println(GenUtils.FAILURE);
                    return;
                }

                out.println(GenUtils.SUCCESS);
                return;

        }

        out.println(GenUtils.FAILURE);
        return;

    }
}
