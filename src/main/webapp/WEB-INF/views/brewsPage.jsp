<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
        <spring:url value="/resources/assets/lente.png" var="lentePNG" />
        <spring:url value="/resources/assets/logo.png" var="logoPNG" />
        <spring:url value="/resources/assets/honey.png" var="honeyPNG" />
        <spring:url value="/resources/assets/grain.png" var="grainPNG" />
        <spring:url value="/resources/assets/hop.png" var="hopPNG" />
        <spring:url value="/resources/assets/malt.png" var="maltPNG" />
        <spring:url value="/resources/assets/water.png" var="waterPNG" />
        <spring:url value="/resources/assets/honey.orange.png" var="honeyorangePNG" />
        <spring:url value="/resources/assets/grain.grey.png" var="graingreyPNG" />
        <spring:url value="/resources/assets/alcohol.png" var="alcoholPNG" />
        <spring:url value="/resources/assets/water.pink.png" var="waterpinkPNG" />
        <link href="${logoPNG}" rel="icon" />

        <spring:url value="/resources/style.css" var="styleCSS" />
        <spring:url value="/resources/brews/header.js" var="headerbrewsJS" />
        <spring:url value="/resources/login/header.css" var="headerloginCSS" />
        <spring:url value="/resources/recipes/header.css" var="headerrecipesCSS" />
        <spring:url value="/resources/recipes/recipes.css" var="recipesCSS" />
		<spring:url value="/resources/brews/brews.css" var="brewsCSS" />
        <spring:url value="/resources/components/recipeCard.js" var="recipeCardJS" />
        <spring:url value="/resources/components/recipeCard.css" var="recipeCardCSS" />

        <link href="${styleCSS}" rel="stylesheet" />
        <script src="${headerbrewsJS}"></script>
        <link href="${headerloginCSS}" rel="stylesheet" />
        <link href="${headerrecipesCSS}" rel="stylesheet" />
        <link href="${recipesCSS}" rel="stylesheet" />
		<link href="${brewsCSS}" rel="stylesheet" />
        <script src="${recipeCardJS}"></script>
        <link href="${recipeCardCSS}" rel="stylesheet" />

    </head>
    <body>
		
        <header-brews logo="${logoPNG}" plus="${plusPNG}"
        add="createBeer"
        ingredients="location.href='editUserIng?nick=${autore}';" 
		tools="location.href='editUserEquip?nick=${autore}';"></header-brews>

		<div id="lista"></div>

		<script>
			// script lista spesa
			var x = "";
			<c:forEach var="item" items="${spesa}">
					x += "${item}" + "\n";
			</c:forEach>
			if(x.length > 0)
			alert(x);
		</script>

		<script>
			var lista = document.getElementById("lista");
			var birre = new Array();
			<c:forEach var="birre" items="${listBirre}">
				var text = "${birre}";
				birre.push(text.split(" - "));
			</c:forEach>
			for(val of birre)
				lista.innerHTML += "<p>Nome ricetta: "+val[0] +" Quantità creata: " +val[1] +"l  Note: " + val[2]+"</p><br>";
		</script>

		</body>
</html>