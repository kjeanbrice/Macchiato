<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html class="no-js" lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Macchiato</title>
    <link rel="stylesheet" href="../../css/app.css">

    <link rel="stylesheet" href="css/assignment.css">
    <link rel="stylesheet" href="../../icons/foundation-icons/foundation-icons/foundation-icons.css"/>
    <link href="https://fonts.googleapis.com/css?family=Cormorant" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
</head>

<body>
<!-- TOP BAR -->
<div class="top-bar-container" data-sticky-container>

    <div class="top-bar">
        <div class="top-bar-left">
            <ul class="menu">
                <li><img src="images/Macchiato.png" alt="JTE Image" height="32" width="32"></li>
                <li><a href="#" class="logo-name">Macchiato</a></li>
            </ul>
        </div>
        <div class="top-bar-right">
            <ul class="menu">
                <li><a href="/Student.htm" class="link">Home</a></li>
                <li><a href="/CourseInfo.htm" class="link">Course Info</a></li>
                <li><a href="/Discussionboard.htm" class="link">Forum</a></li>
                <li><a href="/logout.htm" class="link">Logout</a></li>
            </ul>
        </div>
    </div>
    <div class="top_bar_container" style="padding:1.5%;" >
        <div class="top_bar">
            <div class="top-bar-left">
                <div id="stud_name" ></div>
            </div>
            <div class="top-bar-right">
                <ul class="dropdown menu" data-dropdown-menu>
                    <li>
                        <a id = "nav_assignments"  href="javascript:void(0)" class="scroll-nav link-highlight">Courses</a>
                        <ul id = "load_assignment_area" class="menu forum-links submenu-test">

                        </ul>
                        <form id="link-form" action=" " method="get" style="display: none;">
                            <input name = "crsName" id = "form-crsName" type = "text" style="display:none;">
                            <input name = "crsCode" id = "form-crsCode" type = "text" style="display:none;">
                        </form>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>

<!-- BODY -->
<div class="row">
    <div class="medium-12 medium-centered columns" style="background: #FFFFFF; box-shadow: 1px 2px 4px rgba(0, 0, 0, .5);" >
        <h3 id="class_title" style="text-align: center; padding: 25px 0px 0px"></h3>
        <h4 id="class_section" style="text-align: center; padding: 25px 0px 0px"></h4>
        <hr  style="margin: 0px 10px;">
        <h4 style="padding: 0px 25px;">Course Description</h4>
        <p id ="crs_desc" style="padding: 0px 50px;"></p>
    </div>
</div>
<!-- END BODY -->




<section class="row">
    <div  class="small-4 medium-4 large-4 columns">

    </div>
    <div class="small-4 medium-4 large-4 columns">

    </div>
    <div class="small-4 medium-4 large-4 columns">

    </div>

</section>

<!-- MODAL AREA-->
<div id="enroll_modal" class="reveal" data-reveal data-animation-in="slide-in-down fast" data-animation-out="slide-out-up fast" >
    <a name ="close-enroll" class="close-button" data-close>&#215;</a>
    <div class = "modal-heading-form">
        <h3 class ="modal-heading-text">Enroll in a Course</h3>
    </div>
    <label>
        <div><span class = "label-style-modal">Course Code</span></div>
        <div><span id="no_crs_code"class = "label-style-modal" style="color:red"></span></div>
        <input name="enroll_crs_code" id="enroll_crs_code" type="text" placeholder="Type your course code here..." >
    </label>


    <div id="enroll_submit" class="menu modal-area ">
        <span class="modal-btn-full" >Enroll</span>
    </div>

</div>

<div id="notification_modal" class="reveal" data-reveal data-animation-in="slide-in-down fast" data-animation-out="slide-out-up fast" >
    <a id ="close-notification" class="close-button" data-close>&#215;</a>
    <div class = "modal-heading-form">
        <h3 id="notification_title" class ="modal-heading-text"></h3>
    </div>
    <div style=" text-align: center"><span id="notification_txt"class = "label-style-modal" ></span></div>

    <div class="menu modal-area " data-close>
        <span class="modal-btn-full">Close</span>
    </div>
</div>
<!-- END MODAL AREA-->


<!-- SCRIPTS TO POPULATE LISTS-->
<script id= "course-list-template" type="text/x-handle-template">

    {{#each Courses}}
    <li class="list_file" data-crsName ="{{name}}" data-crsCode="{{course_code}}">
        <a href="javascript:void(0)">{{name}}</a>
    </li>
    {{else}}
    {{/each}}
    <li>
        <a id="new_enroll" href="#" data-open="enroll_modal">Enroll</a>
    </li>


</script>

<script src="libs/handlebars-v4.0.5.js" type="text/javascript"></script>
<script src="js/vendor/jquery.js"></script>
<!--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>-->
<!--<script src="https://code.jquery.com/jquery-3.1.1.min.js" type="text/javascript"></script>-->
<script src="js/stud_crs_info_transactions.js"></script>
<script src="js/vendor/what-input.js"></script>
<script src="js/vendor/foundation.js"></script>
<script src="js/app.js"></script>

<script>
    $(document).ready(function() {
        $(document).foundation();
    })
</script>

</body>


</html>
