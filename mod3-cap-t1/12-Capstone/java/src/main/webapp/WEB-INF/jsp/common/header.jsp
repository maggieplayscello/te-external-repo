<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>NATIONAL PARK GEEK</title>
    <c:url value="/m3-java-capstone/src/main/webapp/css/npgeekstyling.css" var="cssHref" />
    <link rel="stylesheet" href="${cssHref}">
</head>

<body >
    <div id="mainheader">
    		<c:url value="/" var="home" />
    		<c:url value="/img/logo.png" var="logoSrc" />
       <div> <a href="${home}">
        		<img src="${logoSrc}" alt="Solar System Geek logo" id="logo" />
        </a>
        
          <nav class="navbar">
   <ul class="navbox"> 
   <li class="navbutton"><a href="<c:url value="/changeTemp"/>">Display Temp: <c:out value="${temppref}"/>°</a></li>  
   <li class="navbutton"><a href="<c:url value="/survey"/>"><c:out value="Survey"/></a></li>
      <li class="navbutton"><a href="<c:url value="/topParks"/>"><c:out value="Top Parks"/></a></li>
   </ul>
    </nav>
        </div>
    </div>
  