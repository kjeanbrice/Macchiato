/**
 * Created by li on 4/5/2017.
 */

$(document).ready(function () {

    load_student_home();

    function load_student_home(){
        var user_email=$('#a_email');
        var url ="/loadstudenthome.htm";

        user_email.text("");
        user_email.text(user_status.trim());
        user_email.attr("email_val", user_status.trim());
    }
});