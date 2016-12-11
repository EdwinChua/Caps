<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!--  additional custom styles -->
<link rel="stylesheet" href="resources/mystyle.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>
<body>
	<div id="header"></div>
	<div class="container" style="width: 50%; min-width: 300px">
		<h1>Login</h1>
		<div class="jumbotron">

			<form:form modelAttribute="user" method="POST"
				action="${pageContext.request.contextPath}/home/authenticate">
				<h2 class="form-signin-heading">Please sign in</h2>
				<label for="userid" class="sr-only"></label>
				<form:input class="form-control" placeholder="UserId" id="userid"
					path="userId" />
				<br>
				<label for="inputPassword" class="sr-only"></label>
				<form:input class="form-control" placeholder="Password"
					id="inputPassword" path="password" />
				<div class="checkbox">
					<label> <input type="checkbox" value="remember-me">
						Remember me
					</label>
				</div>
				<!-- removed the type="submit" property for testing, redirect function for testing only -->
				<form:button class="btn btn-lg btn-primary btn-block" name="submit"
					type="submit" value="s">
				Sign in</form:button>
			</form:form>

		</div>
	</div>
	<footer
		class="t7-container t7-dark-grey t7-padding-32 t7-padding-xlarge footer">
	<div id="footer"></div>
	</footer>

</body>
<script>
	$(function() {
		$("#header").load("resources/header.jsp");
		$("#footer").load("resources/footer.html");
	});

	function Redirect() {
		var login = document.getElementById("inputEmail").value;
		if (login.includes("admin")) {
			window.location.href = "adminmgt.html?userrole=admin&manage=student";
		}
	}
</script>
</html>