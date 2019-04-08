$(function () {
    //侧边栏点击动画
    $("#hamburger-div").click(function () {
        var width = $("#left-div").width();
        if(parseInt(width) == 200) {
            $("#left-div").animate({
                "width": "54px"
            }, 500);
            $("#right-div").animate({
                "margin-left": "54px"
            }, 500);
            $("#left-ul > div > a > li").css({
                "padding": "0 0 0 20px"
            });
        } else {
            $("#left-div").animate({
                "width": "200px"
            }, 500);
            $("#right-div").animate({
                "margin-left": "200px"
            }, 500);
        }
    });

    /* 搜索框 */
    $("#search-icon").click(function () {
        var width = $("#input-div").width();
        if(parseInt(width) == 0) {
            $("#input-div").animate({
                "width": "200px"
            }, 500)
        } else if($("#search").val() == ""){
            $("#input-div").animate({
                "width": "0"
            }, 500)
        } else {
            var name = $("#search").val();
            $(location).attr("href", "/admin/name?name=" + name);
        }
    });

    /* 菜单 */
    $("#user-avatar").click(function (event) {
        event.stopPropagation();
        $("#menu-ul").slideToggle();
    });
    $(document).click(function (event) {
        var _con = $("#menu-ul");
        if(!_con.is(event.target) && _con.has(event.target).length === 0){
            $("#menu-ul").hide(500);
        }
    })

    $("#search").bind("keypress", function (event) {
        if(event.keyCode == "13"){
            $("#search-icon").click();
        }
    })
});