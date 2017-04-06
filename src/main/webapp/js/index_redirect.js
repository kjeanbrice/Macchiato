/**
 * Created by Karl on 4/5/2017.
 */
$(document).ready(function () {
    redirect_home();

    function redirect_home() {
        var home_form = $('#home-form');

        var url = "/Home.htm";
        home_form.attr("action", url);
        home_form.submit();
    }
});

