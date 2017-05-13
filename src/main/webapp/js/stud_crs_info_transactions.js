/**
 * Created by li on 5/12/2017.
 */

$(document).ready(function () {
    var currCrsList;
    load_info("/LoadStudent.htm");

    function load_info($url) {
        $.ajax({
            method: 'get',
            url: $url,
            dataType: 'json',
            success: function (crs_info) {
                console.log("Get Info:Success");
                var JSON_crs_info = crs_info;
                $('#stud_name').text(JSON_crs_info.Student[0].email);
                $('#class_title').text(JSON_crs_info.Student[2].crsName);
                $('#crs_desc').text(JSON_crs_info.Student[2].description);
                currCrsList = JSON_crs_info.Student[1]
                load_course_list(currCrsList);
            },
            error: function () {
                console.log("Get Info Failure: Aw, It didn't connect to the servlet :(");
            }
        });
        console.log("Load Course Info is done");
    }

    /*
     Loads the list of courses the Student is enrolled in
     * */
    function load_course_list(crs_list){
        var assignment_area = $('#load_assignment_area');
        var hostname = window.location.host;
        assignment_area.html("<li><a href='javascript:void(0)'>Searching...</a></li>");
        console.log(crs_list);
        var source = $('#course-list-template').html();
        console.log(source);
        var course_list_template = Handlebars.compile(source);
        console.log(course_list_template);
        var list_data = course_list_template(crs_list);

        assignment_area.html(list_data);
        console.log(list_data);
    }

    $('body').on('click mouseover', '#nav_assignments', function () {
        load_course_list(currCrsList);
    });

    $('body').on('click', 'li[data-crsName][data-crsCode]', function () {
        var crsName = $(this).attr('data-crsName');
        var crsCode = $(this).attr('data-crsCode');

        var form_crsName = $('#form-crsName');
        var form_crsCode = $('#form-crsCode');

        var link_form = $('#link-form');

        form_crsName.attr("value",crsName.trim());
        form_crsCode.attr("value",crsCode.trim());

        var test = link_form.serializeArray();
        console.log(crsName);
        console.log(crsCode);
        var url = "/LoadStudent.htm?crsName=" + crsCode;
        console.log(url);
        load_info(url);

    });


});