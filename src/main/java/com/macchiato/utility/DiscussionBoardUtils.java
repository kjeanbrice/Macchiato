package com.macchiato.utility;

import com.google.appengine.api.datastore.*;
import com.macchiato.general.discussiondata.CourseData;
import com.macchiato.general.discussiondata.CourseDataHelper;
import com.macchiato.general.discussiondata.EnrollmentData;
import com.macchiato.general.discussiondata.PostData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Karl on 3/30/2017.
 */
public class DiscussionBoardUtils {

    public final static int SUCCESS = 0;
    public final static int ENROLLED = 1;
    public final static int NOT_ENROLLED = 2;
    public final static int NOT_LOGGED_IN = 3;
    public final static int DUPLICATE_STUDENT = 4;
    public final static int EMPTY_PARAMETERS = 5;
    public final static int BOARD_NOT_FOUND = 6;
    public final static int DATABASE_ERROR = 7;
    public final static int POST_NOT_FOUND = 8;
    public final static int NO_ACCESS = 9;
    public final static int INVALID_ARGS = 10;



    public static int isEnrolled(Key groupKey, String email){

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter groupkey_filter = new Query.FilterPredicate("groupkey", Query.FilterOperator.EQUAL, groupKey);
        Query.Filter email_filter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email);
        Query.CompositeFilter email_groupkey_filter = Query.CompositeFilterOperator.and(groupkey_filter,email_filter);
        Query q = new Query("Enrollment").setFilter(email_groupkey_filter);
        PreparedQuery pq = datastore.prepare(q);

        int student_size = pq.asList(FetchOptions.Builder.withDefaults()).size();
        if(student_size > 1){
            return DUPLICATE_STUDENT;
        }

        if(student_size == 0){
            return NOT_ENROLLED;
        }

