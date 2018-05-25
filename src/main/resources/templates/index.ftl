<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>迎宾系统</title>
    <link rel="stylesheet" href="/static/css/index.css"/>
    <link rel="stylesheet" href="/static/css/jquery.flipster.css"/>
    <script type="text/javascript" src="/static/js/jquery.min.js"></script>
    <script type="text/javascript" src="/static/js/jquery.flipster.js"></script>
    <script type="text/javascript" src="/static/js/index.js"></script>
    <script src="/static/js/sockjs-0.3.4.js"></script>
    <script src="/static/js/stomp.js"></script>

</head>
<body>

<div id="faceFind" style="display:none" class="zzsc-container">
    <div class="zzsc-content bgcolor-3">
        <div id="Main-Content">
            <div class="Container">
                <div class="flipster">
                    <ul class="flip-items">
                        <li title="Cricket" data-flip-category="Fun Sports">
                            <div class="kc-item">
                                <div class="t_box" id="second"></div>
                            </div>
                        </li>
                        <li>
                            <div class="kc-item">
                                <div class="t_box" id="first"></div>
                            </div>
                        </li>
                        <li title="Baseball" data-flip-category="Boring Sports" >
                            <div class="kc-item">
                                <div class="t_box" id="third"></div>
                            </div>
                        </li>

                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="main">
    <div class="zzc"></div>
    <div class="content">
        <div class="top_box">
            <div class="logo"><img src="/static/img/logo.png"/></div>
        </div>
        <div class="middle_box">
            <div class="video_box">
                <img id="camera_show" src="" height="100%" width="100%"/>
            </div>
            <div class="time_box">
                <div class="mintime_box">
                    <div class="ymd">
                        <span></span> /
                        <span></span> /
                        <span></span>
                    </div>
                    <div class="img_box"><img src="/static/img/plate.png"/></div>
                </div>
                <div class="mintime_box">
                    <div class="hms">
                        <span></span> :
                        <span></span> :
                        <span></span>
                    </div>
                    <div class="img_box"><img src="/static/img/plate.png"/></div>
                </div>
                <div class="mintime_box">
                    <div class="xq"></div>
                    <div class="img_box"><img src="/static/img/plate.png"/></div>
                </div>
                <div class="four">
                    <div class="coordinate">深圳</div>
                    <div class="img_box"><img src="/static/img/plate.png"/></div>
                </div>
            </div>
        </div>
        <div class="bottom_box">
            <input id="camera" type="text"/>
            <button onclick="reConnect()">重连</button>
            深圳市恩钛控股有限公司
        </div>
    </div>
</div>
</body>
</html>