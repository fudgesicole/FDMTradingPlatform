<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Trading Platform</title>

<script src="resources/index_files/jquery.min.js"></script>
<script src="resources/js/authentication.js"></script>
<script src="resources/js/foundation.min.js"></script>
<script src="resources/index_files/bootstrap.min.js"></script>
    <script src="resources/index_files/holder.min.js"></script>

<link href="resources/css/bootstrap.css" rel="stylesheet">
<link href="resources/index_files/carousel.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/modal.css" type="text/css">
<link href="resources/index_files/index.css" rel="stylesheet">
<link href="resources/css/bootstrap.icon-large.min.css" rel="stylesheet">
</head>
<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<img alt="FDM logo" id="fdm-logo" src="resources/img/FDM.png">

			<c:choose>
				<c:when test="${sessionScope.loggedInUser == null}">
					<ul class="navbar-opt navbar-right">
						<button class="btn btn-info btn-auth" data-toggle="modal"
							data-target="#loginModal" id="signin-btn">Sign In</button>
					</ul>
					<ul class="navbar-opt navbar-right">
						<button class="btn btn-info btn-auth" data-toggle="modal"
							data-target="#registerModal" id="register-btn">Register</button>
					</ul>
				</c:when>

				<c:otherwise>
					<ul class="navbar-opt navbar-right">
						<button class="btn btn-info btn-auth"
							href="/TradingPlatform/dashboard" id="dashboard-btn">Dashboard</button>
					</ul>
					<ul class="navbar-opt navbar-right">
						<button class="btn btn-info btn-auth"
							href="/TradingPlatform/logout" id="logout-btn">Logout</button>
					</ul>
				</c:otherwise>
			</c:choose>

		</div>
	</nav>

	<!-- Carousel
	    ================================================== -->
	<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
		<!-- Indicators -->
		<ol class="carousel-indicators">
			<li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
			<li data-target="#carousel-example-generic" data-slide-to="1"></li>
		</ol>

		<!-- Wrapper for slides -->
		<div class="carousel-inner" role="listbox">
			<div class="item active">
				<img class="first-slide img-item" src="resources/img/main2.jpg" alt="First slide">
				<div class="carousel-caption">
						<h1>Trading Platform</h1>
						<p>The best trading platform for brokers and shareholders is
							finally here! Featuring a responsive dashboard to manage all of
							your assets!</p>
					</div>
			</div>
			<div class="item">
				<img class="img-item" src="resources/img/tp_bg.jpg" id="ads-img">
			</div>
		</div>

		<!-- Controls -->
		<a class="left carousel-control" href="#carousel-example-generic"
			role="button" data-slide="prev"> <span
			class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> <span
			class="sr-only">Previous</span>
		</a> <a class="right carousel-control" href="#carousel-example-generic"
			role="button" data-slide="next"> <span
			class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
			<span class="sr-only">Next</span>
		</a>
	</div>

	<!-- /.carousel -->


	<!-- Marketing messaging and featurettes
	    ================================================== -->
	    <!-- Wrap the rest of the page in another container to center all the content. -->
	
	    <div class="container marketing">
	
	      <!-- Three columns of text below the carousel -->
	      <div class="row">
	        <div class="col-lg-4">
	          <img class="img-circle" src="resources/img/round1.png" alt="Generic placeholder image" width="140" height="140">
	          <h2>Support</h2>
	          <p>Administrators can add or remove users and view system logs to solve problems.</p>
	        </div><!-- /.col-lg-4 -->
	        <div class="col-lg-4">
	          <img class="img-circle" src="resources/img/round2.png" alt="Generic placeholder image" width="140" height="140">
	          <h2>Shareholders</h2>
	          <p>Request trades and view your portfolio and outstanding requests</p>
	        </div><!-- /.col-lg-4 -->
	        <div class="col-lg-4">
	          <img class="img-circle" src="resources/img/round4.png" alt="Generic placeholder image" width="140" height="140">
	          <h2>Brokers</h2>
	          <p>View, edit and add companies and set up initial shares for sale.</p>
	        </div><!-- /.col-lg-4 -->
	      </div><!-- /.row -->
	      <!-- Three columns of text below the carousel -->
	      <hr/>
	      <!-- FOOTER -->
	      <footer>
	        <p>© 2016 FDMGroup</p>
	        <span class="social social-facebook"></span>
	      </footer>
	
	    </div><!-- /.container -->

	<div class="modal fade" id="registerModal" tabindex="-1" role="dialog"
		aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button>
					<h1 class="modal-title custom_align" id="Heading">Create Your
						Account</h1>
				</div>
				<sf:form id="register-form" action="register" modelAttribute="user">
					<div class="modal-body">
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon">Username</span>
								<sf:input path="userName" class="red-glow form-control input-lg"
									type="text" autocomplete="off" name="userName" max-length="50"
									required="required" id="register-username-input" />
							</div>
						</div>
						<p style="display: none;" id="user-exists">*This username is
							already taken. Please try another.</p>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon">Password</span>
								<sf:input path="passWord" class="red-glow form-control input-lg"
									type="password" name="userName" max-length="50"
									required="required" id="register-password-input" />
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon">First Name</span>
								<sf:input path="firstName" class="red-glow form-control input-lg"
									type="text" name="firstName" max-length="50"
									required="required" id="register-firstname-input" />
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon">Last Name</span>
								<sf:input path="lastName" class="red-glow form-control input-lg"
									type="text" name="lastName" max-length="50" required="required"
									id="register-lastname-input" />
							</div>
						</div>

						<h3>I am a:</h3>
						<div class="checkbox">
							<label><input type="checkbox" id="admin-box"
								name="roleNames" value="admin" />Admin</label>
						</div>
						<div class="checkbox">
							<label><input type="checkbox" id="shareholder-box"
								name="roleNames" value="shareholder" />Shareholder</label>
						</div>
						<div class="checkbox">
							<label><input type="checkbox" id="broker-box"
								name="roleNames" value="broker" />Broker</label>
						</div>
						<div class="checkbox">
							<label><input type="checkbox" id="company-box"
								name="roleNames" value="company" />Company</label>
						</div>

					</div>
					<div class="modal-footer ">
						<button type="submit" id="register-btn"
							class="btn btn-danger btn-lg" style="width: 100%;">
							<span class="glyphicon glyphicon-ok-sign"></span> Register
						</button>
					</div>
				</sf:form>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>

	<div class="modal fade" id="loginModal" tabindex="-1" role="dialog"
		aria-labelledby="edit" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</button>
					<h1 class="modal-title custom_align" id="Heading">Log in</h1>
				</div>
				<sf:form id="login-form" action="login" class="no-confirm"
					modelAttribute="user">
					<div class="modal-body">
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon">Username</span>
								<sf:input path="userName" class="red-glow form-control input-lg"
									type="text" autocomplete="off" name="userName" max-length="50"
									required="required" id="login-username-input" />
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon">Password</span>
								<sf:input path="passWord" class="red-glow form-control input-lg"
									type="password" name="userName" max-length="50"
									required="required" id="login-password-input" />
							</div>
						</div>
						<p id="invalid-login">Invalid username/password</p>
					</div>
					<div class="modal-footer ">
						<button type="submit" id="login-btn" class="btn btn-danger btn-lg"
							style="width: 100%;">
							<span class="glyphicon glyphicon-ok-sign"></span> Log in
						</button>
					</div>
				</sf:form>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
	<jsp:include page="confirmation.jsp"></jsp:include>
</body>
</html>