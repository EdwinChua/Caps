<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@page import="edu.iss.caps.model.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Home</title>
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
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/mystyle.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
#event {
	padding-top: 25px;
}

#news {
	padding-top: 50px;
}

#about {
	padding-top: 50px;
}
</style>
<!--Navigation bar mobile responsive-->

<nav class="navbar navbar-inverse navbar-fixed-top">
<div class="container-fluid">
	<div class="navbar-header">
		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target="#myNavbar">
			<span class="icon-bar"></span> <span class="icon-bar"></span> <span
				class="icon-bar"></span>
		</button>
		<a class="navbar-brand"
			href="${pageContext.request.contextPath}/home/index?#ssh">CAPS</a>

	</div>
	<div class="collapse navbar-collapse" id="myNavbar">

		<ul class="nav navbar-nav">


			<%
				if (session.getAttribute("user") == null || session.getAttribute("user").equals("")) {
				}
				else{
			%>
			<li id="navbarrole"><a
				href="${pageContext.request.contextPath}/home/redirectToRolesHomePage">${role}</a></li>
			<%
				}
			%>


		</ul>
		<ul class="nav navbar-nav navbar-right">
			<!--<li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>-->
			<li><a
				href="${pageContext.request.contextPath}/home/index?#event">Events</a></li>
			<li><a
				href="${pageContext.request.contextPath}/home/index?#news">News</a></li>
			<li><a
				href="${pageContext.request.contextPath}/home/index?#about">About
					us</a></li>


			<%-- <li id="navbarUser"><a href="#" style="max-width: 100%"><span
						class="glyphicon glyphicon-user"></span> ${role}</a>
						</li>  --%>

			<%
				if (session.getAttribute("user") == null || session.getAttribute("user").equals("")) {
			%>

			<li id="navbarLogin"><a
				href="${pageContext.request.contextPath}/home/login"
				data-toggle="login" data-placement="auto" title="Login"
				style="max-width: 100%"><span class="glyphicon glyphicon-log-in"></span>
					Login</a></li>

			<%
				} else {
			%>
			<li id="navbarLogout"><a
				href="${pageContext.request.contextPath}/home/logout"
				data-toggle="logout" data-placement="auto" title="Logout"
				style="max-width: 100%"><span
					class="glyphicon glyphicon-log-out"></span> Log out</a></li>

			<%
				}
			%></li>

		</ul>
	</div>
</div>
</nav>






