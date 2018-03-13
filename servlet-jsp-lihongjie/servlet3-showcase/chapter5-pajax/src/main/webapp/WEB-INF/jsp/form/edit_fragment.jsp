<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form data-pjax action="" method="post">

    <label for="title">标题:</label><input type="text" id="title" name="title"/><br/>

    <input type="submit" value="新增">

</form>
<script>
    $(function () {
        $('form[data-pjax]').on('submit', function(event) {
            $.pjax.submit(event, '#container')
        })
    });
</script>
