/**
 * Created by Karl Jean-Brice
 */
$(document).ready(function () {

    //show_welcome_area();
    //hide_list_area();
    //check_user_status();


    $(location).attr('href');

    //pure javascript
    var pathname = window.location.pathname;

    // to show it in an alert window
    //alert(location.protocol + "//" + location.host);

    function load_forum_list() {
        var forum_area = $('#load_forum_area');
        var hostname = window.location.host;
        forum_area.html("<li><a href='javascript:void(0)'>Searching...</a></li>");

        var $url = "/getcourselist.htm";
        $.ajax({
            method: 'get',
            url: $url,
            dataType: 'json',
            success: function (course_list) {
                console.log("COURSE LIST:Success");
                var JSON_course_list = course_list;
                console.log(JSON_course_list);
                var source = $('#discussion-list-template').html();
                console.log(source);
                var forum_list_template = Handlebars.compile(source);
                console.log(forum_list_template);
                var list_data = forum_list_template(JSON_course_list);

                forum_area.html(list_data);
                console.log(list_data);
            },
            error: function () {
                console.log("Loading Course list: Aw, It didn't connect to the servlet :(");
            }

        });
    }


    /*
    $('body').on('click', 'li.list_file', function (e) {
        var file_row = $(this);
        var list_name = file_row.attr("list_name");
        var owner_name = file_row.attr("owner_name");

        var notification_textarea = $('#notification_textarea');
        var notification_title = $('#notification_title');

        var url = "/loadlist.htm?listName=" + list_name + "&owner=" + owner_name;
        $.ajax({
            method: 'get',
            url: url,
            dataType: 'text',
            success: function (loadlist_status) {
                console.log(loadlist_status);
                if (loadlist_status.trim() === "SUCCESS") {
                    hide_welcome_area();
                    hide_list_area();
                    //notification_title.html("Load Status");
                    //notification_textarea.text("\"" + list_name + "\" has loaded successfully.");
                    //$('#notification_modal').modal('show');
                    load_table_items("LOADING");

                    show_list_area();
                }
                else {
                    console.log("LOAD FAILURE: This is strange. I wonder why it failed.");
                }

            },
            error: function () {
                console.log("LOAD LIST FAILURE: Aw, It didn't connect to the servlet :(");
            }
        });


    });*/



    $('body').on('click mouseover', '#nav_forum', function () {
        load_forum_list();
    });


    $('body').on('click', 'li[data-list-email][data-course]', function () {
        //data-list_name="{{instructorEmail}}" data-course
        var i_email = $(this).attr('data-list-email');
        var course = $(this).attr('data-course');

        if(!i_email || i_email.trim().length === 0 || !course || course.trim().length === 0){
                return;
        }


        var form_iemail = $('#form-iemail');
        var form_course = $('#form-course');

        var link_form = $('#link-form');

        form_iemail.attr("value",i_email.trim());
        form_course.attr("value",course.trim());
        var url = location.protocol + "//" + location.host + "/discussionboard.htm";
        link_form.attr("action",url);
        link_form.submit();
    });







});


