<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${empty layout or layout eq true}">
<div id="footer">
    页面尾部
</div>
<script src="${pageContext.request.contextPath}/static/jquery.1.10.2.js"></script>
<script>
    $(function () {
        var initHistoryState = function(links) {
            links.on("click", function () {
                if (history.pushState) { //支持html5的维护历史记录栈
                    var link = $(this);
                    var url = link.attr("href");
                    var title = link.text();
                    $.ajax({
                        cache : false,
                        url : url,
                        headers : {layout : false}
                    }).success(function(data) {
                        $("#container").html(data);
                        var stateData = {
                            url : url,
                            msg : 'hello'
                        };
                        //将新的地址压入历史记录栈，而且地址栏地址也发生改变
                        //但该页面没有刷新，而是通过ajax取得一个html片段更新的
                        history.pushState(stateData, title, url);
                        //再初始化页面按钮
                        setTimeout(function() {
                            initHistoryState($("#container a"));
                        }, 0);
                    });
                } else {
                    alert("您的浏览器不支持html5的history.pushState");
                }
                return false;
            });
        };
        initHistoryState($("a"));
    });
</script>
</body>
</html>
</c:if>