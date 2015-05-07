<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>

<title>Schedule to Reddit</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="<c:url value="/resources/datetime-picker.css" />">
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="<c:url value="/resources/datetime-picker.js" />"></script>
<script src="<c:url value="/resources/validator.js" />"></script>
<style type="text/css">
.btn.disabled{
background-color: #ddd;
border-color: #ddd;
}

.btn.disabled:hover{
background-color: #ddd;
border-color: #ddd;
}
</style>
</head>
<body>
<jsp:include page="header.jsp" />

<div class="container">
<h1>Edit Scheduled Post</h1>
<form action="<c:url value="/updatePost/${post.getId()}" />" method="post" role="form" data-toggle="validator">
<div class="row">
<input type="hidden" name="id" value="${post.getId()}"/>
<div class="form-group">
    <label class="col-sm-3">Title</label>
    <span class="col-sm-9"><input name="title" placeholder="title" class="form-control" value="${post.getTitle()}" required data-minlength="3"/></span>
</div>
<br><br>
<div class="form-group">
    <label class="col-sm-3">Url</label>
    <span class="col-sm-9"><input name="url" type="url" placeholder="url" class="form-control" value="${post.getUrl()}" required data-minlength="3"/></span>
</div>
<br><br>  
<div class="form-group">
    <label class="col-sm-3">Subreddit</label>
    <span class="col-sm-9"><input name="sr" placeholder="Subreddit" class="form-control" value="${post.getSubreddit()}" required data-minlength="3"/></span>
</div>
<br><br>
<div>
<label  class="col-sm-3">Send replies to my inbox</label>  <span class="col-sm-9"> <input type="checkbox" name="sendreplies" value="true" <c:if test="${post.isSendReplies()=='true'}"> checked </c:if> /></span> 
</div>
<br><br>

<label class="col-sm-3">Submission Date</label>
<span class="col-sm-9"><input type="text" name="date" class="form-control" value="${dateValue}" readonly></span>
    <script type="text/javascript">
        $(function(){
            $('*[name=date]').appendDtpicker({"inline": true});
        });
    </script>

    <br><br>
    
    <button type="submit" class="btn btn-primary">Save Changes</button>
   </div>
</form>
</div>
</body>
</html>