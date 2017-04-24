<%--
  Created by IntelliJ IDEA.
  User: Xiangbin
  Date: 4/19/2017
  Time: 3:55 PM
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
    <script src="teacher.js"></script>
    <script>
        function pr() {
            document.getElementById("result").innerHTML = "Class information has been updated \n\n\n\n\n\n              "+"Email: "+
                document.getElementById('email').value + "\n office Hours:" + document.getElementById('officeHours').value
                + "\n Course Information:" + document.getElementById('CourseInfo').value;
        }
    </script>
</head>

<body>
<!-- TOP BAR -->
<div class="top-bar-container" data-sticky-container>
    <div class="sticky" data-sticky data-options="anchor: page; marginTop: 0; stickyOn: small;">
        <div class="top-bar">
            <div class="top-bar-left">
                <ul class="menu">
                    <li><img src="images/mklogo.png" alt="JTE Image" height="35" width="35"></li>
                    <li><a href="#" class="logo-name">Macchiato</a></li>
                </ul>
                <p style="color:#ffffff;"><strong>Instructor: Thomas Shapipo</strong></p>
            </div>
            <div class="top-bar-right">
                <ul class="nav navbar-nav">
                    <p style="color:#ffffff;"><strong>CLASS NAME: CSE353</strong></p>
                    <li class="active"><a href="TeacherHomePage.htm">HOME</a></li>
                    <li><a href="#">FORUM</a></li>
                    <li class="active"><a href="#">DISCUSSION BOARD</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<div class="container">
    <form action="PayslipServlet" method="get">
        Teacher Name:   Thomas Shapipo<br/>
        Email Address:   <input type="text" name="email" id="email"><br/>
        OfficeHours:  <input type="text" name="officeHours" id ="officeHours"><br/>
        Course information:  <input type="text" name="CourseInfo" id ="CourseInfo"><br/>
        <br>
        <input type="button" value="Submit" onClick="pr()">
    </form>
    <span id="result"></span>

</div>


<!-- Latest compiled and minified JavaScript -->
<script src="https://code.jquery.com/jquery-3.2.0.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.1.1.min.js" type="text/javascript"></script>
<script src="bower_components/jquery/dist/jquery.js"></script>
<script src="bower_components/what-input/dist/what-input.js"></script>
<script src="bower_components/foundation-sites/dist/js/foundation.js"></script>
<script src="js/app.js"></script>
</body>


</html>