<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
	<html>
		<head>
			<meta charset="ISO-8859-1">
			<title>Trading Platform</title>
		    <meta charset="utf-8">
		    <meta http-equiv="X-UA-Compatible" content="IE=edge">
		    <meta name="viewport" content="width=device-width, initial-scale=1">
		    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
		    <link rel="icon" href="img/logo-TP.jpg">
		    <meta name="description" content="Login page for Trading Platform">
		    <meta name="author" content="Cole Fudge">
			<link href="plugins/bootstrap/bootstrap.css" rel="stylesheet">
			<link href="plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet">
			<link
				href="http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css"
				rel="stylesheet">
			<link href="plugins/fancybox/jquery.fancybox.css" rel="stylesheet">
			<link href="plugins/xcharts/xcharts.min.css" rel="stylesheet">
			<link href="plugins/select2/select2.css" rel="stylesheet">
			<link href="plugins/justified-gallery/justifiedGallery.css"
				rel="stylesheet">
			<link href="plugins/chartist/chartist.min.css" rel="stylesheet">
		    
		    <link href='https://fonts.googleapis.com/css?family=Amaranth' rel='stylesheet' type='text/css'>
	
			<script type="text/javascript" src="js/index.js"></script> 
			<link rel="stylesheet" href="css/login.css" type="text/css">
			<link rel="stylesheet" href="css/main.css" type="text/css">
		</head>
	<body>
		<!-- color scheme I want: BLACK: #202020 rg(32, 32, 32), RED: #B81D18 rgb(184, 29, 24), 
		GREY: #B6B6B6 rgb(182,182,182), BLUE: #004687 rgb(0,60,135) , WHITE: #F0F0F0 rgb(240,240,240) -->
		<div id="top-color"></div>
		
		<div class="bounce-div" id="logo">
			<img class="bouncy" src="img/logo-TP.jpg"/>
		</div>
			
		<div class="boxed" id="login-box">	
			<div class="container">
				<form class="form-signin" method="post" id="form" action="addUser">
			    	<h2 class="form-signin-heading">Welcome, please enter your info</h2>
			    	<div id="sign-in-fields">
			        	<label for="inputUserName" class="sr-only">User name</label>
				        <input type="text" id="inputUsername" class="form-control" placeholder="User name" required="required" name="userName">
				        <label for="inputPassword" class="sr-only">Password</label>
				        <input type="password" id="inputPassword" class="form-control" placeholder="Password" required="required" name="passWord">
				        <label for="inputFirstName" class="sr-only">First name</label>
				        <input type="text" id="inputFirstName" class="form-control" placeholder="First name" required="required" name="firstName">
				        <label for="inputLastName" class="sr-only">Last name</label>
				        <input type="text" id="inputLastName" class="form-control" placeholder="Last name" required="required" name="lastName">
					    <div class="btn-group" role="group" aria-label="...">
							<label class="btn btn-primary">
								<input id="add-user-admin-input" type="checkbox" name="roles[]" value="1" autocomplete="off"> Admin
							</label>
							<label class="btn btn-primary">
								<input id="add-user-shareholder-input" type="checkbox" name="roles[]" value="2" autocomplete="off"> Shareholder
							</label>
							<label class="btn btn-primary">
								<input id="add-user-company-input" type="checkbox" name="roles[]" value="3" autocomplete="off"> Company
							</label>
							<label class="btn btn-primary">
								<input id="add-user-broker-input" type="checkbox" name="roles[]" value="4" autocomplete="off"> Broker
							</label>
						</div>
						<input type="hidden" name="signup" value="true">
					    <button class="btn btn-lg btn-primary btn-block" type="submit" id="sign-up-button">Sign up</button>
				    </div>
			    </form>
		    </div> 
		</div>	
		<div id="bottom-color"></div>
		
		<img id="fdm-logo" src="img/FDMlogo.jpg" href="http://www.fdmgroup.com/">
	</body>
</html>