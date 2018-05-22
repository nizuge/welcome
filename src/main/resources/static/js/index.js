$(document).ready(function() {
    connect();
});

var stompClient = null;
var topic = 'D4:12:BB:13:5E:B8';
function connect() {
    var socket = new SockJS('gee');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/'+topic, function(data){
            var jsonData = JSON.parse(data.body);
            if(jsonData['pic'] !== undefined){
                $("#view").attr("src","data:image;base64,"+jsonData['pic']);
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
