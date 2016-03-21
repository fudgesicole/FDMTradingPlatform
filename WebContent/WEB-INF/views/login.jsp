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
				<form class="form-signin" method="post" id="form" action="login">
			    	<h2 class="form-signin-heading">Please sign in</h2>
			    	<div id="sign-in-fields">
			        	<label for="inputUserName" class="sr-only">User name</label>
				        <input type="text" id="inputUsername" class="form-control" placeholder="User name" required="required" name="userName">
				        <label for="inputPassword" class="sr-only">Password</label>
				        <input type="password" id="inputPassword" class="form-control" placeholder="Password" required="required" name="passWord">
					    <button class="btn btn-lg btn-primary btn-block" type="submit" id="login-button">Sign in</button>
				    </div>
			    </form>
			    <p id="login-error-msg">*${requestScope.errorMsg}</p>
			    <div id="sign-up">
				    <p>Not a member yet?</p>
				    <a role="button" href="register.jsp" class="btn btn-lg btn-primary btn-block" type="submit" id="sign-up-btn">Sign up</a>
			    </div>
		    </div> 
		</div>	
		<div id="bottom-color"></div>
		
		<img id="fdm-logo" src="img/FDMlogo.jpg" href="http://www.fdmgroup.com/">
	</body>
</html>