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
		<p id="welcome">Hello, ${sessionScope.loggedInUser.firstName}
			${sessionScope.loggedInUser.lastName}</p>
		<div id="power-button">
			<a class="btn btn-default glyphicon glyphicon-off" href="/TradingPlatform/logout"></a>
		</div>
	</div>
</header>

<div id="sidebar">
	<ul class="nav main-menu">
		<c:forEach items="${sessionScope.loggedInUser.roles}" var="role">
			<c:choose>
				<c:when test="${role.name == 'admin'}">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <i class="fa fa-cogs"></i> <span
							class="hidden-xs">Admin Actions</span>
					</a>
						<ul class="sub-menu">
							<li><a
								href="/TradingPlatform/userList">User List</a></li>
						</ul></li>
				</c:when>
				<c:when test="${role.name == 'shareholder'}">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <i class="fa fa-money"></i> <span
							class="hidden-xs">Shareholder Actions</span></a>
						<ul class="sub-menu">
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
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> <i class="fa fa-briefcase"></i> <span
							class="hidden-xs">Broker Actions</span></a>
						<ul class="sub-menu">
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
