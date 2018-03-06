<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
</head>
<body>

<script src="${pageContext.request.contextPath}/static/jquery.1.10.2.js"></script>
<script type="text/javascript">

    function callback1(msg) {
        var div = document.createElement("div");
        div.innerHTML = msg;
        document.body.appendChild(div);
    }
</script>
<script type="text/javascript">
var comet = {
    connection   : false,
    iframediv    : false,
    callback     : callback1,
    url          : "${pageContext.request.contextPath}/async3?t={t}",

    initialize: function() {

        var url = comet.url.replace("{t}", new Date().getTime());

        if (navigator.appVersion.indexOf("MSIE") != -1) {
            // For IE browsers
            comet.connection = new ActiveXObject("htmlfile");
            comet.connection.open();
            comet.connection.write(
                    "<html><script>" +
                            "document.domain='" + document.domain + "';" +
                            "</html>");
            comet.connection.close();

            comet.iframediv = comet.connection.createElement("div");
            comet.connection.appendChild(comet.iframediv);
            comet.connection.parentWindow.callback = comet.callback;
            comet.iframediv.innerHTML = "<iframe id='comet_iframe' src='"+url+"'></iframe>";
            var cometIFrame = comet.iframediv.getElementsByTagName("iframe")[0];
            var cometInterval = setInterval(function() {//超时需要手动重连
                if(cometIFrame && cometIFrame.readyState == 'complete') {
                    comet.onUnload();
                    comet.initialize();
                    clearInterval(cometInterval);
                }
            } , 1000);

        } else {//目前测试结果是：超时能自动重连
            // For other browser (Firefox...)
            comet.connection = document.createElement('iframe');
            comet.connection.style.cssText = 'position:absolute;left:-100px;top:-100px;height:1px;width:1px;visibility:hidden';

            comet.connection.onload = function() {
                comet.iframediv = document.createElement('div');
                comet.iframediv.setAttribute('src', url);
                comet.connection.appendChild(comet.iframediv);
                comet.connection.contentWindow.parent.callback = comet.callback;
                comet.iframediv.innerHTML = "<iframe id='comet_iframe' onload=\"try{dd;}catch(e){}this.src='"+url+"'\"></iframe>";
            };
            document.body.appendChild(comet.connection);

        }
    },
    onUnload: function() {
        if (comet.connection) {
            comet.connection = false; // release the iframe to prevent problems with IE when reloading the page
            if (navigator.appVersion.indexOf("MSIE") != -1) {
                try {CollectGarbage();} catch(e) {}
            }
        }
    }
};

$(window).on("load", comet.initialize);
$(window).on("unload", comet.onUnload);

</script>

</body>
</html>