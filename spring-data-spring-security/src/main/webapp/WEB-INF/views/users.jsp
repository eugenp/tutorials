<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>My Tweets (Liked)</title>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<style>
a {
	cursor: pointer;
	background-color: lightblue;
}
</style>
</head>
<body>
	<section class="main">
		<div class="container-fluid">
			<div class="row" style="margin-top: 20px">
				<div class="col-md-offset-2 col-lg-offset-3 col-md-8 col-lg-6">
					<h3 class="text-center">My Tweets And Likes</h3>
					<p><a href="/load" class="btn btn-sm btn-success">Load Dummy Content</a>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>No.</th>
								<th>Tweet</th>
								<th>Tweet Owner</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="pageModel.first && pageModel.hasContent">
							<fmt:parseNumber var="totalPages" value="${pageModel.totalPages}" integerOnly="true"/>
							<c:forEach items="pageModel.content" var="tweet" varStatus="i">
								<tr>
									<td>${i }</td>
									<td>${tweet.tweet }</td>
									<td>${tweet.owner }</td>
								</tr>
							</c:forEach>
							</c:if>
						</tbody>
						<tfoot>
							<ul class="pagination">
								<c:forEach end="${totalPages }" begin="1" var="i">
									<li class="${pageModel.number == i ? 'active' : '' }"><a
										href="/users?page=${i }">${i }</a></li>
								</c:forEach>
							</ul>
						</tfoot>
					</table>
				</div>
			</div>
		</div>
	</section>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
		integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
		crossorigin="anonymous"></script>
</body>
</html>