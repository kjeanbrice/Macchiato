/**
 * Created by li on 4/5/2017.
 */

$(document).ready(function () {
    console.log("test");

    var $url = "/LoadStudent.htm";
    $.ajax({
        method: 'get',
        url: $url,
        dataType: 'json',
        success: function (stud_user) {
            console.log("Get Email:Success");
            var JSON_stud_user = stud_user;
            $('#stud_name').text(JSON_stud_user.email);
        },
        error: function () {
            console.log("Email Failure: Aw, It didn't connect to the servlet :(");
        }

    });



});