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
	<div class="splashheader">
		<h1>${park.parkName}</h1>
	</div>

	<div class="splashimage">
		<c:url value="/img/parks/${fn:toLowerCase(park.parkCode)}.jpg"
			var="parkPic" />
		<img src="${parkPic}" alt="${park.parkName}" />
	</div>

	<div class="tile" id="detailinfo">
		<div class="tiletext">
			<table>
				<tr>
					<td>State:</td>
					<td>${park.state}</td>
				</tr>
				<tr>
					<td>Year Founded:</td>
					<td>${park.yearFounded}</td>
				</tr>
				<tr>
					<td>Acreage:</td>
					<td>${park.acreage}</td>
				</tr>
				<tr>
					<td>Elevation:</td>
					<td>${park.elevationInFeet}ft.</td>
				</tr>
				<tr>
					<td>Miles of Trail:</td>
					<td>${park.milesOfTrail}</td>
				</tr>
				<tr>
					<td>Number of Animal Species:</td>
					<td>${park.numberOfAnimalSpecies}</td>
				</tr>
				<tr>
					<td>Climate:</td>
					<td>${park.climate}</td>
				</tr>
				<tr>
					<td>Annual Visitors:</td>
					<td>${park.annualVisitorCount}</td>
				</tr>
				<tr>
					<td>Number of Campsites:</td>
					<td>${park.numberOfCampsites}</td>
				</tr>
				<tr>
					<td>Entry Fee:</td>
					<td>$${park.entryFee}.00</td>
				</tr>
			</table>
		</div>
		<div class="tiletext">
			"${park.inspirationalQuote}" <br> -
			${park.inspirationalQuoteSource} <br> <br>
			${park.parkDescription}
		</div>
	</div>
</body>

<footer>
	<br>
	<br>
	<h2 class="splashheader">Five Day Forecast:</h2>
	<div class="footer">
		<c:forEach items="${forecast}" var="forecast">
			<div class="forecast">
				<table class="forecastinfo">
					<tr>
						<td><c:choose>
								<c:when test="${forecast.forecast == 'partly cloudy'}">
									<c:url value="/img/weather/partlyCloudy.png"
										var="forecastImage" />
								</c:when>
								<c:otherwise>
									<c:url value="/img/weather/${forecast.forecast}.png"
										var="forecastImage" />
								</c:otherwise>
							</c:choose> <img src="${forecastImage}" />
						<td><c:out value="High: ${forecast.high}" />°<c:out
								value="${temppref}" /><br> <c:out
								value="Low: ${forecast.low}" />°<c:out value="${temppref}" /></td>
					</tr>
				</table >
				<div class = "forecastinfo">
				<p><c:out value="${forecast.recommendation}" /></p>
				</div>
			</div>
		</c:forEach>
	</div>
</footer>
</html>