        return ENROLLED;
    }

    public static CourseDataHelper retrieveCourseList(String email) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter email_filter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email);
        Query q = new Query("Enrollment").setFilter(email_filter);
        PreparedQuery pq = datastore.prepare(q);

        CourseDataHelper data = null;
        String i_email = null;
        String course = null;
        String username = null;
        ArrayList<CourseData> courseData = new ArrayList<>();

        for (Entity result : pq.asIterable()) {
            Key key = (Key) result.getProperty("groupkey");
            try {
                Entity group = datastore.get(key);
                i_email = (String)group.getProperty("email");
                course = (String)group.getProperty("course");

                email_filter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, i_email);
                q = new Query("Enrollment").setFilter(email_filter);
                pq = datastore.prepare(q);

                int list_size = pq.asList(FetchOptions.Builder.withDefaults()).size();
                if(list_size == 1){
                    Entity enroll_result = pq.asIterable().iterator().next();
                    username = (String)enroll_result.getProperty("username");
                }else{
                    return null;
                }

                CourseData newData = new CourseData(i_email,username,course);
                courseData.add(newData);


            } catch (EntityNotFoundException e) {
                System.out.println("Can't find group with key:" + key);
            }


        }

        data = new CourseDataHelper(email);
        data.setCourseList(courseData);
        System.out.println(data.generateJSON());
        return data;
    }

    public static int enrollUser(Key groupKey, String email, String nickname, int access){

        final int ALREADY_ENROLLED = 0;
        final int SUCCESS = 1;
        final int DUPLICATE_ENTRY = 2;

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter groupkey_filter = new Query.FilterPredicate("groupkey", Query.FilterOperator.EQUAL, groupKey);
        Query.Filter email_filter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email);
        Query.CompositeFilter email_groupkey_filter = Query.CompositeFilterOperator.and(groupkey_filter,email_filter);
        Query q = new Query("Enrollment").setFilter(email_groupkey_filter);
        PreparedQuery pq = datastore.prepare(q);

        int student_size = pq.asList(FetchOptions.Builder.withDefaults()).size();
        if(student_size > 1){
            return DUPLICATE_ENTRY;
        }

        if(student_size == 1){
            return ALREADY_ENROLLED;
        }

        Entity user = new Entity("Enrollment");
        user.setProperty("email",email);
        user.setProperty("groupkey",groupKey);
        user.setProperty("access",access);

        if(nickname == null || nickname.trim().length() == 0){
            user.setProperty("username",email);
        }
        else{
            user.setProperty("username",nickname);
        }
        datastore.put(user);

        return SUCCESS;

    }


    public static Key getGroupKey(String instructor_email, String course){

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Key key = new KeyFactory.Builder("User",instructor_email).addChild("Course",course.trim()).addChild("Group",course.trim()).getKey();
        Query.Filter group_key_filter = new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.EQUAL, key);
        Query q = new Query("Group").setFilter(group_key_filter);
        PreparedQuery pq = datastore.prepare(q);
        int group_size = pq.asList(FetchOptions.Builder.withDefaults()).size();
        if(group_size == 0){
            return null;
        }

        if(group_size > 1){
            return null;
        }

        Entity group = pq.asIterable().iterator().next();
        System.out.println(group.getKey());

        return group.getKey();
    }

    public static int updateUsername(Key groupkey, String email, String username){

        final int ALREADY_ACTIVE = 0;
        final int NOT_VALID = 1;
        final int NOT_ENROLLED = 2;
        final int DATABASE_ERROR = 3;
        final int SUCCESS = 4;

        Entity user;
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        if(username == null || username.trim().length() == 0){
            return NOT_VALID;
        }

            Query.Filter groupkey_filter = new Query.FilterPredicate("groupkey", Query.FilterOperator.EQUAL, groupkey);
            Query.Filter username_filter = new Query.FilterPredicate("username", Query.FilterOperator.EQUAL, username.trim());
            Query.Filter not_email_filter = new Query.FilterPredicate("email", Query.FilterOperator.NOT_EQUAL, email);

            Query.CompositeFilter new_username_filter = Query.CompositeFilterOperator.and(groupkey_filter,username_filter,not_email_filter);
            Query q = new Query("Enrollment").setFilter(new_username_filter);
            PreparedQuery pq = datastore.prepare(q);

            int list_size = pq.asList(FetchOptions.Builder.withDefaults()).size();
            if(list_size > 0){
                return ALREADY_ACTIVE;
            }

            Query.Filter email_filter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email);
            q = new Query("Enrollment").setFilter(email_filter);
            pq = datastore.prepare(q);

            int enroll_email_size = pq.asList(FetchOptions.Builder.withDefaults()).size();
            if(enroll_email_size == 0){
                return NOT_ENROLLED;
            }

            if(enroll_email_size > 1){
                return DATABASE_ERROR;
            }

            user = pq.asIterable().iterator().next();
            user.setProperty("username",username);
            datastore.put(user);
            return SUCCESS;
    }

    public static EnrollmentData getEnrolledUser(String email, String course , Key groupkey){

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query.Filter groupkey_filter = new Query.FilterPredicate("groupkey", Query.FilterOperator.EQUAL, groupkey);
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
                        (String) enrollment_data.getProperty("username"), course, (long) enrollment_data.getProperty("access"));
                break;
            }
        }

        return enrolled_user;
    }

    public static void createDummyDiscussionData() {


        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query q = new Query("Dummydata");
        PreparedQuery pq = datastore.prepare(q);
        int list_size = pq.asList(FetchOptions.Builder.withDefaults()).size();
        if (list_size > 0) {
            return;
        }



        String instructor_email = "teacher1@example.com";
        String course = "cse114";

        GenUtils.createInstructor(instructor_email);

        Key key = new KeyFactory.Builder("User", instructor_email).getKey();
        Entity course_entity = new Entity("Course", course.trim(), key);
        course_entity.setProperty("course", course.trim());
        course_entity.setProperty("email", instructor_email);
        datastore.put(course_entity);

        //Key parentKey = KeyFactory.createKey(key,"Group");

        createDummyDiscussionBoard(course, instructor_email);

        Key group_key = DiscussionBoardUtils.getGroupKey(instructor_email,course);
        if(group_key == null){
            return;
        }

        createDummyPost(course,instructor_email,instructor_email,"Lorem ipsum dolor sit amet. ","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris et sapien eros. Aliquam metus dolor. ");

        String student_email1 = "student1@example.com";
        DiscussionBoardUtils.enrollUser(group_key,"student1@example.com","Jake Wilson", 0);
        DiscussionBoardUtils.enrollUser(group_key,"student2@example.com","Brian Tanner ", 0);
        DiscussionBoardUtils.enrollUser(group_key,"student3@example.com","Emily Roberts", 0);
        createDummyPost(course,instructor_email,"student3@example.com","Lorem ipsum dolor sit amet, consectetur. ","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce turpis erat, accumsan nec magna eu. ");
        createDummyComment(course,instructor_email,"student2@example.com"," Duis non laoreet enim, a dignissim tortor. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Sed non libero non leo pulvinar cursus quis id metus. Donec tristique venenatis eleifend. Vestibulum hendrerit finibus arcu et ornare. Cras ut mi non diam ornare feugiat. In facilisis nulla nec ante hendrerit, eu pharetra dolor malesuada. Donec quis gravida ligula. Duis purus lorem, ornare ac libero quis, congue blandit risus. Ut commodo a turpis id viverra. Curabitur aliquet, purus quis sagittis feugiat, est nisl sollicitudin urna, a fringilla nunc neque eget diam. ");
        createDummyComment(course,instructor_email,"student1@example.com", "Praesent turpis risus, varius sed enim quis, pulvinar pellentesque magna. Ut fringilla nulla a orci tempor iaculis at non erat. Cras enim quam, ornare sed fermentum vel, iaculis ac tellus. Cras pharetra metus libero, ut varius ligula commodo ac. Aliquam augue sapien, sodales vel sollicitudin nec, porta eu nibh. Curabitur eget porta est, eu semper lectus. Cras et diam quis velit laoreet posuere. Integer ac dolor lorem. Aenean elementum porta velit, et pharetra ante molestie nec. Donec rutrum velit vel lorem semper, pharetra efficitur risus pharetra. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla facilisi. Quisque eu molestie justo. Fusce lacinia magna consectetur lorem faucibus porta. Pellentesque luctus justo in elit consectetur, quis volutpat ligula mattis. ");


        Entity dummyData = new Entity("Dummydata");
        dummyData.setProperty("set",1);
        datastore.put(dummyData);

    }


    public static String createDummyDiscussionBoard(String course, String email) {

        if (email == null || email.trim().isEmpty()) {
            return "F";
        }

        if (GenUtils.isInstructor(email)) {
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

            DiscussionBoardUtils.enrollUser(group.getKey(), email, "Ted Wilson", 1);


            return "S";
        } else {
            return "F";
        }
    }

    public static int createDummyPost(String course, String instructor_email, String post_email, String post_title, String post_content) {


        if (instructor_email == null || course == null || post_title == null || post_content == null
                || course.trim().length() == 0 || instructor_email.trim().length() == 0 ||
                post_title.trim().length() == 0 || post_content.trim().length() == 0) {
            return DiscussionBoardUtils.EMPTY_PARAMETERS;
        }

        Key groupkey = DiscussionBoardUtils.getGroupKey(instructor_email.trim(), course.trim());
        if (groupkey == null) {
            return DiscussionBoardUtils.BOARD_NOT_FOUND;
        }

        int enrollment_status = DiscussionBoardUtils.isEnrolled(groupkey, post_email);
        switch (enrollment_status) {
            case DiscussionBoardUtils.NOT_ENROLLED:
                return DiscussionBoardUtils.NOT_ENROLLED;
            case DiscussionBoardUtils.DUPLICATE_STUDENT:
                return DiscussionBoardUtils.DATABASE_ERROR;
            case DiscussionBoardUtils.ENROLLED:
                DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
                Entity user = new Entity("Post");
                user.setProperty("title", post_title);
                user.setProperty("content", post_content);
                user.setProperty("email", post_email);
                user.setProperty("groupkey", groupkey);
                user.setProperty("timestamp", new Date().getTime());
                datastore.put(user);
                return DiscussionBoardUtils.SUCCESS;
        }
        return DiscussionBoardUtils.DATABASE_ERROR;
    }

    public static void createDummyComment(String course, String instructor_email, String comment_email, String comment_content)  {


        if (instructor_email == null || course == null || comment_content == null || comment_email == null
                || course.trim().length() == 0 || instructor_email.trim().length() == 0 ||
                comment_content.trim().length() == 0 || comment_email.trim().length() == 0) {

            return;
        }

        Key groupkey = DiscussionBoardUtils.getGroupKey(instructor_email.trim(), course.trim());
        if (groupkey == null) {
            return;
        }



        int enrollment_status = DiscussionBoardUtils.isEnrolled(groupkey, comment_email);
        switch (enrollment_status) {
            case DiscussionBoardUtils.NOT_ENROLLED:
                break;
            case DiscussionBoardUtils.DUPLICATE_STUDENT:
                break;
            case DiscussionBoardUtils.ENROLLED:
                DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

                Query.Filter groupkey_filter = new Query.FilterPredicate("groupkey", Query.FilterOperator.EQUAL, groupkey);

                PostData postData;
                Query q = new Query("Post").setFilter(groupkey_filter);
                PreparedQuery pq = datastore.prepare(q);

                List<Entity> post_list = pq.asList(FetchOptions.Builder.withDefaults());
                int post_list_size = post_list.size();
                for (int i = 0; i < post_list_size; i++) {
                    Entity post = post_list.get(i);
                    Entity user = new Entity("Comment");
                    user.setProperty("postkey", post.getKey().getId());
                    user.setProperty("content", comment_content);
                    user.setProperty("email", comment_email);
                    user.setProperty("groupkey", groupkey);
                    user.setProperty("timestamp",new Date().getTime());
                    datastore.put(user);
                }
                break;
        }
    }



}
