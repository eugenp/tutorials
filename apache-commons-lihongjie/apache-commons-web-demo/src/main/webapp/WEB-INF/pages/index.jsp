<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/public-resources/css/bootstrap.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/public-resources/js/bootstrap.js"></script>
</head>
<body>

<form action="singleUpload" method="post" enctype="multipart/form-data">
<input type="file" class="input-sm" name="file"/>
<input type="submit" value="Upload" class="btn btn-primary btn-sm"/>
</form>


</body>
</html>