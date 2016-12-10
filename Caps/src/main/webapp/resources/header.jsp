<!--Navigation bar mobile responsive-->


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#myNavbar">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${pageContext.request.contextPath}/home/index">CAPS</a>
			</li>
		</div>
		<div class="collapse navbar-collapse" id="myNavbar">

			<ul class="nav navbar-nav">
				<li id="navbarHome"><a href="${pageContext.request.contextPath}/home/index">Home</a></li>
			<!-- 	<li id="navbarAdmin" class="dropdown"><a
					class="dropdown-toggle" data-toggle="dropdown" href="#">Admin
						Menu<span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="adminmgt.html?manage=student">Manage Students</a></li>
						<li><a href="adminmgt.html?manage=lecturer">Manage
								Lecturers</a></li>
						<li><a href="adminmgt.html?manage=course">Manage Courses</a></li>
						<li><a href="adminmgt.html?manage=enrolment">Manage
								Enrolment</a></li>
					</ul></li>
				<li id="navbarStudent" class="dropdown"><a
					class="dropdown-toggle" data-toggle="dropdown" href="#">Student
						Menu<span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="studentgrades.html">Grades and GPA</a></li>
						<li><a href="studentcourses.html">View Courses</a></li>
						<li><a href="studentenrol.html">Enrol for a Course</a></li>
					</ul></li>
				<li id="navbarLecturer" class="dropdown"><a
					class="dropdown-toggle" data-toggle="dropdown" href="#">Lecturer
						Menu<span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="lecturercourses.html">View Courses Taught</a></li>
						<li><a href="lecturerviewenrolment.html">View Course
								Enrolment</a></li>
						<li><a href="lecturergradecse.html">Grade a Course</a></li>
						<li><a href="lecturerviewperf.html">View a Student
								Performance</a></li>
					</ul></li>
-->
			</ul> 
			<ul class="nav navbar-nav navbar-right">
				<!--<li><a href="#"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>-->
				 <li id="logedinuser"><a href="#" style="max-width: 100%"><span
						class="glyphicon glyphicon-user"></span>
					<%= request.getParameter("idlogedin")
					%>
					
						
						</a></li>
				<!--<li id="navbarLogin"><a href="login.html" data-toggle="login"
					data-placement="auto" title="Login" style="max-width: 100%"><span
						class="glyphicon glyphicon-log-in"></span> Login</a></li> -->
				<li id="navbarLogout"><a href="${pageContext.request.contextPath}/home/logout"
					data-toggle="logout" data-placement="auto" title="Logout"
					style="max-width: 100%"><span
						class="glyphicon glyphicon-log-out"></span> Log out</a></li>
			</ul>
		</div>
	</div>
</nav>
<br>


<!-- 
<script>
	var url = window.location.pathname
	if (url.includes("index"))
	{
		document.getElementById("navbarHome").className = "active";
	} else if (url.includes("adminmgt"))
	{
		document.getElementById("navbarAdmin").className = "active";
	} else if (url.includes("student"))
	{
		document.getElementById("navbarStudent").className = "active";
	} else if (url.includes("lecturer"))
	{
		document.getElementById("navbarLecturer").className = "active";
	} else if (url.includes("login"))
	{
		document.getElementById("navbarLogin").className = "active";
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

			document.getElementById("navbarAdmin").style.display = "none";
			document.getElementById("navbarStudent").style.display = "none";
			document.getElementById("navbarLecturer").style.display = "none";
			userloggedout();

		}
	})(window.location.search.substr(1).split('&'));

	
	try
	{
	console.log(qs["userrole"]); // testing purposes

		if (qs["userrole"] == "admin")
		{
			document.getElementById("navbarAdmin").style.display = "initial";
			document.getElementById("navbarStudent").style.display = "none";
			document.getElementById("navbarLecturer").style.display = "none";
			userloggedin();
		} else if (qs["userrole"] == "student")
		{
			document.getElementById("navbarAdmin").style.display = "none";
			document.getElementById("navbarStudent").style.display = "initial";
			document.getElementById("navbarLecturer").style.display = "none";
			userloggedin();
		} else if (qs["userrole"] == "lecturer")
		{
			document.getElementById("navbarAdmin").style.display = "none";
			document.getElementById("navbarStudent").style.display = "none";
			document.getElementById("navbarLecturer").style.display = "initial";
			userloggedin();
		} else
		{
			document.getElementById("navbarAdmin").style.display = "none";
			document.getElementById("navbarStudent").style.display = "none";
			document.getElementById("navbarLecturer").style.display = "none";
			userloggedout();
		}
	}
	catch (err)
	{
		document.getElementById("navbarAdmin").style.display = "none";
		document.getElementById("navbarStudent").style.display = "none";
		document.getElementById("navbarLecturer").style.display = "none";
		userloggedout();
	}

	function userloggedin()
	{
		document.getElementById("navbarUser").style.display = "initial";
		document.getElementById("navbarLogout").style.display = "initial";
		document.getElementById("navbarLogin").style.display = "none";
	}

	function userloggedout()
	{
		document.getElementById("navbarUser").style.display = "none";
		document.getElementById("navbarLogout").style.display = "none";
		document.getElementById("navbarLogin").style.display = "initial";
	}
</script> -->