package com.macchiato.utility;

import com.google.appengine.api.datastore.*;
import com.macchiato.beans.CourseBean;


import java.util.ArrayList;

/**
 * Created by Xiangbin on 4/26/2017.
 */
public class TeachersUtils {

    //helping to find all the course that created by this teacher,
    // when you inputs a email it will return all the Coursebean list Created by this teacher
    public static ArrayList<CourseBean> isOwned(String email){
        System.out.print(123);
        String CrsName;
        ArrayList<CourseBean> classList=new ArrayList<CourseBean>();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


        Query.Filter CrsCode_filter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email);
        Query q = new Query("Course").setFilter(CrsCode_filter);
        PreparedQuery pq = datastore.prepare(q);
        int numberOfClass = pq.asList(FetchOptions.Builder.withDefaults()).size();
        if (numberOfClass == 0) {
            System.out.println("There is no class that you own");
        }else{
            for (Entity result : pq.asIterable()) {
                Key key = (Key) result.getProperty("crsCode");
                if(key==null){
                    System.out.print("no key");
                    break;
                }
                try {
                    Entity Course = datastore.get(key);
                    email = (String)Course.getProperty("email");
                    CrsName = (String)Course.getProperty("crsName");
                    CourseBean newBean =new CourseBean();
                    newBean.setInstrEmail(email);
                    newBean.setCrsName(CrsName);
                    newBean.setCrsCode(key.toString());
                    classList.add(newBean);
                } catch (EntityNotFoundException e) {
                    System.out.println("Can't find group with key:" + key);
                }
            }
        }
        return classList;
    }

    //this function helps generate  CourseBean list to a List of json type of string
    public static String CourseListJson(ArrayList<CourseBean> courseList){
        String outputString = "[";
        if (courseList.size()<=0){
            return  "[]";
        }
        for(int i =0;i<courseList.size();i++) {
            if(i==courseList.size()-1){
                outputString+=courseList.get(i).generateJSON()+"]";
            }else{
                outputString += courseList.get(i).generateJSON() + ",";
            }
        }
        return outputString;
    }
}
