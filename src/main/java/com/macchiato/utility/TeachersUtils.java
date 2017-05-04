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
        String CrsCode;
        String Crsdis;
        ArrayList<CourseBean> classList=new ArrayList<CourseBean>();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


        Query.Filter email_filter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email);
        Query q = new Query("Course").setFilter(email_filter);
        PreparedQuery pq = datastore.prepare(q);
        int numberOfClass = pq.asList(FetchOptions.Builder.withDefaults()).size();
        System.out.println("number of course;"+numberOfClass);
        if (numberOfClass == 0) {
            System.out.println("There is no class that you own");
        }else{
            for (Entity result : pq.asIterable()) {
                    email = (String)result.getProperty("email");
                    CrsName = (String)result.getProperty("crsName");
                    CrsCode=(String)result.getProperty("crsCode");
                    Crsdis=(String)result.getProperty("description");
                    CourseBean newBean =new CourseBean();
                    newBean.setInstrEmail(email);
                    newBean.setCrsName(CrsName);
                    newBean.setCrsCode(CrsCode);
                newBean.setDescription(Crsdis);
                    classList.add(newBean);
            }
        }


        System.out.println("number of class in the list:" + classList.size());
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
