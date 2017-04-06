<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!doctype html>
<html class="no-js" lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Macchiato</title>
    <link rel="stylesheet" href="css/app.css">
    <link rel="stylesheet" href="icons/foundation-icons/foundation-icons/foundation-icons.css"/>
    <link href="https://fonts.googleapis.com/css?family=Cormorant" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
</head>

<body id="body-style">
<!-- TOP BAR -->
<div class="top-bar-container " data-sticky-container>
    <div id= "navbar-static" class="sticky" data-sticky data-sticky data-options="stickTo:top; stickyOn:small;">
        <div class="top-bar">
            <div class="top-bar-left">
                <ul class="menu">
                    <li><img src="images/Macchiato.png" alt="JTE Image" height="32" width="32"></li>
                    <li><a href="#" class="scroll-nav logo-name">Macchiato</a></li>

                </ul>
            </div>
            <div class="top-bar-right">
                <ul class="dropdown menu" data-dropdown-menu>
                    <li><a href="/Student.htm" class="scroll-nav link">Home</a></li>
                    <li><a href="/CourseInfo.htm" class="scroll-nav link">Course Info</a></li>
                    <li><a id = "nav_forum"  href="/Discussionboard.htm" class="scroll-nav link-highlight">Forum</a></li>


                    <li><a href="#" class="project-btn-styles scroll-nav" id="create-new-post" data-open="change_post_modal">Create New
                        Post</a></li>
                    <li class="extra-padding-left"><a href="#" class="project-btn-styles scroll-nav"
                                                      id="change-user-name" data-open="change_username_modal">Change User Name</a></li>


                </ul>
            </div>
        </div>
    </div>
</div>

<!-- END TOP BAR -->


<!-- MODAL AREA -->
<div id="change_username_modal" class="reveal" data-reveal data-animation-in="slide-in-down fast" data-animation-out="slide-out-up fast">
    <a class="close-button" data-close>&#215;</a>
    <div class = "modal-heading-form">
        <h3 class ="modal-heading-text">Change Your Username</h3>
    </div>
    <label><span class = "label-style-modal">Username</span>
        <input type="text" placeholder="Type your username here...">
    </label>


    <div class="menu modal-area btn-username-submit">
        <span class="modal-btn-full">Submit Request</span>
    </div>


    <p class="text-center"><a href="#"></a></p>

</div>


<div id="change_post_modal" class="reveal" data-reveal data-animation-in="slide-in-down fast" data-animation-out="slide-out-up fast">
    <a class="close-button" data-close>&#215;</a>
    <div class = "modal-heading-form">
        <h3 class = "modal-heading-text">New Post</h3>
    </div>
    <label><span class = "label-style-modal">Title</span>
        <input type="text" placeholder="Type your post title here...">
    </label>

    <label><span class = "label-style-modal">Content</span>
        <textarea class="height-transition comment_input"
                  placeholder="Write your post content here..."></textarea>
    </label>

    <div class="menu modal-area btn-post-submit">
        <span class="modal-btn-full">Create Post</span>
    </div>


    <p class="text-center"><a href="#"></a></p>

</div>


<div id="edit_post_modal" class="reveal" data-reveal data-animation-in="slide-in-down fast" data-animation-out="slide-out-up fast">
    <a class="close-button" data-close>&#215;</a>
    <div class = "modal-heading-form">
        <h3 class = "modal-heading-text">Modify Post</h3>
    </div>
    <label><span class = "label-style-modal">Title</span>
        <input type="text" placeholder="Type your post title here...">
    </label>

    <label><span class = "label-style-modal">New Content</span>
        <textarea class="height-transition comment_input"
                  placeholder="Write your post content here..."></textarea>
    </label>


    <div class="menu modal-area btn-post-submit">
        <span class="modal-btn-full">Confirm Changes</span>
    </div>


    <p class="text-center"><a href="#"></a></p>

</div>


<div id="edit_comment_modal" class="reveal" data-reveal data-animation-in="slide-in-down fast" data-animation-out="slide-out-up fast">
    <a class="close-button" data-close>&#215;</a>
    <div class = "modal-heading-form">
        <h3 class = "modal-heading-text">Modify Comment</h3>
    </div>

    <label><span class = "label-style-modal">New Content</span>
        <textarea class="height-transition comment_input"
                  placeholder="Write your comment content here..."></textarea>
    </label>

    <div class="menu modal-area btn-post-submit">
        <span class="modal-btn-full">Confirm Changes</span>
    </div>


    <p class="text-center"><a href="#"></a></p>

