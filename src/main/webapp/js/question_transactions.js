/**
 * Created by Raymond on 4/7/2017.
 */

/**
 * This javascript will handle all the actions a user will perform on the question webpage
 */

$.noConflict();
$(document).ready(function () {

    $(".sub_box").hide();

    var JSON_list_items;
    var JSON_list_items1;
    var $url = "/PopulateQues.htm";
    var i = 0;
    var $url1 = "/PopulateQuesInfo.htm";

    $.ajax({
        method: 'get',
        url: $url,
        dataType: 'json',
        success: function (question_table) {
            console.log("Get Questions :Success");
            JSON_list_items = question_table;
            $('.ques_num').text(JSON_list_items.Questions[i].id);
            $('.the_ques').text(JSON_list_items.Questions[i].problem);
            localStorage.setItem("questionNum",i);

            $.ajax({
                method: 'get',
                url: $url1,
                dataType: 'json',
                success: function (questionInfo_table) {
                    console.log("Get QuestionsInfo :Success");
                    JSON_list_items1 = questionInfo_table;
                    $('#myText').val(JSON_list_items1.QuestionsInfo[i].studentanswer);
                },
                error: function () {
                    console.log("Get QuestionsInfo Failure: Aw, It didn't connect to the servlet :(");
                }
            });

        },
        error: function () {
            console.log("Get Questions Failure: Aw, It didn't connect to the servlet :(");
        }
    });

    /**
     * Allows the user to move onto the next question
     */
    $('body').on('click','.next_box', function(e) {

        clearText();
        nextQues();
    });

    /**
     * Allows the user to move onto the prev question
     */
    $('body').on('click','.prev_box', function(e) {

        clearText();
        if (i == 0){
            i = 2;
        }
        else{
            i--;
        }
        $('.ques_num').text(JSON_list_items.Questions[i].id);
        $('.the_ques').text(JSON_list_items.Questions[i].problem);
        $('#myText').val(JSON_list_items1.QuestionsInfo[i].studentanswer);
        localStorage.setItem("questionNum",i);
    });

    /**
     * Allows the user to see the solution
     */
    $('body').on('click','.sol_box', function(e){

        $('#solution').text(JSON_list_items.Questions[i].solution);
        $('#dialog').dialog();
    });

    /**
     * Allows the user to check their code that they wrote to see if it has errors or it is fine
     */
    $('body').on('click','.compile_box', function(e){

        $(".sub_box").show();

        $('#output').val('');
        var text = $('#myText').val();
        var num = localStorage.getItem("questionNum");
        //text = text.replace(/\r?\n/g, '<br />');
        var $url = "/Compile.htm?" + "&text=" + text + "&num=" + num;

        $.ajax({
            method: 'post',
            url: $url,
            dataType: 'text',
            success: function (text_field){
                if(text_field != ""){
                    var new_text = text_field.replace(/<br\s?\/?>/g,"\n");
                    $('#output').html(new_text);
                    $('#dialog2').dialog({height:'auto',width:'auto'});
                }
                else{
                    $('#output').html("Could Not Compile Your Code!");
                    $('#dialog2').dialog({height:'auto',width:'auto'});
                }

            },
            error: function () {
                console.log("Compile Failure: Aw, It didn't connect to the servlet :(");
            }
        });

    });

    /**
     * Allows the user to submit their code  so that they can get a point value associated with each
     * question
     */
    $('body').on('click','.sub_box', function(e){

        $('#code').text( $('#myText').val());
        $( "#dialog1" ).dialog({
            resizable: false,
            height: "auto",
            width: 400,
            modal: true,
            buttons: {
                "Yes": function() {
                    $( this ).dialog( "close" );
                    var text = $('#myText').val();
                    var num = localStorage.getItem("questionNum");
                    //text = text.replace(/\r?\n/g, '<br />');
                    var $url = "/Compile.htm?" + "&text=" + text + "&num=" + num;
                    clearText();
                    nextQues();
                },
                No: function() {
                    $( this ).dialog( "close" );
                }
            }
        });
    });

    /**
     * Helper function to populate the web page by modifying the elements in the jsp
     */
    function nextQues(){
        if (i == JSON_list_items.Questions.length - 1){
            i = 0;
        }
        else {
            i++;
        }
        $('.ques_num').text(JSON_list_items.Questions[i].id);
        $('.the_ques').text(JSON_list_items.Questions[i].problem);
        $('#myText').val(JSON_list_items1.QuestionsInfo[i].studentanswer);
        localStorage.setItem("questionNum",i);
    }

    /**
     * Helper function to clear what a user has written in a text area
     */
    function clearText(){

        $('#myText').val('');
    }

});