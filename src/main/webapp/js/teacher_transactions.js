/**
 * Created by Xiangbin on 4/20/2017.
 */

//this page is all the JS function to support teacher home page
$(document).ready(function () {

        alert("Heyyyy  No");
        var JSON_list_items;
        var $url = "/LoadCourse.htm";

        $.ajax({
            method: 'get',
            url: $url,
            dataType: 'json',


            //this function will bring json file and load course list to JSP file on teacher page
            success: function (course_table) {
                var item_area = $('#item_area');
                console.log("Get Courses :Success");
                JSON_list_items = course_table;

                var list_data = "";

                $.each(JSON_list_items, function (i, item) {
                    list_data += '<tr><td>' + item.crsName + '</td><td>' + "123" + '</td><td>' + "123" + '</td><td>' + item.crsCode + '</td></tr>';
                });
                item_area.html(list_data);
            },
            error: function () {
                console.log("Loading the course table: Aw, It didn't connect to the servlet :(");
            }
        });


    // this function will control the button in the teacher home page to create a new class
    $('body').on('click', '#add_course_submit', function (e) {
        var course_name = $('#course_name_text').val().trim();
        var course_dis = $('#course_dis_text').val().trim();
        console.log("123");
        console.log(course_dis);
        var url = "/addCourse.htm?crsName=" +course_name + "&description=" + course_dis;

        $.ajax({
            method: 'post',
            url: url,
            dataType: 'text',
            success: function (h) {
                if(course_name.length==0||course_dis.length==0){
                    alert("Can not create course with course name or course description is empty")
                    console.log("ADD COURSE ERROR");
                }
                else{
                    clear_form_data();
                    load_course_item('ADD_COURSE');
                    console.log("ADD ITEM: SUCCESS");
                }
            },
            error: function () {
                console.log("Add Item Failure: Aw, It didn't connect to the servlet :(");
            }
        });
    });

    //this function will bring json file and load course list to JSP file on teacher page
    function load_course_item(type){
        console.log("Type:" + type);

        $.ajax({
            method: 'get',
            url: "/LoadCourse.htm",
            dataType: 'json',

            success: function (course_table) {
                var item_area=$('#item_area');
                console.log("Get Courses :Success");
                JSON_list_items = course_table;
                var list_data="";
                $.each( JSON_list_items, function (i, item) {
                    list_data += '<tr><td>' + item.crsName + '</td><td>' +"123" + '</td><td>' + "123" + '</td><td>' + item.crsCode + '</td></tr>';
                });
                item_area.html(list_data);
            },
            error: function () {
                console.log("Loading the course table: Aw, It didn't connect to the servlet :(");
            }
        });
    }

    function clear_form_data() {
        var item_area=$('#item_area');
        console.log("Clear all data");
        item_area.val("");
        $('#course_name_text').val("");
        $('#course_dis_text').val("");
    }


})



