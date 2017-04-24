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
    <script src="teacher.js"></script>

    <script type="text/javascript">
        window.onload = function(){

            document.getElementById("addUser").onclick = function(){

                //获取姓名 电话 住址
                var name = document.getElementById("name").value;



                //把数据封装到数组中，然后遍历数组，取出每个值，在循环中创建单元格
                var info =[name];

                //创建行
                var tr = document.createElement("tr");

                for( var i=0;i<info.length ; i++){
                    var td = document.createElement("td");
                    td.innerHTML = info[i];
                    //把单元格添加到行上
                    tr.appendChild(td);
                }

                //需要单独创建删除的单元格
                var del = document.createElement("td");

                //创建一个超链接
                var a = document.createElement("a");
                var linkText = document.createTextNode("my title text");
                a.appendChild(linkText);
                a.title = "my title text";
                a.href = "TeacherInformationPage.htm";
                a.innerHTML = "Class Information";



                del.appendChild(a);

                tr.appendChild(del);

                document.getElementById("usertable").appendChild(tr);
            }
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
                Name of Course: <input type="text" name="name" id="name" />&nbsp;&nbsp;
                <br>
            <div class="form-group">
                <label for="comment">Course Description:</label>
                <textarea name="classDis" class="form-control" rows="20" id="comment"></textarea>
            </div>
                <button id="addUser" >SUBMIT</button>
                <button type="button" class="btn" data-dismiss="modal">CLOSE</button>
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
                            <table id="usertable" border="1" cellpadding="5" cellspacing="0">
                                <tr>
                                    <th>Name of Course</th>
                                </tr>

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
        <script src="js/app.js"></script>

</body>


</html>