<%--
  Created by IntelliJ IDEA.
  User: Xiangbin
  Date: 4/21/2017
  Time: 6:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!doctype html>
<html class="no-js" lang="en">

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
                    <button type="button" class="btn btn-danger navbar-btn" data-toggle="modal" data-target="#myModal">CREATE A CLASS</button>
                </ul>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<<div class="container">
    <h2>ASSIGNMENTS</h2>
    <table class="table">
        <thead>
        <tr>
            <th>Assignments Name</th>
            <th>Due Date</th>
            <th>Grades</th>
        </tr>
        <tr>
            <td><li><a href=# class="link">Assigment 1</a></li></td>
            <td>10/10/2017</td>
            <th><li><a href=# class="link">86%</a></li></th>
        </tr>
        </thead>
        <tbody>
        <tr>
        </tr>
        </tbody>
    </table>
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



