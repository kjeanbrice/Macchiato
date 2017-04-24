/**
 * Created by li on 4/5/2017.
 */

$(document).ready(function () {
    console.log("test");
    load_student();
    // Loads in the student information.
    function load_student() {
        var $url = "/LoadStudent.htm";
        $.ajax({
            method: 'get',
            url: $url,
            dataType: 'json',
            success: function (stud_user) {
                console.log("Get Email:Success");
                var JSON_stud_user = stud_user;
                // Gets CSE114
                $('#stud_name').text(JSON_stud_user.Student[1].Courses[0].crsCode);
            },
            error: function () {
                console.log("Email Failure: Aw, It didn't connect to the servlet :(");
            }
        });
        console.log("Load Student is done");
    }

    $('body').on('click', '#enroll_submit', function (e) {
        var crsCode = $('#txt_stud_crs_code').val().trim();
        var url = "/Enrollment.htm?crscode=" + crsCode;
        var course = '<a href="#">';
        course += crsCode;
        course += '</a>';
        var list = $('#crs_list');
        list.append(course);

        $('#close-enroll').click();
        console.log(url);
        console.log(course);
    });



});