</div>


<div id="delete_post_modal" class="reveal" data-reveal data-animation-in="slide-in-down fast" data-animation-out="slide-out-up fast">
    <a class="close-button" data-close>&#215;</a>
    <div class = "modal-heading-form">
        <h3 class = "modal-heading-text">Delete Confirmation</h3>
    </div>

    <div class = "text-center">
        <span class = "notification-style-modal">Are you sure you want to delete this post?</span>
    </div>


    <div class="menu modal-area btn-deletepost-submit">
        <span class="modal-btn-full">Delete Post</span>
    </div>


    <p class="text-center"><a href="#"></a></p>

</div>


<div id="delete_comment_modal" class="reveal" data-reveal data-animation-in="slide-in-down fast" data-animation-out="slide-out-up fast">
    <a class="close-button" data-close>&#215;</a>
    <div class = "modal-heading-form">
        <h3 class = "modal-heading-text">Delete Confirmation</h3>
    </div>

    <div class = "text-center">
        <span class = "notification-style-modal">Are you sure you want to delete this comment?</span>
    </div>


    <div class="menu modal-area btn-deletepost-submit">
        <span class="modal-btn-full">Delete Comment</span>
    </div>


    <p class="text-center"><a href="#"></a></p>

</div>

<!-- END MODAL AREA -->



<!-- COURSE INFORMATION SECTION -->
<section id="forum-stats-section">
    <div class="top-bar-container">
        <div class="top-bar">
            <div class="top-bar-center">
                <ul class="menu">
                    <li><a  href="javascript:void(0)" id = "navbar-instructor"  class="scroll-nav">Instructor: Paul Fodor</a></li>
                    <li><a  href="javascript:void(0)" id = "navbar-course" >Class: CSE 114</a></li>
                    <li><a  href="javascript:void(0)" id = "navbar-enrolled" >Enrolled: 30</a></li>
                    <li><a  href="javascript:void(0)" id = "navbar-posts" >Posts: 2</a></li>
                    <li><a  href="javascript:void(0)" id = "navbar-comments" >Comments: 3</a></li>

                </ul>
            </div>
        </div>
    </div>
</section>
<!-- END COURSE INFORMATION SECTION -->


