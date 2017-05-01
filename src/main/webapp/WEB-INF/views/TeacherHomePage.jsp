<%--
  Created by IntelliJ IDEA.
  User: Xiangbin
  Date: 4/19/2017
  Time: 3:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Macchiato</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <!-- Optional theme -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
    <link rel="stylesheet" href="css/app.css">
    <link rel="stylesheet" href="icons/foundation-icons/foundation-icons/foundation-icons.css"/>
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
                <p style="color:#ffffff;"><strong>Instructor: Thomas Shapipo</strong></p>
            </div>
            <div class="top-bar-right">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="TeachersPage.html">Home</a></li>
                    <li><a href="#">FORUM</a></li>
                    <li><a href="/logoutTeacher.htm" class="link">Logout</a></li>
                    <button type="button" class="btn btn-danger navbar-btn" data-toggle="modal" data-target="#add_modal">CREATE A CLASS</button>
                </ul>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="add_modal" class="modal fade"
     style="display: none;">
    <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">CREATE A CLASS</h4>
            </div>
                Name of Course: <input id="course_name_text" type="text" name="name" id="name" />&nbsp;&nbsp;
                <br>
            <div class="form-group">
                <label for="comment">Course Description:</label>
                <textarea id="course_dis_text" name="classDis" class="form-control" rows="20" id="comment"></textarea>
            </div>
            <button type="button" class="btn btn-primary pull-right"  id="add_course_submit" data-dismiss="modal" >CREATE</button>
            <button type="button" class="btn btn-primary pull-right" id="close_modal" data-dismiss="modal">CLOSE</button>
            <div class="modal-footer">
            </div>
        </div>
    </div>
</div>

<div class="container-fluid text-center">
    <div class="row content">
        <div class="col-sm-8 text-left">
            <h1>Welcome</h1>
            <p>MACCHIATO is a web-application that provides a platform for Computer Science professor to assign to students to solve.
                Students will be able to ask the professors questions on the Discussion Board anything they need and can talk amongst themselves.</p>
            <hr>
        </div>
        <div class="col-sm-1 sidenav">
            <div class="container">
                <table class="table table-striped">
                    <tbody>
                    <tr>
                        <td><div class="container">
                            <%--<table id="usertable" border="1" cellpadding="5" cellspacing="0">--%>
                                <%--<tr>--%>
                                    <%--<th>Name of Course</th>--%>
                                <%--</tr>--%>
                                <%----%>

                            <%--</table>--%>
                                <table>
                                    <thead>
                                    <tr>
                                        <th>Name of Course</th>
                                        <th>Course Information</th>
                                        <th>Assignments</th>
                                        <th>Course Code</th>
                                    </tr>
                                    </thead>
                                    <tbody id="item_area" class="tbody-default">
                                    </tbody>
                                </table>


                        </div></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://code.jquery.com/jquery-3.2.0.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.1.1.min.js" type="text/javascript"></script>
        <script src="bower_components/jquery/dist/jquery.js"></script>
        <script src="bower_components/what-input/dist/what-input.js"></script>
        <script src="bower_components/foundation-sites/dist/js/foundation.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/mustache.js/2.3.0/mustache.min.js"></script>
        <script src="js/app.js"></script>
        <script src="js/teacher_transactions.js"></script>
</body>


</html>