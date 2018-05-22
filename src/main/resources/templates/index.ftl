<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>index</title>
    <script type="text/javascript" src="/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/index.js"></script>
    <script src="/static/js/sockjs-0.3.4.js"></script>
    <script src="/static/js/stomp.js"></script>
</head>
<body>
<h1>${test}</h1>
<button onclick="send()">发送</button>
摄像头：<input id="camera" type="text"/>
<button onclick="reConnect()">重连</button>
<img id="view" src=""/>
</body>
</html>