/**
 * Created by Raymond on 4/7/2017.
 */

$(document).ready(function () {

    var i = 0;

    $('body').on('click','.next_box', function(e) {
        if (i == 2){
            i = 0;
        }
        else{
            i++;
        }

        var $url = "/PopulateQues.htm";

        $.ajax({
            method: 'get',
            url: $url,
            dataType: 'json',
            success: function (question_table) {
                console.log("Get List Items:Success");
                var JSON_list_items = question_table;
                $('.ques_num').text(JSON_list_items.Questions[i].id);
                $('.the_ques').text(JSON_list_items.Questions[i].problem);
            },
            error: function () {
                console.log("Add Item Failure: Aw, It didn't connect to the servlet :(");
            }

        });




    });

    $('body').on('click','.prev_box', function(e) {
        if (i == 0){
            i = 2;
        }
        else{
            i--;
        }

        var $url = "/PopulateQues.htm";

        $.ajax({
            method: 'get',
            url: $url,
            dataType: 'json',
            success: function (question_table) {
                console.log("Get List Items:Success");
                var JSON_list_items = question_table;
                $('.ques_num').text(JSON_list_items.Questions[i].id);
                $('.the_ques').text(JSON_list_items.Questions[i].problem);
            },
            error: function () {
                console.log("Add Item Failure: Aw, It didn't connect to the servlet :(");
            }

        });




    });

    $('body').on('click','.sol_box', function(e){
        var $url = "/PopulateQues.htm";

        $.ajax({
            method: 'get',
            url: $url,
            dataType: 'json',
            success: function (question_table) {
                console.log("Get List Items:Success");
                var JSON_list_items = question_table;
                $('#solution').text(JSON_list_items.Questions[i].solution);
                $('#dialog').dialog();
            },
            error: function () {
                console.log("Add Item Failure: Aw, It didn't connect to the servlet :(");
            }
        })
    });
});