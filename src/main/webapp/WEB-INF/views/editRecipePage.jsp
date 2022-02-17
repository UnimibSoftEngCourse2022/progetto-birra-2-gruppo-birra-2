<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
	<head>
	<meta charset="ISO-8859-1">
	<title>BrewDay!</title>

	<!-- Custom Fonts -->
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css2?family=Sora:wght@100;200;300;400;500;600;700;800&display=swap" rel="stylesheet">

	<!-- External Files -->
	<spring:url value="/resources/assets/+.png" var="plusPNG" />
	<spring:url value="/resources/assets/-.png" var="minusPNG" />
	<spring:url value="/resources/assets/lente.png" var="lentePNG" />
	<spring:url value="/resources/assets/logo.png" var="logoPNG" />
	<link href="${logoPNG}" rel="icon" />

	<spring:url value="/resources/style.css" var="styleCSS" />
	<spring:url value="/resources/components/header.js" var="headerJS" />
	<spring:url value="/resources/login/header.css" var="headerloginCSS" />
	<spring:url value="/resources/recipes/header.css" var="headerrecipesCSS" />
	<spring:url value="/resources/recipes/recipes.css" var="recipesCSS" />

	<link href="${styleCSS}" rel="stylesheet" />
	<script src="${headerJS}"></script>
	<link href="${headerloginCSS}" rel="stylesheet" />
	<link href="${headerrecipesCSS}" rel="stylesheet" />
	<link href="${recipesCSS}" rel="stylesheet" />
	
	</head>
	<body>

		<header-simple logo="${logoPNG}" plus="${plusPNG}" add=""
		ingredients="location.href='editUserIng?nick=${autore}';" 
		tools="location.href='editUserEquip?nick=${autore}';"></header-plus>

		<form action="recipes" method="GET">
			<input type="submit" value="Indietro" />
		</form>
		
		<input type="button" 
			onclick="location.href='modifyRecipe?id=${Ricetta.ID}';" 
			value="Modifica" />
		&nbsp;&nbsp;&nbsp;
		
		<input type="button" 
			onclick="location.href='deleteRecipe?id=${Ricetta.ID}';" 
			value="Elimina" />
		&nbsp;&nbsp;&nbsp;
		
		<h2>${Ricetta.nome}</h2>
		
		<p>${Ricetta.descrizione}<p>
		
		<p>${Ricetta.procedimento}<p>
		
		<h4>INGREDIENTI</h4>
		
		<c:forEach var="component" items="${listRecComponents}" varStatus="status">
			<p>${component}<p>
		</c:forEach>

		<h4>ATTREZZATURA</h4>
		
		<c:forEach var="tool" items="${listRecTools}" varStatus="status">
			<p>${tool}<p>
		</c:forEach>
		
	</body>
</html>