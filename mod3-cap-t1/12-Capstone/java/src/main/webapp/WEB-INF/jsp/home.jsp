<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<c:url value="/css/npgeekstyling.css" var="cssHref" />
<link rel="stylesheet" type="text/css" href="${cssHref}">
<c:import url="/WEB-INF/jsp/common/header.jsp" />

<html>
<head>
<meta charset="ISO-8859-1">
<title>NATIONAL PARK GEEK!</title>
</head>
<body class="background">
	<div class="grid">
		<c:forEach items="${parks}" var="park">
			<c:url value="/img/parks/${fn:toLowerCase(park.parkCode)}.jpg" var="parkPic" />
			<c:url value="/parkDetails" var="parkDetails">
			<c:param value="${park.parkCode}" name="parkCode" /></c:url>
			<div class="tile">
				<div class="tileimage">
					<a href="${parkDetails}"> <img src="${parkPic}" alt="${park.parkName}" /></a>
				</div>
				<div class="tiletext">
					<h2>
						<a href="${parkDetails}"> <c:out value="${park.parkName}" /></a>
					
					</h2>
				
					<p>
						<c:out value="${park.parkDescription}" />
					</p>
				</div>
			</div>

		</c:forEach>
	</div>
</body>
</html>