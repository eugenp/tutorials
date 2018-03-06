<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="footer">
    页面尾部
</div>
<script src="${pageContext.request.contextPath}/static/jquery.1.10.2.js"></script>
<script src="${pageContext.request.contextPath}/static/jquery.pjax.js"></script>
<script>
    $(function () {
        $(document).pjax("a[data-pjax]", "#container", {timeout : 3000});
        $(document).on('pjax:send', function() {
            $('#loading').show()
        })
        $(document).on('pjax:complete', function() {
            $('#loading').hide()
        })
    });
</script>
</body>
</html>