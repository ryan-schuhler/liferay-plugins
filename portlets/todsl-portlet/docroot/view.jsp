<%@ include file="/init.jsp" %>

<%
String appName = ParamUtil.getString(request, "appName", "trip-info");

appName = StringUtil.replace(appName, "-", "_");

String appFile = "/apps/" + appName + ".jsp";
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(trip) %>">
		<div class="row-fluid">
			<%@ include file="/banner.jspf" %>
		</div>

		<div class="max-full row-fluid">
			<div class="span3 widgets">
				<%@ include file="/widgets.jspf" %>
			</div>

			<div class="display span6">
				<jsp:include page="<%= appFile %>" />
			</div>

			<div class="apps span3">
				<%@ include file="/apps.jspf" %>
			</div>
		</div>
	</c:when>
	<c:when test="<%= themeDisplay.isSignedIn() %>">
		Sorry this trip doesn't exist. Create one <a href="<%= baseURL %>create">here</a>.
	</c:when>
	<c:otherwise>
		Please <a href="<%= themeDisplay.getURLSignIn() %>">Sign In</a> to create a trip.
	</c:otherwise>
</c:choose>

<style type="text/css">
	<%= trip.getTripCss() %>
</style>