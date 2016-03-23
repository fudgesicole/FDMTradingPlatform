<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<!DOCTYPE html>
<header>
	<i class="fa fa-bars fa-2x sidebar-toggle"></i>
	<div id="title">
		<p>Trading Platform</p>
	</div>
	<div id="user-widget">
		<div id="power-button">
			<div class="dropdown">
			  <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
			    Hello, ${sessionScope.loggedInUser.firstName}
			${sessionScope.loggedInUser.lastName}
			    <span class="caret"></span>
			  </button>
			  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
			    <li><a href="/TradingPlatform/logout">Logout <span class="glyphicon glyphicon-off"></span></a></li>
			    <li id="change-pw-btn" data-toggle="modal" data-target="#change-password-modal"><a>Change password</a></li>
			  </ul>
			</div>
		</div>
	</div>
</header>

<div id="sidebar">
	<ul class="nav main-menu">
		<c:forEach items="${sessionScope.loggedInUser.roles}" var="role">
			<c:choose>
				<c:when test="${role.name == 'admin'}">
					<li class="sidebar-dropdown dropdown"><a href="#" class="sidebar-dropdown dropdown-toggle"
						data-toggle="dropdown"> <i class="fa fa-cogs"></i> <span
							class="hidden-xs">Admin Actions</span>
					</a>
						<ul class="sidebar-dropdown sub-menu">
							<li><a
								href="/TradingPlatform/userList">User List</a></li>
						</ul></li>
				</c:when>
				<c:when test="${role.name == 'shareholder'}">
					<li class="sidebar-dropdown dropdown"><a href="#" class="sidebar-dropdown dropdown-toggle"
						data-toggle="dropdown"> <i class="fa fa-money"></i> <span
							class="hidden-xs">Shareholder Actions</span></a>
						<ul class="sidebar-dropdown sub-menu">
							<li class="inactive"><a
								href="/TradingPlatform/portfolio">Portfolio</a></li>
							<li class="inactive"><a
								href="/TradingPlatform/tradeHistory">Trade History</a></li>
							<li class="inactive"><a
								href="/TradingPlatform/requests">Requests</a></li>
							<li class="inactive"><a
								href="/TradingPlatform/buyShares">Buy Shares</a></li>
						</ul></li>
				</c:when>
				<c:when test="${role.name == 'broker'}">
					<li class="sidebar-dropdown dropdown"><a href="#" class="sidebar-dropdown dropdown-toggle"
						data-toggle="dropdown"> <i class="fa fa-briefcase"></i> <span
							class="hidden-xs">Broker Actions</span></a>
						<ul class="sidebar-dropdown sub-menu">
							<li class="inactive"><a
								href="/TradingPlatform/brokerCompanyList">View Companies</a></li>
						</ul></li>
				</c:when>
				<c:otherwise>
					<!-- Nothing for other roles -->
				</c:otherwise>
			</c:choose>
		</c:forEach>
	</ul>
</div>

<div class="modal fade" id="change-password-modal" tabindex="-1" role="dialog" aria-labelledby="edit" aria-hidden="true">
	<div class="modal-dialog modal-lg">
	      <div class="modal-content">
	             <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
	                           <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
	                    </button>
	                    <h1 class="modal-title custom_align" id="Heading">Change password</h1>
	             </div>
	             <form id="update-password-form" action="updatePassword">
	             	 <input type="hidden" id="update-password-username-input" value="${sessionScope.loggedInUser.userName}">
		             <div class="modal-body">
	                    <div class="form-group">
	                    	<div class="input-group">
	                    		<span class="input-group-addon input-lg">Current Password</span> 
								<input name="oldPassWord" class="input-lg red-glow form-control" type="password" required="required" id="current-password-input"/>
	                        </div> 
	                    </div>
	                    <p style="display: none;" id="invalid-login">*Incorrect password</p>
	                    <div class="form-group">
	                    	<div class="input-group">
	                    		<span class="input-group-addon input-lg">New Password</span> 
								<input name="newPassWord" class="input-lg red-glow form-control" type="password" name="passWord" maxlength="50" required="required" id="change-password-input"/>
	                        </div> 
	                    </div>
	                    <div class="form-group">
							<div class="input-group">
								<span class="input-group-addon">Confirm New Password</span>
								<input class="red-glow form-control input-lg"
									type="password"
									required="required" id="change-password-confirm-input" />
							</div>
						</div>
						<p style="display: none;" id="password-match-msg">*Passwords do not match</p>
					 </div>
		             <div class="modal-footer ">
		                    <button type="submit" id="change-pw-submit-btn" class="btn btn-danger btn-lg" style="width: 100%;">
		                           <span class="glyphicon glyphicon-ok-sign"></span> Update password
		                    </button>
		           </div>
	          </form>
	      </div>
	      <!-- /.modal-content -->
	</div>
	<!-- /.modal-dialog -->
   </div>