<section class="row">
    <!-- DISCUSSION AREA -->
    <div id="extra-section" class="small-1 medium-1 large-1 columns">
        <span id ="i_email" style="display: none;" >${i_email}</span>
        <span id ="course" style="display: none;" >${course}</span>
    </div>


    <div id="discussion-section" class="small-10 medium-10 large-10 columns">
        <div class="post-area">
            <div class="wrapper">
                <div class="middle_box">
                    <div class="clear"></div>
                    <div class="feed_div">
                        <div id="post_area_template" class="post-area-template">
                            <div class="feed_box clearfix">
                                <div class="feed_left">
                                    <p><img class="userimg" src="images/user_icon.gif"></p>
                                    <p>Ellie&nbsp;Franklin</p>
                                </div>
                                <div class="feed_right">
                                    <span class="h2-title">Assignment One</span>
                                    <span class="h2-title small-padding fi-comments"></span>
                                    <span class="comment-count">2</span>
                                    <span class= "h2-title small-padding fi-arrow-up arrow-up"></span>


                                    <span class="block-style post_refresh">
                                                A purple pig and a green donkey flew a kite in the middle of the night and ended up sunburnt.
                                            </span>
                                    <p class="likebox"><i class="fi-like"></i><span class="like-count">&nbsp;0</span>&nbsp;<span
                                            class="darker-gray">·</span>
                                        <a class="link_btn like_post_btn" post_userid="100004" postid="3333" href="#!">Like</a>&nbsp;<span
                                                class="darker-gray">·</span>
                                        <a class="link_btn dislike_post_btn" post_userid="100004" postid="3333"
                                           href="#!">Dislike</a>&nbsp;<span class="darker-gray">·</span>
                                        <a class="link_btn edit_post_btn" post_userid="100004" postid="3333" href="#!" data-open="edit_post_modal">Edit</a>&nbsp;<span
                                                class="darker-gray">·</span>
                                        <a class="link_btn delete_post_btn" post_userid="100004" data-open="delete_post_modal" postid="3333"
                                           href="#!">Delete</a>
                                    </p>


                                    <div class="comment_div">
                                        <div class=" comment_feed_left">
                                            <p><img class="userimg" src="images/user_icon.gif"></p>
                                            <p>Levi&nbsp;Ross</p>
                                        </div>
                                        <div class="comment_background comment_ele text-left">
                                                 <span class="comment_refresh">The class teacher asks students to name an animal that begins with an “E”. One boy says, “Elephant.”
                                                     Then the teacher asks for an animal that begins with a “T”. The same boy says, “Two elephants.”
                                                     The teacher sends the boy out of the class for bad behavior. After that she asks for an animal beginning with “M”.
                                                     The boy shouts from the other side of the wall: “Maybe an elephant!<br></span>
                                            <p class="likebox"><i class="fi-like"></i><span
                                                    class="like-count">&nbsp;0</span>&nbsp;<span
                                                    class="darker-gray">·</span>
                                                <a class="link_btn like_comment_btn" post_userid="100004"
                                                   postid="3333"
                                                   href="#!">Like</a>&nbsp;<span
                                                        class="darker-gray">·</span>
                                                <a class="link_btn dislike_comment_btn" post_userid="100004"
                                                   postid="3333" href="#!">Dislike</a>&nbsp;<span
                                                        class="darker-gray">·</span>
                                                <a class="link_btn edit_comment_btn" data-open="edit_comment_modal" post_userid="100004"
                                                   postid="3333"
                                                   href="#!">Edit</a>&nbsp;<span
                                                        class="darker-gray">·</span>
                                                <a class="link_btn delete_comment_btn" data-open="delete_comment_modal" post_userid="100004"
                                                   postid="3333" href="#!">Delete</a>
                                            </p>
                                        </div>

                                    </div>


                                    <div class="comment_div">
                                        <div class=" comment_feed_left">
                                            <p><img class="userimg" src="images/user_icon.gif"></p>
                                            <p>Benjamin&nbsp;Franklin</p>
                                        </div>
                                        <div class="comment_background comment_ele text-left">
                                                 <span class="comment_refresh">A Professor was traveling by boat. On his way he asked the sailor:
                                                     “Do you know Biology, Ecology, Zoology, Geography, physiology?
                                                     The sailor said no to all his questions.
                                                     Professor: What the hell do you know on earth. You will die of illiteracy.
                                                     After a while the boat started sinking. The Sailor asked the Professor, do you know swiminology & escapology from sharkology?
                                                     The professor said no. Sailor: “Well, sharkology & crocodilogy will eat your assology, headology & you will dieology because of your mouthology.<br></span>
                                            <p class="likebox"><i class="fi-like"></i><span
                                                    class="like-count">&nbsp;0</span>&nbsp;<span
                                                    class="darker-gray">·</span>
                                                <a class="link_btn like_comment_btn" post_userid="100004"
                                                   postid="3333"
                                                   href="#!">Like</a>&nbsp;<span
                                                        class="darker-gray">·</span>
                                                <a class="link_btn dislike_comment_btn" post_userid="100004"
                                                   postid="3333" href="#!">Dislike</a>&nbsp;<span
                                                        class="darker-gray">·</span>
                                                <a class="link_btn edit_comment_btn" data-open="edit_comment_modal" post_userid="100004"
                                                   postid="3333"
                                                   href="#!">Edit</a>&nbsp;<span
                                                        class="darker-gray">·</span>
                                                <a class="link_btn delete_comment_btn" post_userid="100004"
                                                   postid="3333" href="#!">Delete</a>
                                            </p>
                                        </div>

                                    </div>

                                    <form id="commentform_3332" class="comment_submit" action="javascript:void(0);"
                                          method="get">
                                        <input name="action" value="comment" type="hidden">
                                        <input name="post_id" value="3333" type="hidden">
                                        <div class="comment_submit_section">
                                            <div class="comment_textarea">
                                                        <textarea class="height-transition comment_input"
                                                                  name="comment" id="comment_3332"
                                                                  placeholder="Write your comment here"></textarea>
                                            </div>
                                            <span class="lbl-error hide" name="err_comment">fdfsfdsfds<br></span>
                                            <div class="comment_add extra-padding-bottom">
                                                <ul class="menu">
                                                    <li><span class="comment-btn">Submit</span></li>
                                                    <li> &nbsp;&nbsp;<span class="comment-btn">Cancel</span></li>
                                                </ul>

                                            </div>
                                        </div>
                                    </form>
                                </div>

                            </div>



                            <div class="feed_box clearfix">
                                <div class="feed_left">
                                    <p><img class="userimg" src="images/user_icon.gif"></p>
                                    <p>Ellie&nbsp;Franklin</p>
                                </div>
                                <div class="feed_right">
                                    <span class="h2-title">Question One</span>
                                    <span class=" h2-title small-padding fi-comments"></span>
                                    <span class="comment-count">2</span>
                                    <span class=" h2-title small-padding fi-arrow-up arrow-up"></span>
                                    <span class="post_refresh block-style">
                                                If Purple People Eaters are real… where do they find purple people to eat?
                                            </span>
                                    <p class="likebox"><i class="fi-like"></i><span class="like-count">&nbsp;0</span>&nbsp;<span
                                            class="darker-gray">·</span>
                                        <a class="link_btn like_post_btn" post_userid="100004" postid="3333" href="#!">Like</a>&nbsp;<span
                                                class="darker-gray">·</span>
                                        <a class="link_btn dislike_post_btn" post_userid="100004" postid="3333"
                                           href="#!">Dislike</a>&nbsp;<span class="darker-gray">·</span>
                                        <a class="link_btn edit_post_btn" post_userid="100004" postid="3333" href="#!" data-open="edit_post_modal">Edit</a>&nbsp;<span
                                                class="darker-gray">·</span>
                                        <a class="link_btn delete_post_btn" post_userid="100004" postid="3333"
                                           href="#!">Delete</a>
                                    </p>


                                    <div class="comment_div">
                                        <div class=" comment_feed_left">
                                            <p><img class="userimg" src="images/user_icon.gif"></p>
                                            <p>Levi&nbsp;Ross</p>
                                        </div>
                                        <div class="comment_background comment_ele text-left">
                                                 <span class="comment_refresh">A police officer found a perfect hiding place for watching for speeding motorists.
                                                     One day, the officer was amazed when everyone was under the speed limit, so he investigated and found the problem.
                                                     A 10 years old boy was standing on the side of the road with a huge hand painted sign which said “Radar Trap Ahead.”
                                                     A little more investigative work led the officer to the boy’s accomplice: another boy about 100 yards beyond the radar trap with a sign reading “TIPS” and a bucket at his feet full of change. <br></span>
                                            <p class="likebox"><i class="fi-like"></i><span
                                                    class="like-count">&nbsp;0</span>&nbsp;<span
                                                    class="darker-gray">·</span>
                                                <a class="link_btn like_comment_btn" post_userid="100004"
                                                   postid="3333"
                                                   href="#!">Like</a>&nbsp;<span
                                                        class="darker-gray">·</span>
                                                <a class="link_btn dislike_comment_btn" post_userid="100004"
                                                   postid="3333" href="#!">Dislike</a>&nbsp;<span
                                                        class="darker-gray">·</span>
                                                <a class="link_btn edit_comment_btn" post_userid="100004"
                                                   postid="3333"
                                                   href="#!">Edit</a>&nbsp;<span
                                                        class="darker-gray">·</span>
                                                <a class="link_btn delete_comment_btn" post_userid="100004"
                                                   postid="3333" href="#!">Delete</a>
                                            </p>
                                        </div>

                                    </div>


                                    <form id="commentform_3333" class="comment_submit" action="javascript:void(0);"
                                          method="get">
                                        <input name="action" value="comment" type="hidden">
                                        <input name="post_id" value="3333" type="hidden">
                                        <div class="comment_submit_section">
                                            <div class="comment_textarea">
                                                        <textarea class="height-transition comment_input"
                                                                  name="comment" id="comment_3333"
                                                                  placeholder="Write your comment here"></textarea>
                                            </div>
                                            <span class="lbl-error hide" name="err_comment">fdfsfdsfds<br></span>
                                            <div class="comment_add extra-padding-bottom">
                                                <ul class="menu">
                                                    <li><span class="comment-btn">Submit</span></li>
                                                    <li> &nbsp;&nbsp;<span class="comment-btn">Cancel</span></li>
                                                </ul>

                                            </div>
                                        </div>
                                    </form>
                                </div>

                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div id="stats-section" class="small-1 medium-1 large-1 columns">
    </div>
    <!-- END DISCUSSION AREA -->
</section>





<script src="https://code.jquery.com/jquery-3.1.1.min.js" type="text/javascript"></script>
<script src="libs/handlebars-v4.0.5.js" type="text/javascript"></script>

<!-- Work in Progress
<script src="js/discussion_board_transactions.js" type="text/javascript"></script>
<script src="js/nav_bar_transactions.js" type="text/javascript"></script> -->
<script src="js/vendor/jquery.js"></script>
<script src="js/vendor/what-input.js"></script>
<script src="js/vendor/foundation.js"></script>
<script src="js/app.js"></script>
</body>


</html>
