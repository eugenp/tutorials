<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<head>

<title>Schedule to Reddit</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

</head>
<body>
<jsp:include page="header.jsp" />

<div class="container">
<h1>My Scheduled Posts</h1>
<table class="table table-bordered">
<thead>
<tr>
<th>Post title</th>
<th>Submission Date</th>
<th>Status</th>
<th>Actions</th>
</tr>
</thead>
<c:forEach var="post" items="${posts}" >
    <tr <c:if test="${post.isSent()}"> class="success"</c:if>>
        <td><c:out value="${post.getTitle()}"/></td>
        <td><fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${post.getSubmissionDate()}" /></td>
        <td><c:out value="${post.getSubmissionResponse()}"/></td>
        <td>
            <a href="editPost/${post.getId()}" class="btn btn-warning" >Edit</a>
            <a href="#" class="btn btn-danger" onclick="confirmDelete(${post.getId()})">Delete</a>
        </td>
    </tr>
</c:forEach>
</table>
</div>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script>
function confirmDelete(id) {
    if (confirm("Do you really want to delete this post?") == true) {
    	deletePost(id);
    } 
}

function deletePost(id){
	$.ajax({
	    url: 'deletePost/'+id,
	    type: 'DELETE',
	    success: function(result) {
	    	window.location.href="posts"
	    }
	});
}
</script>
</body>
</html>