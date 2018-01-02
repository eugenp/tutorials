<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
	crossorigin="anonymous">
<body>
	<section class="main">
		<div class="container-fluid">
			<div class="row" style="margin-top: 20px">
				<div class="col-md-offset-3 col-lg-offset-4 col-md-6 col-lg-4">
					<h3 class="text-center">SignUp Form</h3>
					<form action="signup" method="post">
						<div class="form-group">
							<label for="name">Email address</label> <input
								type="text" required class="form-control" id="name"
								name="name" placeholder="Enter Name">
						</div>
						<div class="form-group">
							<label for="email">Email address</label> <input
								type="email" required class="form-control" id="email"
								name="email" placeholder="Email">
						</div>
						<div class="form-group">
							<label for="password">Password</label> <input
								type="password" required class="form-control" id="password"
								name="password" placeholder="Password">
						</div>
						
						<div class="form-group">
						<button type="submit" class="btn btn-primary">Register</button>
						</div>
					</form>
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