</head>
<body>
	<div id="ssh">
		<br> <br>
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
				<li data-target="#myCarousel" data-slide-to="2"></li>
				<li data-target="#myCarousel" data-slide-to="3"></li>
			</ol>

			<!-- Wrapper for slides -->
			<div class="carousel-inner" role="listbox">

				<div class="item active">
					<img src="${pageContext.request.contextPath}/resources/pic/21.jpg"
						alt="Chania" width="100%">
				</div>

				<div class="item">
					<img src="${pageContext.request.contextPath}/resources/pic/22.jpg"
						alt="Chania" width="100%">
					<div class="carousel-caption">
					</div>
				</div>

				<div class="item">
					<img src="${pageContext.request.contextPath}/resources/pic/23.jpg"
						alt="Flower" width="100%">
				</div>

				<div class="item">
					<img src="${pageContext.request.contextPath}/resources/pic/24.jpg"
						alt="Flower" width="100%">
					<div class="carousel-caption">

					</div>
				</div>

			</div>

			<!-- Left and right controls -->
			<a class="left carousel-control" href="#myCarousel" role="button"
				data-slide="prev"> <span
				class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
			</a> <a class="right carousel-control" href="#myCarousel" role="button"
				data-slide="next"> <span
				class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>
	</div>
	<br>

	<div id="event" class="container">
		<div class="row">

			<div class="col-sm-4 ">
				<br>
				<div class="t7-head">
					<h3 class="fa fa-calendar" style="font-size: 36px"> Events</h3>
				</div>
				<br>
				<div class="t7-card-8 t7-grey" style="height: 300px">
					<div class="t7-container">
						<br>
						<div class="t7-container t7-card-8 t7-white">
							<h5>03 Dec - Six Days FDP Short Course on Finite ...</h5>
						</div>
					</div>
					<br>
					<div class="t7-container">
						<div class="t7-container t7-card-8 t7-white">
							<h5>05 Dec - Four Days Short Course on Java...</h5>
						</div>
					</div>
					<br>
					<div class="t7-container">
						<div class="t7-container t7-card-8 t7-white">
							<h5>08 Dec - One Day ADO.NET Refresher..</h5>
						</div>
					</div>

				</div>

			</div>

			<br>

			<div class="col-sm-4 ">
				<div class="t7-head">
					<h3 class="fa  fa-question" style="font-size: 36px"> Why CAPS</h3>
				</div>

				<br>
				<div class="t7-card-8" style="height: 300px;">
					<img class="mySlides"
						src="${pageContext.request.contextPath}/resources/pic/cheat1.jpg"
						style="width: 100%; height: 290px"> <img class="mySlides"
						src="${pageContext.request.contextPath}/resources/pic/cheat2.jpg"
						style="width: 100%; height: 300px">

				</div>

				<br>
			</div>
			<div class="col-sm-4 ">
				<div class="t7-head">
					<h3 class="fa fa-user" style="font-size: 36px"> Login</h3>
				</div>
				<br>
				<div class="t7-card-8 t7-grey" style="height: 300px;">
					<center>
						<br>
						<div style="width: 80%; text-align: left">
							<br>
							<form:form modelAttribute="user" method="POST"
				action="${pageContext.request.contextPath}/home/authenticate">
				<h2 class="form-signin-heading">Please sign in</h2>
				<label for="userid" class="sr-only"></label>
				<form:input class="form-control" placeholder="UserId" id="userid"
					path="userId" />
				<br>
				<label for="inputPassword" class="sr-only"></label>
				<form:input type="password" class="form-control" placeholder="Password"
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
					</center>
				</div>
			</div>
		</div>
	</div>
	<br>
	<br>
	<div id="news" class="container">
		<div class="row">

			<div class="col-sm-6 ">
				<div class="t7-head">
					<h3 class="fa fa-newspaper-o" style="font-size: 36px"> News</h3>
				</div>
				<br>
				<div class="row">
					<div class="col-sm-6 ">
						<img src="${pageContext.request.contextPath}/resources/pic/a.jpg"
							alt="Norway" style="width: 100%">

						<div class="t7-container t7-center t7-blue t7-card-8">
							<br>
							<p>
								<B>NUS prof to chair </B>
							</p>
							<p>Prof Barry Halliwell will help to steer biomedical
								research efforts in Singapore at the Agency from 1 January 2017</p>

						</div>
						<br>
					</div>
					<div class="col-sm-6">
						<img src="${pageContext.request.contextPath}/resources/pic/b.jpg"
							alt="Norway" style="width: 100%">

						<div class="t7-container t7-center t7-blue t7-card-8">
							<br>
							<p>
								<b>Caring through music</b>
							</p>
							<p>Our Yong Siew Toh Conservatory of Music alumnus and
								award-winning conductor.</p>
						</div>

					</div>
				</div>
				<br> <br> <br>
			</div>

			<div class="col-sm-6 ">
				<div class="t7-head">
					<h3 class="fa fa-file-movie-o " style="font-size: 36px">
						Videos
						<h6 class="t7-right">(Mouse over to play/pause)</h6>
					</h3>
				</div>

				<br>
				<div id="videosList">
					<div class="video">
						<video loop audio muted width="100%" height="300" autoplay>
						<source
							src="${pageContext.request.contextPath}/resources/pic/bit.mp4"
							type="video/mp4"></video>
					</div>
				</div>
			</div>
			<script>
				var figure = $(".video").hover(hoverVideo, hideVideo);

				function hoverVideo(e) {
					$('video', this).get(0).play();
				}

				function hideVideo(e) {
					$('video', this).get(0).pause();
				}
			</script>
		</div>
	</div>
	</div>


	<!-- additional stuff -->
	<div id="about" class="container">
		<div class="row">

			<div class="col-sm-6 ">
				<div class="t7-head">
					<h3 class="fa fa-address-card-o" style="font-size: 36px">
						Contact us</h3>
				</div>

				<br>
				<div class="t7-card-8 t7-container">
					<br> <i class="fa fa-map-marker" style="font-size: 25px;"></i>
					Singapore 119615<br> <br> <i class="fa fa-phone"
						style="font-size: 25px;"> </i> Phone: +65 6516 2093<br>( Enquiry
					Line :Monday - Friday : 9:00am to 4:00pm)<br> <br> <i
						class="fa fa-envelope" style="font-size: 25px;"> </i> <a href="mailto:sa43team7@gmail.com?Subject=Enquires for SA43 Team 7"> Email:
					sa43team7@gmail.com<br></a> <br>

				</div>
				<br>
				

				<div class="t7-head">
					<h3 class="fa fa-coffee" style="font-size: 36px"> Follow us</h3>
				</div>
				<br>
				<div class="t7-container t7-xlarge t7-padding t7-card-8">
					<i class="fa fa-facebook-official t7-hover-text-indigo"
						style="font-size: 48px;"></i>&nbsp; <i
						class="fa fa-whatsapp t7-hover-text-green"
						style="font-size: 48px;"></i>&nbsp; <i
						class="fa fa-google-plus t7-hover-text-red"
						style="font-size: 48px;"></i>&nbsp; <i
						class="fa fa-instagram t7-hover-text-purple"
						style="font-size: 48px;"></i>&nbsp; <i
						class="fa fa-snapchat t7-hover-text-yellow"
						style="font-size: 48px;"></i>&nbsp; <i
						class="fa fa-pinterest-p t7-hover-text-red"
						style="font-size: 48px;"></i>&nbsp; <i
						class="fa fa-twitter t7-hover-text-light-blue"
						style="font-size: 48px;"></i>&nbsp; <i
						class="fa fa-linkedin t7-hover-text-indigo"
						style="font-size: 48px;"></i>
				</div>

				<div style="height: 20px"></div>
				<br> 
			</div>

			<div class="col-sm-6 ">
				<div class="t7-head">
					<h3 class="fa fa-map-marker " style="font-size: 36px"> Reach us</h3>
				</div>
				<br>
				<div class="t7-container t7-card-8"">

					<h3>
						<i class="fa fa-clock-o" style="font-size: 30px"></i> Operating
						Hours
					</h3>
					<p>
						Monday - Friday: 9:00am to 4:00pm<br>(Closed on Saturday,
						Sunday and Public Holiday)
					</p>
				</div>
				<br>
				<div class="t7-card-8" style="height: 250px">
					<iframe
					  width="100%"
					  height=100%
					  frameborder="0" style="border:0"
					  src="https://www.google.com/maps/embed/v1/place?key=AIzaSyAJxp3M2utOpluDl1ZXuP3s9G5VtWBqTAE
					    &q=Singapore+119615" allowfullscreen>
					</iframe>
				</div>
			</div>
		</div>

		<!-- Logged out Modal -->

		<div id="logoutModal" class="modal fade" role="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title">Log out successful</h4>
					</div>
					<div class="modal-body">
						<p>You have been logged out successfully.</p>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					</div>
				</div>

			</div>
		</div>
	</div>

	<br>
	<br>


	<div class="t7-container t7-grey">
		<br>
		<div class="">
			<a href="#" class="t7-btn t7-padding-large t7-margin-bottom t7-right"><i
				class="fa fa-arrow-up w3-margin-right"></i> To the top</a>
			<p>Powered by Team 7</p>
		</div>
	</div>



