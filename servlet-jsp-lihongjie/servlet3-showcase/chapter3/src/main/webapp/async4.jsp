<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
</head>
<body>

<script src="${pageContext.request.contextPath}/static/jquery.1.10.2.js"></script>
<script type="text/javascript">

    function callback(msg) {
        var div = document.createElement("div");
        div.innerHTML = msg;
        document.body.appendChild(div);
    }

    var url = "${pageContext.request.contextPath}/async4";

    function longPolling(url, calback) {
        $.ajax({
            url: url,
            async: true,
            cache: false,
            global: false,
            timeout: 30 * 1000,
            success: function (data,status,request) {
                callback(request.responseText);

                data = null;
                status = null;
                request = null;
                setTimeout(
                        function() {longPolling(url, callback);},
                        10
                );
            },
            error: function (xmlHR, textStatus, errorThrown) {
                xmlHR = null;
                textStatus = null;
                errorThrown = null;

                setTimeout(
                        function() {longPolling(url, callback);},
                        5 * 1000
                );
            }
        });
    }

    longPolling(url, callback);
</script>

</body>
</html>