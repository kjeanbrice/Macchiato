/**
 * Created by Xiangbin on 5/4/2017.
 */
$(document).ready(function () {
    load_question_item("Load the Question");


    // this function will control the button in the teacher home page to create a new assignment
    $('body').on('click', '#add_question_submit', function (e) {
        var problem = $('#question_problem_text').val().trim();
        var solution = $('#question_answer_text').val().trim();
        console.log("ADD ASSIGNEMTN");
        var url = "/addQuestion.htm?problem=" +problem + "&solution=" + solution+"&assignmentKey=" + localStorage.getItem("assignmentKey");

        $.ajax({
            method: 'post',
            url: url,
            dataType: 'text',
            success: function (h) {
                if(problem.length==0||solution.length==0){
                    alert("Can not create question with problem and solution empty")
                    console.log("ADD QUESTION ERROR");
                }
                else{
                    clear_form_data();
                    load_question_item('ADD_QUESTION');
                    console.log("ADD QUESTION: SUCCESS");
                }
            },
            error: function () {
                console.log("Add Item Failure: Aw, It didn't connect to the servlet :(");
            }
        });
    });

});
function clear_form_data(){
    $('#question_problem_text').val("");
    $('#question_answer_text').val("");
    console.log("Clear all data");
}

//this function will bring json file and load course list to JSP file on teacher page
function load_question_item(type){
    console.log("Type:" + type);

    $.ajax({
        method: 'get',
        url: "/LoadQuestion.htm?assignmentKey="+localStorage.getItem("assignmentKey"),
        dataType: 'json',

        success: function (question_table) {
            var item_area=$('#item_area');
            console.log("Get Question :Success");
            JSON_list_items = question_table;
            var list_data="";
            $.each(JSON_list_items, function (i, item) {
                list_data += '<tr><td>' + i + '</td><td>'+ '<button onclick="delete_helper('+'\''+item.id+'\''+')" type="button" class="btn btn-link" >delete</button>'+'</td><td>' +
                    '<button onclick="Question_helper('+'\''+item.id+'\''+')" type="button" class="btn btn-link" >Edit</button>'+'</td><td>'+item.problem+ '</td></tr>';
            });
            item_area.html(list_data);
        },
        error: function () {
            console.log("Loading the course table: Aw, It didn't connect to the servlet :(");
        }
    });
}


function delete_helper(questionkey){
    localStorage.setItem("questionkey",questionkey);
    console.log("Set new value to local Storage "+questionkey);
    var url="/deleteQuestion.htm?questionKey="+questionkey;
    console.log(url);

    $.ajax({
        method: 'post',
        url: url,
        dataType: 'text',
        success: function (h) {
            if(localStorage.getItem("questionkey")==0){
                console.log("ADD QUESTION ERROR");
            }
            else{
                clear_form_data();
                load_question_item('delete_QUESTION');
                console.log("Question Delete: SUCCESS");
            }
        },
        error: function () {
            console.log("Add Item Failure: Aw, It didn't connect to the servlet :(");
        }
    });
}

function Question_helper(questionkey){
    localStorage.setItem("assignmentKey",questionkey);
    console.log("Set new value to local Storage "+questionkey+" ,And junmp to grading page");
     //var url="TeacherGradingPage.htm"
    // location.href =url;
    console.log(localStorage.getItem(questionkey));
}

