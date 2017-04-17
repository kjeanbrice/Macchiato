<%--
  Created by IntelliJ IDEA.
  User: Raymond
  Date: 4/6/2017
  Time: 3:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html class="no-js" lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Macchiato</title>
    <link rel="stylesheet" href="css/app.css">
    <link rel="stylesheet" href="icons/foundation-icons/foundation-icons/foundation-icons.css"/>
    <link rel="stylesheet" href="css/assignment.css">
    <link href="https://fonts.googleapis.com/css?family=Cormorant" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">


</head>

<body>
<!-- TOP BAR -->
<div class="top-bar-container" data-sticky-container>
    <div class="sticky" data-sticky data-options="anchor: page; marginTop: 0; stickyOn: small;">
        <div class="top-bar">
            <div class="top-bar-left">
                <ul class="menu">
                    <li><img src="images/mklogo.png" alt="JTE Image" height="32" width="32"></li>
                    <li><a href="#" class="logo-name">Macchiato</a></li>

                </ul>
            </div>
            <div class="top-bar-right">
                <ul class="menu">
                    <li><a href="/Student.htm" class="link">Home</a></li>
                    <li><a href="/Discussionboard.htm" class="link">Forum</a></li>
                    <li><a href="/Student.htm" class="link">Assignments</a></li>
                    <li><a href="/logout.htm" class="link">Logout</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>


<!-- MODAL AREA -->
<div class="student_bar">
	<span id="stu_name">
        STUDENT: Raymond Xue
	</span>
    <span class="class_dropdown">
     <button class="dropbtn">Class</button>
  <div class="dropdown-content">
    <a href="#">CSE114</a>
    <a href="#">CSE214</a>
    <a href="#">CSE219</a>
  </div>
	</span>

    <span class="ques_list">
        <img src="images/barline.png" width="50" height="50" class="quesbtn">
	<div class="question-content">
    <a href="#">Question 1</a>
    <a href="#">Question 2</a>
    <a href="#">Question 3</a>
  </div>
    </span>
</div>
<div class="ques_box">
    <div class="ques_num">Question 1</div>
    <div class="the_ques">Initialize the variable i as an integer with a value of 1.</div>
    <button class="sol_box">VIEW SOLUTION</button>
    <div id="dialog" style="display: none"; title="Solution">
        <p id = "solution" >This is the default dialog which is useful for displaying information. The dialog window can be moved, resized and closed with the 'x' icon.</p>
    </div>
    <textarea name="Text1" id="myText" cols="40" rows="5"></textarea>
    <button class="sub_box">SUBMIT</button>
    <div class = "prev_next_box">
    <span class = "prev_space"><button class="prev_box">PREV</button>
</span>
        <span class = "next_space"><button class="next_box">NEXT</button>
</span>
    </div>

</div>


<!-- END MODAL AREA -->


<section class="row">
    <div class="small-4 medium-4 large-4 columns">

    </div>
    <div class="small-4 medium-4 large-4 columns">

    </div>
    <div class="small-4 medium-4 large-4 columns">

    </div>

</section>


<script src="https://code.jquery.com/jquery-3.1.1.min.js" type="text/javascript"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="js/app.js"></script>
<script src="js/question_transactions.js"></script>
</body>


</html>

<html>
<head>
    <title>Title</title>
</head>
<body>

</body>
</html>
