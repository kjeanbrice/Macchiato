<%--
  Created by IntelliJ IDEA.
  User: Xiangbin
  Date: 4/21/2017
  Time: 6:24 PM
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
<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">CREATE A CLASS</h4>
            </div>
            <div class="form-group">
                <label for="usr">Name of Class:</label>
                <input type="text" class="form-control" id="usr">
            </div>
            <div class="form-group">
                <label for="comment">Class Information:</label>
                <textarea class="form-control" rows="20" id="comment"></textarea>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-danger navbar-btn" data-toggle="modal" data-target="#CrsCode">CREATE</button>
                <button type="button" class="btn" data-dismiss="modal">CLOSE</button>
            </div>
        </div>
    </div>

</div>

<div class="modal fade" id="CrsCode" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">CLASS CODE:5464YH</h4>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn" data-dismiss="modal">YES</button>
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
                    <thead>
                    <tr>
                        <th>ClassName</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><div class="container">
                            <div class="dropdown">
                                <button class="btn btn-info btn-lg" type="button" id="menu1" data-toggle="dropdown">CSE353
                                    <span class="caret"></span></button>
                                <ul class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="menu1">
                                    <li role="presentation"><a role="menuitem" tabindex="-1" href="ClassInformationPage.html">CLASS INFORMATION</a></li>
                                    <li role="presentation"><a role="menuitem" tabindex="-1" href="TeacherAssigmentPage.html">ASSIGNMENT</a></li>
                                </ul>
                            </div>
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
        <script src="js/app.js"></script>

</body>


</html>