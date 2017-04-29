/**
 * Created by Raymond on 4/7/2017.
 */

/**
 * This javascript will handle all the actions a user will perform on the question webpage
 */
$(document).ready(function () {

    var JSON_list_items;
    var $url = "/PopulateQues.htm";
    var i = 0;

    $.ajax({
        method: 'get',
        url: $url,
        dataType: 'json',
        success: function (question_table) {
            console.log("Get Questions :Success");
            JSON_list_items = question_table;
            $('.ques_num').text(JSON_list_items.Questions[i].id);
            $('.the_ques').text(JSON_list_items.Questions[i].problem);
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

        $('#output').val('');
        var text = $('#myText').val();
        //text = text.replace(/\r?\n/g, '<br />');
        var $url = "/Compile.htm?" + "&text=" + text;

        $.ajax({
            method: 'post',
            url: $url,
            dataType: 'text',
            success: function (text_field){
                var new_text = text_field.replace(/<br\s?\/?>/g,"\n");
                $('#output').html(new_text);
                $('#dialog2').dialog({height:'auto',width:'auto'});
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
        alert(i);
        if (i == JSON_list_items.Questions.length - 1){
            i = 0;
        }
        else {
            i++;
        }
        $('.ques_num').text(JSON_list_items.Questions[i].id);
        $('.the_ques').text(JSON_list_items.Questions[i].problem);
    }

    /**
     * Helper function to clear what a user has written in a text area
     */
    function clearText(){

        $('#myText').val('');
    }

});