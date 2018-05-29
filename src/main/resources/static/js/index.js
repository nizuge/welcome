$(document).ready(function() {

    $(".flip-item img ").css("height",$(".flip-item img").width());
    $(".portrait").css("height",$(".flip-item img").width());
    $(function() {
        $(".flipster").flipster({
            itemContainer: 'ul', // Container for the flippin' items.
            itemSelector: 'li', // Selector for children of itemContainer to flip
            style: 'coverflow', // Switch between 'coverflow' or 'carousel' display styles
            start: 'center', // Starting item. Set to 0 to start at the first, 'center' to start in the middle or the index of the item you want to start with.
            enableKeyboard: true, // Enable left/right arrow navigation
            enableMousewheel: true, // Enable scrollwheel navigation (up = left, down = right)
            enableTouch: true, // Enable swipe navigation for touch devices
            enableNav: true, // If true, flipster will insert an unordered list of the slides
            enableNavButtons: true, // If true, flipster will insert Previous / Next buttons
            onItemSwitch: function() {}, // Callback function when items are switches
        });
    });

    time();
    setInterval(time, 1000);
    connect();
});
var stompClient = null;
var topic = 'D4:12:BB:13:5E:B8';
var first, second, third;
var identify_show_timeout;
function connect() {
    var socket = new SockJS('gee');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/'+topic, function(data){
            var jsonData = JSON.parse(data.body);
            if(jsonData['scene'] !== undefined){
                $("#camera_show").attr("src","data:image;base64,"+jsonData['scene']);
            }else {
                if (second) {
                    third = second;
                    second = first;
                } else if (first) {
                    second = first;
                }
                first = jsonData;
                if(jsonData['photo'] !== undefined){
                    renderFace();
                }
            }
        });
    },function(message) {
        console.log(message);
        setTimeout("connect()", 2000);
    });
}
function send() {
    stompClient.send("/app/index/registry", {}, JSON.stringify({ 'name': "jklsdafjklasdfjl" }));
    stompClient.send("/app/hello",{},"hello world!");
}
function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}
function reConnect() {
    topic = $("#camera").val();
    disconnect();
    connect();
}

function renderFace() {
    if(identify_show_timeout){
        clearTimeout(identify_show_timeout);
    }
    if (first) {
        var firstHtml = '<div class="portrait">' +
            '<img src="' + "data:image;base64,"+first['photo'] + '"/></div>' +
            '<div class="t_text">' +
            '<h2>身份:<span>' + first["meta"] + '</span></h2></div>';
        $('#first').html(firstHtml);
    }
    if (second) {
        var secondHtml = '<div class="portrait">' +
            '<img src="' + "data:image;base64,"+second['photo'] + '"/></div>' +
            '<div class="t_text">' +
            '<h2>身份:<span>' + second["meta"] + '</span></h2></div>';
        $('#second').html(secondHtml);
    }
    if (third) {
        var thirdHtml = '<div class="portrait">' +
            '<img src="' + "data:image;base64,"+third['photo'] + '"/></div>' +
            '<div class="t_text">' +
            '<h2>身份:<span>' + third["meta"] + '</span></h2></div>';
        $('#third').html(thirdHtml);
    }
    $('#faceFind').show();
    var e = jQuery.Event("resize");
    $('#faceFind').trigger(e);
    identify_show_timeout = setTimeout(function () {
        $('#faceFind').hide();
    }, 2500);
}


/*============== 时间显示 =================*/
function time() {
    var date = new Date();
    var n = date.getFullYear();
    var y = date.getMonth()+1;
    var t = date.getDate();
    var h = date.getHours();
    var m = date.getMinutes();
    var s = date.getSeconds();

    $('.ymd span').eq(0).html(n);
    $('.ymd span').eq(1).html(y);
    $('.ymd span').eq(2).html(t);
    $('.hms span').eq(0).html(h);
    $('.hms span').eq(1).html(m);
    $('.hms span').eq(2).html(s);
    for (var i = 0; i < $('div').length; i++) {
        if ($('div').eq(i).text().length == 1) {
            $('div').eq(i).html(function(index, html) {
                return 0 + html;
            });
        }
    }
    var mydate = new Date();
    var datetext=["星期天","星期一","星期二","星期三","星期四","星期五","星期六"]
    var xq=mydate.getDay();
    $(".xq").text(datetext[xq]);
    $(".cc-decoration").remove();
}