</body>

<script>
$(function() {
	$("#header").load(
			"${pageContext.request.contextPath}/resources/header.jsp");
	$("#footer").load(
			"${pageContext.request.contextPath}/resources/footer.html");
});
	var myIndex = 0;
	carousel();

	function carousel() {
		var i;
		var x = document.getElementsByClassName("mySlides");
		for (i = 0; i < x.length; i++) {
			x[i].style.display = "none";
		}
		myIndex++;
		if (myIndex > x.length) {
			myIndex = 1
		}
		x[myIndex - 1].style.display = "block";
		setTimeout(carousel, 2000); // Change image every 2 seconds
	}
	
	//breaks down query string for use
	var qs = (function(a)
	{
		if (a == "")
			return
			{};
		try
		{
			var b =
			{};

			for (var i = 0; i < a.length; ++i)
			{
				var p = a[i].split('=', 2);
				if (p.length == 1)
					b[p[0]] = "";
				else
					b[p[0]] = decodeURIComponent(p[1].replace(/\+/g, " "));
			}
			return b;

		} catch (err)
		{
			console.log("hi");
		}
	})(window.location.search.substr(1).split('&'));
	try
	{
		if (qs['action']=="logout")
		{
			$('#logoutModal').modal('toggle');
		}
	}
	catch(err)
	{
		console.log("no action exists");
	}
	
	function Redirect()
	{
		var login = document.getElementById("inputEmail").value;
		if (login.includes("admin"))
		{
			window.location.href = "adminmgt.jsp?userrole=admin&manage=student";
		}
	}
	
</script>


</html>