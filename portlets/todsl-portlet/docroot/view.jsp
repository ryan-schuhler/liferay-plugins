<%@ include file="/init.jsp" %>

<%
String appName = ParamUtil.getString(request, "appName", "trip-dashboard");

String appFileName = StringUtil.replace(appName, "-", "_");

String appFile = "/apps/" + appFileName + ".jsp";
%>

<c:choose>
	<c:when test="<%= Validator.isNotNull(trip) %>">
		<div class="row-fluid">
			<%@ include file="/banner.jspf" %>
		</div>

		<div class="flex row-fluid">
			<c:if test="<%= Validator.isNotNull(tripMember) %>">
				<div class="apps span3">
					<%@ include file="/apps.jspf" %>
				</div>
			</c:if>

			<div class="display span9">
				<jsp:include page="<%= appFile %>" />
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