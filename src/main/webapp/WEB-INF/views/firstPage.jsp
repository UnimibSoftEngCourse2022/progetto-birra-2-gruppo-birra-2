<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"/>
		<title>BrewDay!</title>

		<!-- Custom Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Sora:wght@100;200;300;400;500;600;700;800&display=swap" rel="stylesheet">

        <!-- External Files -->
        <spring:url value="/resources/assets/logo.png" var="logoPNG" />
		<spring:url value="/resources/assets/BrewDesign.png" var="brewDesignPNG" />
        <link href="${logoPNG}" rel="icon" />

        <spring:url value="/resources/style.css" var="styleCSS" />
		<spring:url value="/resources/firstPage/header.css" var="headerCSS" />
        <spring:url value="/resources/firstPage/logo.js" var="logoJS" />
		<spring:url value="/resources/firstPage/buttons.js" var="buttonsJS" />
		<spring:url value="/resources/firstPage/presentation.js" var="presentationJS" />
        
        <link href="${styleCSS}" rel="stylesheet" />
		<link href="${headerCSS}" rel="stylesheet" />
        <script src="${logoJS}"></script>
		<script src="${buttonsJS}"></script>
		<script src="${presentationJS}"></script>

	</head>
	<body>
		
		<header>
            <header-logo 
                logo="${logoPNG}">
            </header-logo>
            <header-buttons 
                submitTo1="signin" 
                value1="Registrati" 
                submitTo2="login" 
                value2="Accedi">
            </header-buttons>
        </header>

        <main-presentation 
            submitTo="login" 
            value="Inizia Ora!!" 
            image="${brewDesignPNG}">
        </main-presentation>

	</body>
</html>