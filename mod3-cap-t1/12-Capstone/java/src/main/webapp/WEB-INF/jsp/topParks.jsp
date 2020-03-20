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
		<div class="tile">
		<div class="surveypark">
			<table class= "surveytable">
				<tr>
					<th></th>
					<th>Park Name: </th>
					<th>Votes: </th>
				</tr>
				
				<c:forEach items="${topParks}" var="topPark">
					<tr>
						<c:url value="/img/parks/${fn:toLowerCase(topPark.parkCode)}.jpg" var="parkPic" />
						<c:url value="/parkDetails" var="parkDetails">
						<c:param value="${topPark.parkCode}" name="parkCode" /></c:url>
						<td ><a href="${parkDetails}"> <img src="${parkPic}" alt="${topPark.parkName}" /></a></td>
						<td><c:out value="${topPark.parkName}" /></td>
						<td><c:forEach items="${surveyResults}" var="surveyResults">
							<c:if test="${topPark.parkCode == surveyResults.key}">
								<c:out value="${surveyResults.value}" />
							</c:if>
							</c:forEach>
						</td>
					</tr>
				</c:forEach>			
			</table>
		</div>
		</div>		
	</div>
</body>
</html>