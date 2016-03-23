<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <link rel="icon" href="img/logo-TP.jpg">	

    <title>Trading Platform</title>

    <link href="resources/index_files/bootstrap.min.css" rel="stylesheet">
    <link href="resources/index_files/carousel.css" rel="stylesheet">
    <link href="resources/index_files/index.css" rel="stylesheet">    
    <link href='https://fonts.googleapis.com/css?family=Amaranth' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="resources/css/modal.css" type="text/css">
    
    <script src="resources/index_files/jquery.min.js"></script>
    <script src="resources/index_files/bootstrap.min.js"></script>
    <script src="resources/index_files/holder.min.js"></script>
    <script src="resources/js/authentication.js"></script>
  </head>
  <body>
	  <div class="navbar-wrapper">
	      <div class="container">
	
	        <nav class="navbar navbar-inverse navbar-static-top">
	          <div class="container">
	            <div class="navbar-header">
	              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
	                <span class="sr-only">Toggle navigation</span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	              </button>
	              <a class="navbar-brand" href="#">Trading Platform</a>
	            </div>
	            <div id="navbar" class="navbar-collapse collapse">
	              <ul class="nav navbar-nav">
	             	<c:choose>
						<c:when test="${sessionScope.loggedInUser == null}">
							<li class="navbar-opt"><a class="red-glow" href="#"
								data-toggle="modal" data-target="#registerModal">Create an
									account</a></li>
							<li class="navbar-opt"><a id="login-modal-link" class="red-glow" href="#"
								data-toggle="modal" data-target="#loginModal">Sign In</a></li>
						</c:when>
			
						<c:otherwise>
							<li class="navbar-opt"><a class="red-glow" href="/TradingPlatform/dashboard">Dashboard</a></li>
							<li class="navbar-opt"><a class="red-glow" href="/TradingPlatform/logout">Logout</a></li>
						</c:otherwise>
					</c:choose>
	              </ul>
	            </div>
	          </div>
	        </nav>
	
	      </div>
	    </div>
	  
	    <!-- Carousel
	    ================================================== -->
	    <div id="myCarousel" class="carousel slide" data-ride="carousel">
	      <div class="carousel-inner" role="listbox">
	        <div class="item active">
	          <img class="first-slide" src="resources/img/main.jpg" alt="First slide">
	          <div class="container">
	            <div class="carousel-caption">
	              <h1>Trading Platform</h1>
	              <p>The best trading platform for brokers and shareholders is finally here! Featuring a responsive dashboard to manage all of your assets!</p>
	            </div>
	          </div>
	        </div>
	      </div>
	    </div><!-- /.carousel -->
	
	
	    <!-- Marketing messaging and featurettes
	    ================================================== -->
	    <!-- Wrap the rest of the page in another container to center all the content. -->
	
	    <div class="container marketing">
	
	      <!-- Three columns of text below the carousel -->
	      <div class="row">
	        <div class="col-lg-4">
	          <img class="img-circle" src="resources/img/round-images1.png" alt="Generic placeholder image" width="140" height="140">
	          <h2>Support</h2>
	          <p>Administrators can add or remove users and view system logs to solve problems.</p>
	        </div><!-- /.col-lg-4 -->
	        <div class="col-lg-4">
	          <img class="img-circle" src="resources/img/round-images2.png" alt="Generic placeholder image" width="140" height="140">
	          <h2>Shareholders</h2>
	          <p>Request trades and view your portfolio and outstanding requests</p>
	        </div><!-- /.col-lg-4 -->
	        <div class="col-lg-4">
	          <img class="img-circle" src="resources/img/round-images4.png" alt="Generic placeholder image" width="140" height="140">
	          <h2>Brokers</h2>
	          <p>View, edit and add companies and set up initial shares for sale.</p>
	        </div><!-- /.col-lg-4 -->
	      </div><!-- /.row -->
	      <!-- Three columns of text below the carousel -->
	      <hr/>
	      <!-- FOOTER -->
	      <footer>
	        <p>© 2016 FDMGroup</p>
	      </footer>
	
	    </div><!-- /.container -->
		
			<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
			<div class="modal-dialog modal-lg">
			      <div class="modal-content">
			             <div class="modal-header">
			                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
			                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
			                    </button>
			                    <h1 class="modal-title custom_align" id="Heading">Create Your Account</h1>
			             </div>
			             <sf:form id="register-form" action="register" modelAttribute="user">
				             <div class="modal-body">
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon">Username</span> 
										<sf:input path="userName" class="red-glow form-control" type="text" autocomplete="off" name="userName" max-length="50" required="required" id="register-username-input"/>
			                        </div> 
			                    </div>
			                    <p style="display: none;" id="user-exists">*This username is already taken. Please try another.</p>
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon">Password</span> 
										<sf:input path="passWord" class="red-glow form-control" type="password" name="userName" max-length="50" required="required" id="register-password-input"/>
			                        </div> 
			                    </div>
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon">First Name</span> 
										<sf:input path="firstName" class="red-glow form-control" type="text" name="firstName" max-length="50" required="required" id="register-firstname-input"/>
			                        </div> 
			                    </div>
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon">Last Name</span> 
										<sf:input path="lastName" class="red-glow form-control" type="text" name="lastName" max-length="50" required="required" id="register-lastname-input"/>
			                        </div> 
			                    </div>
			                    
			                    <h3>I am a:</h3>
			                    <div class="checkbox">
								  <label><input type="checkbox" id="admin-box" name="roleNames" value="admin"/>Admin</label>
								</div>
			                    <div class="checkbox">
								  <label><input type="checkbox" id="shareholder-box" name="roleNames" value="shareholder"/>Shareholder</label>
								</div>
			                    <div class="checkbox">
								  <label><input type="checkbox" id="broker-box" name="roleNames" value="broker"/>Broker</label>
								</div>
			                    <div class="checkbox">
								  <label><input type="checkbox" id="company-box" name="roleNames" value="company"/>Company</label>
								</div>			                    

			                  </div>
				             <div class="modal-footer ">
				                    <button type="submit" id="register-btn" class="btn btn-danger btn-lg" style="width: 100%;">
				                           <span class="glyphicon glyphicon-ok-sign"></span> Register
				                    </button>
				           </div>
			          </sf:form>
			      </div>
			      <!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
	    </div>
	    
	    <div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
			<div class="modal-dialog modal-lg">
			      <div class="modal-content">
			             <div class="modal-header">
			                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
			                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
			                    </button>
			                    <h1 class="modal-title custom_align" id="Heading">Log in</h1>
			             </div>
			             <sf:form id="login-form" action="login" class="no-confirm" modelAttribute="user">
				             <div class="modal-body">
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon">Username</span> 
										<sf:input path="userName" class="red-glow form-control" type="text" autocomplete="off" name="userName" max-length="50" required="required" id="login-username-input"/>
			                        </div> 
			                    </div>
			                    <div class="form-group">
			                    	<div class="input-group">
			                    		<span class="input-group-addon">Password</span> 
										<sf:input path="passWord" class="red-glow form-control" type="password" name="userName" max-length="50" required="required" id="login-password-input"/>
			                        </div> 
			                    </div>
			                    <p id="invalid-login">Invalid username/password</p>
			                 </div>
				             <div class="modal-footer ">
				                    <button type="submit" id="login-btn" class="btn btn-danger btn-lg" style="width: 100%;">
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